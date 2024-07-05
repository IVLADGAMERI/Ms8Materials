package com.ms8materials.Ms8Materials.interaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Data
@NoArgsConstructor
public class Response {
    private Object source;

    private SendMessage sendMessage;
    private SendDocument sendDocument;
    private SendPhoto sendPhoto;
    private ResponseType type;
    public Response (SendMessage sendMessage, ResponseType type, Object source) {
        this.sendMessage = sendMessage;
        this.type = type;
        this.source = source;
    }
    public Response (SendDocument sendDocument, ResponseType type, Object source) {
        this.sendDocument = sendDocument;
        this.type = type;
        this.source = source;
    }
    public Response (SendPhoto sendPhoto, ResponseType type, Object source) {
        this.sendPhoto = sendPhoto;
        this.type = type;
        this.source = source;
    }
}
