package com.ms8materials.Ms8Materials.interaction.callbacks.handlers;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;

public interface CallbackHandler {
    Response handle(CallbackData callbackData, long chatId);
}
