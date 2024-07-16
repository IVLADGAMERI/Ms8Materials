package com.ms8materials.Ms8Materials.interaction.messages.handlers;

import com.ms8materials.Ms8Materials.interaction.Response;

public interface MessageHandler {
    Response handle(String message, long chatId);
}
