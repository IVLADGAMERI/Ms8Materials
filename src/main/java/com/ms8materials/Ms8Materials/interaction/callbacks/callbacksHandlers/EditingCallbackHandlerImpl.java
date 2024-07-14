package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdAndMessageIdCallbackData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class EditingCallbackHandlerImpl implements EditingCallbackHandler, ApplicationEventPublisherAware {
    protected ApplicationEventPublisher applicationEventPublisher;
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        try {
            SubjectIdAndMessageIdCallbackData getSubjectMaterialsListCallbackData = objectMapper.readValue(
                    callbackData.getData(), SubjectIdAndMessageIdCallbackData.class
            );
            if (getSubjectMaterialsListCallbackData.getMId() == 0) {
                return new Response(new SendMessage(String.valueOf(chatId), MessagesConstants.ANSWERS.WAIT.getValue()),
                        ResponseType.MESSAGE,
                        this, callbackData);

            } else {
                EditMessageText editMessageText = editMessage(getSubjectMaterialsListCallbackData.getMId(), chatId, callbackData);
                return new Response(editMessageText, ResponseType.EDIT, this, callbackData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(chatId), e.getMessage()),
                    ResponseType.MESSAGE,
                    this, callbackData);
        }
    }

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload) {
        return null;
    }

    @Override
    public void handleMessageSentEvent(MessageSentEvent event) {}

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
