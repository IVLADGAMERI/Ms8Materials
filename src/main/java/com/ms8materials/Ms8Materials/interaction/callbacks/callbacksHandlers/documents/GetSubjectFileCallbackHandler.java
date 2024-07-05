package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.documents;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GetSubjectFileCallbackHandler implements CallbackHandler {
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        return null;
    }
}
