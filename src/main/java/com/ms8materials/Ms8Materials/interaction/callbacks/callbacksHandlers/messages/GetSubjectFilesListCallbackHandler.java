package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.ms8materials.Ms8Materials.data.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.EditingCallbackHandlerImpl;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Component
public class GetSubjectFilesListCallbackHandler extends EditingCallbackHandlerImpl {

    @Autowired
    private SubjectsDataService subjectsDataService;

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload) {
        return null;
    }
}
