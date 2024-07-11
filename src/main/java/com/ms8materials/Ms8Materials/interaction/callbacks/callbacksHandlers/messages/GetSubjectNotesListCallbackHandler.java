package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import org.springframework.stereotype.Component;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;

@Component
public class GetSubjectNotesListCallbackHandler implements CallbackHandler{
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return null;
    }
}
