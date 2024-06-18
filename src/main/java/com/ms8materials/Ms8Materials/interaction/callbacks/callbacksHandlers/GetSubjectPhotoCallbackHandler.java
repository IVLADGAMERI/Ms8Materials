package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GetSubjectPhotoCallbackHandler implements CallbackHandler{
    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
