package com.ms8materials.Ms8Materials.interaction.callbacks.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import okhttp3.Call;

public class CallbackHandlerImpl implements CallbackHandler {
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return null;
    }
}
