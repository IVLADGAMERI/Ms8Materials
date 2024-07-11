package com.ms8materials.Ms8Materials.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Data
@NoArgsConstructor
public class Response {
    private Object source;

    private SendMessage sendMessage;
    private SendDocument sendDocument;
    private SendPhoto sendPhoto;
    private ResponseType type;
    private EditMessageText editMessageText;
    private Object payload;
    public Response (SendMessage sendMessage, ResponseType type, Object source, Object payload) {
        this.sendMessage = sendMessage;
        this.type = type;
        this.source = source;
        this.payload = payload;
    }
    public Response(EditMessageText editMessageText, ResponseType type) {
        this.editMessageText = editMessageText;
        this.type = type;
    }
    public Response (SendDocument sendDocument, ResponseType type, Object source, Object payload) {
        this.sendDocument = sendDocument;
        this.type = type;
        this.source = source;
        this.payload = payload;
    }
    public Response (SendPhoto sendPhoto, ResponseType type, Object source, Object payload) {
        this.sendPhoto = sendPhoto;
        this.type = type;
        this.source = source;
        this.payload = payload;
    }
}
