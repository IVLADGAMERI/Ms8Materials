package com.ms8materials.Ms8Materials.interaction.callbacks.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.events.EditMessageEvent;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class EventBasedEditingCallbackHandlerImpl implements EditingCallbackHandler, ApplicationEventPublisherAware {
    protected ApplicationEventPublisher applicationEventPublisher;
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        try {
            if (callbackData.getMId() == 0) {
                return new Response(new SendMessage(String.valueOf(chatId), MessagesConstants.ANSWERS.WAIT.getValue()),
                        ResponseType.MESSAGE,
                        this, callbackData, null);

            } else {
                Response response = new Response();
                response.setType(ResponseType.EDIT);
                response.setSource(this);
                response.setPayload(callbackData);
                EditMessageText editMessageText = editMessage(callbackData.getMId(), chatId, callbackData, response);
                response.setEditMessageText(editMessageText);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(chatId), e.getMessage()),
                    ResponseType.MESSAGE,
                    this, callbackData, null);
        }
    }

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) {
        return null;
    }

    @Override
    public void handleMessageSentEvent(MessageSentEvent event) {
        if (event.getSource() == this) {
            EditMessageText editMessageText = editMessage(event.getMessageId(), event.getChatId(), event.getPayload(), null);
            applicationEventPublisher.publishEvent(new EditMessageEvent(
                    this, editMessageText
            ));
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
