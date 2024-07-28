package com.ms8materials.Ms8Materials.interaction.callbacks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.interaction.Handler;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.CallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.documents.GetSubjectFileCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages.*;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.photos.GetSubjectPhotoCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@Slf4j
public class CallbacksHandler implements Handler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Map<CallbackType, CallbackHandler> callbackHandlers;


    public CallbacksHandler(@Autowired GetSubjectsListCallbackHandler getSubjectsListCallbackHandler,
                            @Autowired GetSubjectMaterialsListCallbackHandler getSubjectMaterialsListCallbackHandler,
                            @Autowired GetSubjectNotesListCallbackHandler getSubjectNotesListCallbackHandler,
                            @Autowired GetSubjectMaterialsTypesListCallbackHandler getSubjectMaterialsTypesListCallbackHandler,
                            @Autowired GetSubjectFileCallbackHandler getSubjectFileCallbackHandler,
                            @Autowired GetSubjectPhotoCallbackHandler getSubjectPhotoCallbackHandler,
                            @Autowired GetSubjectNoteCallbackHandler getSubjectNoteCallbackHandler,
                            @Autowired FindSubjectFileCallbackHandler findSubjectFileCallbackHandler) {
        this.callbackHandlers = Map.of(
                CallbackType.GET_SUBJECTS_LIST, getSubjectsListCallbackHandler,
                CallbackType.GET_SUBJECT_MATERIALS_TYPES_LIST, getSubjectMaterialsTypesListCallbackHandler,
                CallbackType.GET_SUBJECT_NOTES_LIST, getSubjectNotesListCallbackHandler,
                CallbackType.GET_SUBJECT_MATERIALS_LIST, getSubjectMaterialsListCallbackHandler,
                CallbackType.GET_SUBJECT_NOTE, getSubjectNoteCallbackHandler,
                CallbackType.GET_SUBJECT_PHOTO, getSubjectPhotoCallbackHandler,
                CallbackType.GET_SUBJECT_FILE, getSubjectFileCallbackHandler,
                CallbackType.FIND_SUBJECT_FILE, findSubjectFileCallbackHandler);
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
