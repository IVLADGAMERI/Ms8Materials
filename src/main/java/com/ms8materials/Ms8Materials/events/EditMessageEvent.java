package com.ms8materials.Ms8Materials.events;

import com.ms8materials.Ms8Materials.interaction.Response;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Data
public class EditMessageEvent extends ApplicationEvent {
    private EditMessageText editMessageText;
    public EditMessageEvent(Object source, EditMessageText editMessageText) {
        super(source);
        this.editMessageText = editMessageText;
    }
}
