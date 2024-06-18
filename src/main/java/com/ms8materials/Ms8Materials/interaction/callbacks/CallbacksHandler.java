package com.ms8materials.Ms8Materials.interaction.callbacks;

import com.ms8materials.Ms8Materials.essentials.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CallbacksHandler {
    private final Map<CallbackType, CallbackHandler> callbacksHandlers;

    public CallbacksHandler(@Autowired GetSubjectsListCallbackHandler getSubjectsListCallbackHandler,
                            @Autowired GetSubjectFilesListCallbackHandler getSubjectFilesListCallbackHandler,
                            @Autowired GetSubjectNotesListCallbackHandler getSubjectNotesListCallbackHandler,
                            @Autowired GetSubjectPhotosListCallbackHandler getSubjectPhotosListCallbackHandler,
                            @Autowired GetSubjectMaterialsCallbackHandler getSubjectMaterialsCallbackHandler,
                            @Autowired GetSubjectFileCallbackHandler getSubjectFileCallbackHandler,
                            @Autowired GetSubjectPhotoCallbackHandler getSubjectPhotoCallbackHandler,
                            @Autowired GetSubjectNoteCallbackHandler getSubjectNoteCallbackHandler) {
        this.callbacksHandlers = Map.of(
                CallbackType.GET_SUBJECTS_LIST, getSubjectsListCallbackHandler,
                CallbackType.GET_SUBJECT_FILES_LIST, getSubjectFilesListCallbackHandler,
                CallbackType.GET_SUBJECT_NOTES_LIST, getSubjectNotesListCallbackHandler,
                CallbackType.GET_SUBJECT_PHOTOS_LIST, getSubjectPhotosListCallbackHandler,
                CallbackType.GET_SUBJECT_MATERIALS, getSubjectMaterialsCallbackHandler,
                CallbackType.GET_SUBJECT_FILE, getSubjectFileCallbackHandler,
                CallbackType.GET_SUBJECT_PHOTO, getSubjectPhotoCallbackHandler,
                CallbackType.GET_SUBJECT_NOTE, getSubjectNoteCallbackHandler);
    }

    public SendMessage handle(Update update) {
        return new SendMessage(update.getMessage().getChatId().toString(), "ABOBA");
    }
}
