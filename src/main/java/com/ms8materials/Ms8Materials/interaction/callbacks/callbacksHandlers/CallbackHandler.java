package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    SendMessage handle(Update update);
}
