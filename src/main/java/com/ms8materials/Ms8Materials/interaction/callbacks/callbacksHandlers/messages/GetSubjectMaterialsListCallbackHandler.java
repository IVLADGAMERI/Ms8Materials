package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;

@Component
public class GetSubjectMaterialsListCallbackHandler implements CallbackHandler{
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return null;
    }
}
