package com.ms8materials.Ms8Materials.events;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Message;

@Data
public class MessageSentEvent extends ApplicationEvent {
    private Message message;
    public MessageSentEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }
}
