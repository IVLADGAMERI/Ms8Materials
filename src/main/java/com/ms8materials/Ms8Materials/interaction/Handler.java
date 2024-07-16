package com.ms8materials.Ms8Materials.interaction;

import com.ms8materials.Ms8Materials.interaction.messages.handlers.MessageHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handler {
    Response handle(Update update, Object payload);
}
