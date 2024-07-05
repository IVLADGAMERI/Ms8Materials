package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    Response handle(CallbackData callbackData, long chatId);
}
