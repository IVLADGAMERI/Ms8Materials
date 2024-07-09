package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.services.SubjectsService;
import com.ms8materials.Ms8Materials.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetSubjectsListCallbackHandler implements CallbackHandler{
    @Autowired
    private SubjectsService subjectsService;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        try {
            SemesterIdCallbackData semesterIdCallbackData = objectMapper.readValue(callbackData.getData(),
                    SemesterIdCallbackData.class);
            List<SubjectEntity> subjectEntityList =
                    subjectsService.findAllBySemesterEntityId(semesterIdCallbackData.getSemesterId());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MessagesConstants.ANSWERS.SUBJECTS_LIST_HAT.getValue());
            stringBuilder.append(semesterIdCallbackData.getSemesterId());
            stringBuilder.append("\n");
            if (subjectEntityList.isEmpty()) {
                stringBuilder.append(MessagesConstants.ANSWERS.EMPTY_LIST.getValue());
                return new Response(new SendMessage(String.valueOf(chatId),stringBuilder.toString()),
                        ResponseType.MESSAGE, this, null);
            }
            List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
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
                                        CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                                        objectMapper.writeValueAsString(new GetSubjectMaterialsListCallbackData(item.getId()))))
                );
            }
            stringBuilder.append(MessagesConstants.ANSWERS.SUBJECTS_LIST_FOOTER.getValue());
            InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                    inlineKeyboardButtonDataList, 1
            );
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(stringBuilder.toString());
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            return new Response(sendMessage,
                    ResponseType.MESSAGE, this, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(chatId),
                    "Failed to handle callback!"), ResponseType.MESSAGE,
                    this, null);
        }

    }
}
