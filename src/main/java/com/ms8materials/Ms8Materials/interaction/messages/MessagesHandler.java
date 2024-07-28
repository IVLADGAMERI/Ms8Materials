package com.ms8materials.Ms8Materials.interaction.messages;

import com.ms8materials.Ms8Materials.data.app.ConversationContextData;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Handler;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.messages.handlers.FindSubjectFilesMessageHandler;
import com.ms8materials.Ms8Materials.interaction.messages.handlers.GetSubjectMaterialsMessageHandler;
import com.ms8materials.Ms8Materials.interaction.messages.handlers.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class MessagesHandler implements Handler {
    private Map<MessageHandlerType, MessageHandler> handlers;
    public MessagesHandler(@Autowired GetSubjectMaterialsMessageHandler getSubjectMaterialsMessageHandler,
                           @Autowired FindSubjectFilesMessageHandler findSubjectFilesMessageHandler) {
        this.handlers = Map.of(
                MessageHandlerType.FIND_SUBJECT_FILES, findSubjectFilesMessageHandler,
                MessageHandlerType.GET_SUBJECT_MATERIALS, getSubjectMaterialsMessageHandler
        );
    }
    @Override
    public Response handle(Update update, Object payload) {
        Response response;
        try {
            ConversationContextData conversationContextData = (ConversationContextData) payload;
            MessageHandler messageHandler = handlers.get(conversationContextData.getMessageHandlerType());
            response = messageHandler.handle(update.getMessage(), conversationContextData.getPayload());
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(new SendMessage(String.valueOf(update.getMessage().getChatId()),
                    MessagesConstants.ANSWERS.MESSAGE_UNRECOGNISED.getValue()), ResponseType.MESSAGE,
                    this, null, null);
        }
        return response;
    }
}
