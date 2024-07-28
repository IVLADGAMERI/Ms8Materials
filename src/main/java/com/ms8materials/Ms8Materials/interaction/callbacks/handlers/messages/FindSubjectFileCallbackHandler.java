package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdCallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.CallbackHandlerImpl;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class FindSubjectFileCallbackHandler extends CallbackHandlerImpl {

    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        Response response = new Response();
        response.setType(ResponseType.MESSAGE);
        response.setSource(this);
        try {
            SubjectIdCallbackData subjectIdCallbackData = objectMapper.readValue(callbackData.getD(),
                    SubjectIdCallbackData.class);
            response.setSendMessage(
                    new SendMessage(
                            String.valueOf(chatId),
                            String.format(MessagesConstants.ANSWERS.FIND_SUBJECT_FILES.getValue(), subjectIdCallbackData.getSubId())
                    )
            );
            response.setMessageHandlerType(MessageHandlerType.FIND_SUBJECT_FILES);
            response.setPayload(subjectIdCallbackData);
        } catch (JsonProcessingException e) {
            response.setSendMessage(
                    new SendMessage(
                            String.valueOf(chatId),
                            e.getMessage()
                    )
            );
        }
        return response;
    }
}
