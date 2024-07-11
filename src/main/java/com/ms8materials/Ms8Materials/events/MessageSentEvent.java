package com.ms8materials.Ms8Materials.events;

import com.ms8materials.Ms8Materials.interaction.Response;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Message;

@Data
public class MessageSentEvent extends ApplicationEvent {
    private int messageId;
    private long chatId;
    private Object payload;
    private Object source;
    public MessageSentEvent(Object source, int messageId, long chatId, Object payload) {
        super(source);
        this.messageId = messageId;
        this.chatId = chatId;
        this.source = source;
        this.payload = payload;
    }
}
