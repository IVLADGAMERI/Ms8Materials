package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.events.EditMessageEvent;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.GetSubjectMaterialsListCallbackData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
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
        try {
            GetSubjectMaterialsListCallbackData getSubjectMaterialsListCallbackData = objectMapper.readValue(
                    callbackData.getData(), GetSubjectMaterialsListCallbackData.class
            );
            if (getSubjectMaterialsListCallbackData.getMId() == 0) {
                return new Response(new SendMessage(String.valueOf(chatId), MessagesConstants.ANSWERS.WAIT.getValue()),
                        ResponseType.MESSAGE,
                        this, callbackData);

            } else {
                applicationEventPublisher.publishEvent(
                        new MessageSentEvent(this, getSubjectMaterialsListCallbackData.getMId(), chatId, callbackData)
                );
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(chatId), e.getMessage()),
                    ResponseType.MESSAGE,
                    this, callbackData);
        }

    }

    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event) {
        if (event.getSource() == this) {
            StringBuilder stringBuilder = new StringBuilder();
            int messageId = event.getMessageId();
            long chatId = event.getChatId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setMessageId(messageId);
            editMessageText.setChatId(chatId);
            try {
                CallbackData payload = (CallbackData) event.getPayload();
                GetSubjectMaterialsListCallbackData callbackData = objectMapper.readValue(payload.getData(),
                        GetSubjectMaterialsListCallbackData.class);
                int subjectId = callbackData.getSubId();
                int pageIndex = callbackData.getPg();
                List<SubjectDataEntity> subjectDataEntityList =
                        subjectsDataService.findAllBySubjectId(subjectId,
                                pageIndex, 10);
                InlineKeyboardMarkup inlineKeyboardMarkup;
                List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
                int inlineKeyboardButtonDataListIndex = 0;
                if (subjectDataEntityList.isEmpty()) {
                    stringBuilder.append("Нет данных.");
                    if (pageIndex - 1 >= 0) {
                        inlineKeyboardButtonDataList.add(
                                inlineKeyboardButtonDataListIndex,
                                new InlineKeyboardButtonData(
                                        MessagesConstants.ANSWERS.PREVIOUS_PAGE.getValue(),
                                        new CallbackData(
                                                CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                                                objectMapper.writeValueAsString(
                                                        new GetSubjectMaterialsListCallbackData(
                                                                subjectId,
                                                                pageIndex - 1,
                                                                messageId)
                                                )
                                        )
                                )
                        );
                        inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(inlineKeyboardButtonDataList, 1);
                        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
                    }
                } else {
                    SubjectDataEntity firstSubjectDataEntity = subjectDataEntityList.getFirst();
                    SubjectEntity subjectEntity = firstSubjectDataEntity.getSubject();
                    stringBuilder.append(String.format(MessagesConstants.ANSWERS.SUBJECT_MATERIALS_LIST_HAT.getValue(),
                                    subjectEntity.getName(), pageIndex + 1))
                            .append("\n");
                    if (pageIndex - 1 >= 0) {
                        inlineKeyboardButtonDataList.add(
                                inlineKeyboardButtonDataListIndex,
                                new InlineKeyboardButtonData(
                                        MessagesConstants.ANSWERS.PREVIOUS_PAGE.getValue(),
                                        new CallbackData(
                                                CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                                                objectMapper.writeValueAsString(
                                                        new GetSubjectMaterialsListCallbackData(
                                                                subjectId,
                                                                pageIndex - 1,
                                                                messageId)
                                                )
                                        )
                                )
                        );
                        inlineKeyboardButtonDataListIndex += 1;
                    }
                    inlineKeyboardButtonDataList.add(
                            inlineKeyboardButtonDataListIndex,
                            new InlineKeyboardButtonData(
                                    MessagesConstants.ANSWERS.NEXT_PAGE.getValue(),
                                    new CallbackData(
                                            CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                                            objectMapper.writeValueAsString(
                                                    new GetSubjectMaterialsListCallbackData(
                                                            subjectId,
                                                            pageIndex + 1,
                                                            messageId)
                                            )
                                    )
                            )
                    );
                    inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                            inlineKeyboardButtonDataList, inlineKeyboardButtonDataList.size()
                    );
                    log.info(inlineKeyboardMarkup.toString());
                    for (SubjectDataEntity item : subjectDataEntityList) {
                        stringBuilder.append(item.getId())
                                .append(") ")
                                .append(item.getName())
                                .append("\n")
                                .append(item.getType())
                                .append("\n");
                    }
                    editMessageText.setReplyMarkup(inlineKeyboardMarkup);
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
