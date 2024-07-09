package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.events.EditMessageEvent;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;

import java.util.List;

@Component
@Slf4j
public class GetSubjectMaterialsListCallbackHandler implements CallbackHandler, ApplicationEventPublisherAware {
    @Autowired
    private SubjectsDataService subjectsDataService;
    private ApplicationEventPublisher applicationEventPublisher;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return new Response(new SendMessage(String.valueOf(chatId), MessagesConstants.ANSWERS.WAIT.getValue()),
                ResponseType.MESSAGE,
                this, callbackData);
    }

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {
        if (event.getSource() == this) {
            StringBuilder stringBuilder = new StringBuilder();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setMessageId(event.getMessage().getMessageId());
            editMessageText.setChatId(event.getMessage().getChatId());
            try {
                CallbackData payload = (CallbackData) event.getPayload();
                GetSubjectMaterialsListCallbackData callbackData = objectMapper.readValue(payload.getData(),
                        GetSubjectMaterialsListCallbackData.class);
                List<SubjectDataEntity> subjectDataEntityList =
                        subjectsDataService.findAllBySubjectId(callbackData.getSubjectId());
                if (subjectDataEntityList.isEmpty()) {
                    stringBuilder.append("Нет данных.");
                } else {
                    SubjectDataEntity firstSubjectDataEntity = subjectDataEntityList.getFirst();
                    SubjectEntity subjectEntity = firstSubjectDataEntity.getSubject();
                    stringBuilder.append(MessagesConstants.ANSWERS.SUBJECT_MATERIALS_LIST_HAT.getValue())
                            .append(subjectEntity.getName())
                            .append("\n");

                    for (SubjectDataEntity item : subjectDataEntityList) {
                        stringBuilder.append(item.getId())
                                .append(") ")
                                .append(item.getName())
                                .append("\n")
                                .append(item.getType())
                                .append("\n");
                    }
                }
                editMessageText.setText(stringBuilder.toString());
                applicationEventPublisher.publishEvent(new EditMessageEvent(
                        this, editMessageText
                ));
            } catch (Exception e) {
                editMessageText.setText("Failed!\nException: " + e.getMessage());
                e.printStackTrace();
                applicationEventPublisher.publishEvent(new EditMessageEvent(
                        this, editMessageText
                ));
            }

        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
