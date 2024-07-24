package com.ms8materials.Ms8Materials.interaction.messages.handlers;

import com.ms8materials.Ms8Materials.interaction.Response;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    Response handle(Message message, Object payload);
}
