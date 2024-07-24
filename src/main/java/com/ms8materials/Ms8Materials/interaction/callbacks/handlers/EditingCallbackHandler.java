package com.ms8materials.Ms8Materials.interaction.callbacks.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface EditingCallbackHandler extends CallbackHandler{

    EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) throws JsonProcessingException;
    @EventListener
    public void handleMessageSentEvent(MessageSentEvent event);
}
