package com.ms8materials.Ms8Materials.interaction.callbacks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.TgBotMain;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.documents.GetSubjectFileCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages.*;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.photos.GetSubjectPhotoCallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
@Slf4j
public class CallbacksHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Map<CallbackType, CallbackHandler> callbackHandlers;


    public CallbacksHandler(@Autowired GetSubjectsListCallbackHandler getSubjectsListCallbackHandler,
                            @Autowired GetSubjectFilesListCallbackHandler getSubjectFilesListCallbackHandler,
                            @Autowired GetSubjectNotesListCallbackHandler getSubjectNotesListCallbackHandler,
                            @Autowired GetSubjectPhotosListCallbackHandler getSubjectPhotosListCallbackHandler,
                            @Autowired GetSubjectMaterialsListCallbackHandler getSubjectMaterialsListCallbackHandler,
                            @Autowired GetSubjectFileCallbackHandler getSubjectFileCallbackHandler,
                            @Autowired GetSubjectPhotoCallbackHandler getSubjectPhotoCallbackHandler,
                            @Autowired GetSubjectNoteCallbackHandler getSubjectNoteCallbackHandler) {
        this.callbackHandlers = Map.of(
                CallbackType.GET_SUBJECTS_LIST, getSubjectsListCallbackHandler,
                CallbackType.GET_SUBJECT_FILES_LIST, getSubjectFilesListCallbackHandler,
                CallbackType.GET_SUBJECT_NOTES_LIST, getSubjectNotesListCallbackHandler,
                CallbackType.GET_SUBJECT_PHOTOS_LIST, getSubjectPhotosListCallbackHandler,
                CallbackType.GET_SUBJECT_MATERIALS_LIST, getSubjectMaterialsListCallbackHandler,
                CallbackType.GET_SUBJECT_NOTE, getSubjectNoteCallbackHandler,
                CallbackType.GET_SUBJECT_PHOTO, getSubjectPhotoCallbackHandler,
                CallbackType.GET_SUBJECT_FILE, getSubjectFileCallbackHandler);
    }


    public Response handle(Update update) {
        try {
            CallbackData callbackData = objectMapper.readValue(update.getCallbackQuery().getData(), CallbackData.class);
            log.info(callbackData.toString());
            String callbackTypeString = callbackData.getType();
            CallbackHandler callbackHandler =
                    callbackHandlers.get(CallbackType.valueOf(callbackTypeString));
            if (callbackHandler == null) {
                throw new RuntimeException("Callback handler with type " + callbackTypeString + " is not found!");
            }
            return callbackHandler.handle(callbackData, update.getCallbackQuery().getMessage().getChatId());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(update.getCallbackQuery().getMessage().getChatId()),
                    "Failed to handle callback"), ResponseType.MESSAGE, this, null);
        }
    }

}
