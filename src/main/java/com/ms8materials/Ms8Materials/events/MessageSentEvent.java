package com.ms8materials.Ms8Materials.events;

import com.ms8materials.Ms8Materials.interaction.Response;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Message;

@Data
public class MessageSentEvent extends ApplicationEvent {
    private Message message;
    private Object payload;
    private Object source;
    public MessageSentEvent(Object source, Message message, Object payload) {
        super(source);
        this.message = message;
        this.source = source;
        this.payload = payload;
    }
}
