package com.ms8materials.Ms8Materials.interaction.callbacks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.interaction.Handler;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.CallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.documents.GetSubjectFileCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages.*;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.photos.GetSubjectPhotoCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CallbacksHandler implements Handler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Map<CallbackType, CallbackHandler> callbackHandlers;


    public CallbacksHandler(@Autowired GetSubjectsListCallbackHandler getSubjectsListCallbackHandler,
                            @Autowired GetSubjectDataListCallbackHandler getSubjectDataListCallbackHandler,
                            @Autowired GetSubjectNotesListCallbackHandler getSubjectNotesListCallbackHandler,
                            @Autowired GetSubjectDataTypesListCallbackHandler getSubjectDataTypesListCallbackHandler,
                            @Autowired GetSubjectFileCallbackHandler getSubjectFileCallbackHandler,
                            @Autowired GetSubjectPhotoCallbackHandler getSubjectPhotoCallbackHandler,
                            @Autowired GetSubjectNoteCallbackHandler getSubjectNoteCallbackHandler,
                            @Autowired FindSubjectDataCallbackHandler findSubjectDataCallbackHandler) {
        this.callbackHandlers = new HashMap<>();
        callbackHandlers.put(CallbackType.GET_SUBJECTS_LIST, getSubjectsListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_MATERIALS_TYPES_LIST, getSubjectDataTypesListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_NOTES_LIST, getSubjectNotesListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_FILES_LIST_DESCENDING, getSubjectDataListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_FILES_LIST_ASCENDING, getSubjectDataListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_PHOTOS_LIST_ASCENDING, getSubjectDataListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_PHOTOS_LIST_DESCENDING, getSubjectDataListCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_NOTE, getSubjectNoteCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_PHOTO, getSubjectPhotoCallbackHandler);
        callbackHandlers.put(CallbackType.GET_SUBJECT_FILE, getSubjectFileCallbackHandler);
        callbackHandlers.put(CallbackType.FIND_SUBJECT_FILE, findSubjectDataCallbackHandler);
        callbackHandlers.put(CallbackType.FIND_SUBJECT_PHOTO, findSubjectDataCallbackHandler);
    }


    public Response handle(Update update, Object payload) {
        try {
            CallbackData callbackData = objectMapper.readValue(update.getCallbackQuery().getData(), CallbackData.class);
            log.info(callbackData.toString());
            String callbackTypeString = callbackData.getT();
            CallbackHandler callbackHandler =
                    callbackHandlers.get(CallbackType.getByName(callbackTypeString));
            if (callbackHandler == null) {
                throw new RuntimeException("Callback handler with type " + callbackTypeString + " is not found!");
            }
            return callbackHandler.handle(callbackData, update.getCallbackQuery().getMessage().getChatId());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(update.getCallbackQuery().getMessage().getChatId()),
                    "Failed to handle callback"), ResponseType.MESSAGE, this, null, null);
        }
    }

}
