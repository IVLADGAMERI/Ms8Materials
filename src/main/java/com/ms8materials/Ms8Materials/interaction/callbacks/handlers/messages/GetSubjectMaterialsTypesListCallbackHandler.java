package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.jpa.SubjectDataType;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.*;
import com.ms8materials.Ms8Materials.interaction.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.interaction.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.ResponseBasedEditingCallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
@Slf4j
public class GetSubjectMaterialsTypesListCallbackHandler extends ResponseBasedEditingCallbackHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        CallbackData castedPayload = (CallbackData) payload;
        SubjectIdAndSemesterIdCallbackData subjectIdAndSemesterIdCallbackData = objectMapper.readValue(castedPayload.getD(),
                SubjectIdAndSemesterIdCallbackData.class);
        List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = List.of(
                new InlineKeyboardButtonData(
                        MessagesConstants.INLINE_BUTTONS_TEXT.FILES.getValue(),
                        new CallbackData(
                                CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                                objectMapper.writeValueAsString(
                                        new SubjectIdAndPageNumberAndTypeCallbackData(
                                                subjectIdAndSemesterIdCallbackData.getSubId(),
                                                0,
                                                SubjectDataType.FILE.getCallbackDataValue()
                                        )
                                ),
                                messageId
                        )
                ),
                new InlineKeyboardButtonData(
                        MessagesConstants.INLINE_BUTTONS_TEXT.PHOTOS.getValue(),
                        new CallbackData(
                                CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                                objectMapper.writeValueAsString(
                                        new SubjectIdAndPageNumberAndTypeCallbackData(
                                                subjectIdAndSemesterIdCallbackData.getSubId(),
                                                0,
                                                SubjectDataType.PHOTO.getCallbackDataValue()
                                        )
                                ),
                                messageId
                        )
                ),
                new InlineKeyboardButtonData(
                        MessagesConstants.INLINE_BUTTONS_TEXT.QUIT.getValue(),
                        new CallbackData(
                                CallbackType.GET_SUBJECTS_LIST.getName(),
                                objectMapper.writeValueAsString(new SemesterIdCallbackData(
                                                subjectIdAndSemesterIdCallbackData.getSemId()
                                        )
                                ),
                                messageId
                        )
                )
        );
        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                inlineKeyboardButtonDataList,
                2);
        stringBuilder.append(MessagesConstants.ANSWERS.SUBJECT_MATERIALS_LIST_HAT.getValue());
        editMessageText.setText(stringBuilder.toString());
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

}
