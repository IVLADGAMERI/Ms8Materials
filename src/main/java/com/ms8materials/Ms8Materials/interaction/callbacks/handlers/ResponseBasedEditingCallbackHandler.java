package com.ms8materials.Ms8Materials.interaction.callbacks.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class ResponseBasedEditingCallbackHandler implements EditingCallbackHandler{
    @Autowired
    protected ObjectMapper objectMapper;
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        Response response = new Response();
        response.setType(ResponseType.EDIT);
        response.setPayload(callbackData);
        response.setSource(this);
        try {
            response.setEditMessageText(editMessage(callbackData.getMId(), chatId, callbackData, response));
        } catch (JsonProcessingException e) {
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(callbackData.getMId());
            editMessageText.setText(e.getMessage());
        }
        return response;
    }

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) throws JsonProcessingException {
        return null;
    }

    @Override
    public void handleMessageSentEvent(MessageSentEvent event) {}
}
