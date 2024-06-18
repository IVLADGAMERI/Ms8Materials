package com.ms8materials.Ms8Materials.interaction.callbacks;

import com.ms8materials.Ms8Materials.essentials.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.GetSubjectFilesListCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.GetSubjectsListCallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CallbacksHandler {
    private final Map<CallbackType, CallbackHandler> callbacksHandlers;

    public CallbacksHandler(@Autowired GetSubjectsListCallbackHandler getSubjectsListCallbackHandler,
                            @Autowired GetSubjectFilesListCallbackHandler getSubjectFilesListCallbackHandler) {
        this.callbacksHandlers = Map.of(
                CallbackType.GET_SUBJECTS_LIST, getSubjectsListCallbackHandler,
                CallbackType.GET_SUBJECT_FILES_LIST, getSubjectFilesListCallbackHandler);
    }

    public SendMessage handle(Update update) {
        return new SendMessage(update.getMessage().getChatId().toString(), "ABOBA");
    }
}
