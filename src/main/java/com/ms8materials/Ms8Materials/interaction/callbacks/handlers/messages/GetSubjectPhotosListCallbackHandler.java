package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import org.springframework.stereotype.Component;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.CallbackHandler;

@Component
public class GetSubjectPhotosListCallbackHandler implements CallbackHandler{
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return null;
    }
}
