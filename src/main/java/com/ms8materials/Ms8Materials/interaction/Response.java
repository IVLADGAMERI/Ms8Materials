package com.ms8materials.Ms8Materials.interaction;

import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@Data
@NoArgsConstructor
public class Response {
    private Object source;
    private SendMessage sendMessage;
    private List<SendDocument> sendDocumentList;
    private SendMediaGroup sendMediaGroup;
    private EditMessageText editMessageText;
    private ResponseType type;
    private MessageHandlerType messageHandlerType;
    private Object payload;
    public Response(EditMessageText editMessageText, ResponseType type, Object source, Object payload,
                    MessageHandlerType messageHandlerType) {
        this.editMessageText = editMessageText;
        this.type = type;
        this.source = source;
        this.payload = payload;
        this.messageHandlerType = messageHandlerType;
    }
    public Response (SendMessage sendMessage, ResponseType type, Object source, Object payload,
                     MessageHandlerType messageHandlerType) {
        this.sendMessage = sendMessage;
        this.type = type;
        this.source = source;
        this.payload = payload;
        this.messageHandlerType = messageHandlerType;
    }
    public Response (List<SendDocument> sendDocumentList, ResponseType type, Object source, Object payload,
                     MessageHandlerType messageHandlerType) {
        this.sendDocumentList = sendDocumentList;
        this.type = type;
        this.source = source;
        this.payload = payload;
        this.messageHandlerType = messageHandlerType;
    }
    public Response (SendMediaGroup sendMediaGroup, ResponseType type, Object source, Object payload,
                     MessageHandlerType messageHandlerType) {
        this.sendMediaGroup = sendMediaGroup;
        this.type = type;
        this.source = source;
        this.payload = payload;
        this.messageHandlerType = messageHandlerType;
    }
}
