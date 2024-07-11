package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import org.springframework.stereotype.Component;

@Component
public class GetSubjectFilesListCallbackHandler implements CallbackHandler {
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return null;
    }
}
