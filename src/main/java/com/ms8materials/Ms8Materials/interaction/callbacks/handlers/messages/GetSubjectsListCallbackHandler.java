package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsService;
import com.ms8materials.Ms8Materials.interaction.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.interaction.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.EventBasedEditingCallbackHandlerImpl;
import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.data.SemesterIdData;
import com.ms8materials.Ms8Materials.interaction.data.SubjectIdAndSemesterIdData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetSubjectsListCallbackHandler extends EventBasedEditingCallbackHandlerImpl {
    @Autowired
    private SubjectsService subjectsService;

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) throws JsonProcessingException {
        EditMessageText editMessageText = new EditMessageText();
        CallbackData callbackData = (CallbackData) payload;
        SemesterIdData semesterIdData = objectMapper.readValue(callbackData.getD(),
                SemesterIdData.class);
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        List<SubjectEntity> subjectEntityList =
                subjectsService.findAllBySemesterEntityId(semesterIdData.getSmId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(MessagesConstants.ANSWERS.SUBJECTS_LIST_HAT.getValue(),
                        semesterIdData.getSmId()))
                .append("\n");
        List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
        if (subjectEntityList.isEmpty()) {
            stringBuilder.append(MessagesConstants.ANSWERS.EMPTY_LIST.getValue());
            editMessageText.setText(stringBuilder.toString());
            return editMessageText;
        }
        for (SubjectEntity item : subjectEntityList) {
            stringBuilder
                    .append(item.getId())
                    .append(") ")
                    .append(item.getName())
                    .append("\n")
                    .append(item.getTeachersData())
                    .append("\n");
            inlineKeyboardButtonDataList.add(
                    new InlineKeyboardButtonData(
                            String.valueOf(item.getId()),
                            new CallbackData(
                                    CallbackType.GET_SUBJECT_MATERIALS_TYPES_LIST.getName(),
                                    objectMapper.writeValueAsString(new SubjectIdAndSemesterIdData(
                                                    item.getId(),
                                                    item.getSemesterEntity().getId()
                                            )
                                    ),
                                    messageId
                            )
                    )
            );
        }
        stringBuilder.append(MessagesConstants.ANSWERS.SUBJECTS_LIST_FOOTER.getValue());
        InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                inlineKeyboardButtonDataList, 2
        );
        editMessageText.setText(stringBuilder.toString());
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;

    }
}
