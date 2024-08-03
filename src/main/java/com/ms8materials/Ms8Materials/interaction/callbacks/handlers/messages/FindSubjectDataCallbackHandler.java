package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms8materials.Ms8Materials.data.jpa.SubjectDataType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.SubjectDataTypeAndSortDirection;
import com.ms8materials.Ms8Materials.interaction.data.SubjectIdAndTypeData;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.data.SubjectIdData;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.CallbackHandlerImpl;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class FindSubjectDataCallbackHandler extends CallbackHandlerImpl {

    @Override
    public Response handle(CallbackData callbackData, long chatId) throws JsonProcessingException {
        Response response = new Response();
        response.setType(ResponseType.MESSAGE);
        response.setSource(this);
        SubjectIdData subjectIdData = objectMapper.readValue(callbackData.getD(),
                SubjectIdData.class);
        SubjectDataType subjectDataType = SubjectDataTypeAndSortDirection
                .getByCallbackType(CallbackType.getByName(callbackData.getT())).getSubjectDataType();
        response.setSendMessage(
                new SendMessage(
                        String.valueOf(chatId),
                        String.format(MessagesConstants.ANSWERS.FIND_SUBJECT_FILES.getValue(), subjectIdData.getSbId())
                )
        );
        response.setMessageHandlerType(MessageHandlerType.FIND_SUBJECT_DATA);
        response.setPayload(new SubjectIdAndTypeData(subjectIdData.getSbId(), subjectDataType.getCallbackDataValue()));
        return response;
    }
}
