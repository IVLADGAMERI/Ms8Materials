package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.entities.SemesterEntity;
import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.repositories.SemestersRepository;
import com.ms8materials.Ms8Materials.data.repositories.SubjectsRepository;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SubjectsCommandHandler implements CommandHandler{
    @Data
    @AllArgsConstructor
    private class QueryData {
        private int semesterId;
    }
    @Autowired
    private SemestersRepository semestersRepository;
    @Override
    public Response handle(Update update) {
        ObjectMapper objectMapper = new ObjectMapper();
        String chatId = String.valueOf(update.getMessage().getChatId());
        Response response = new Response();
        response.setType(ResponseType.MESSAGE);
        response.setSource(this);
        List<SemesterEntity> semesterEntityList = semestersRepository.findAll();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtonsMatrix = new ArrayList<>();
        int rowsNumber = semesterEntityList.size() / 2;
        int currentSemesterEntityListIndex = 0;
        try {
            for (int i = 0; i < rowsNumber; i++) {
                List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
                for (int j = 0; j < 2; j++, currentSemesterEntityListIndex++) {
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(String.valueOf(semesterEntityList.get(currentSemesterEntityListIndex).getNumber()));
                    QueryData queryData = new QueryData(semesterEntityList.get(currentSemesterEntityListIndex).getId());
                    CallbackData callbackData = new CallbackData();
                    callbackData.setType(CallbackType.GET_SUBJECTS_LIST.getName());
                    callbackData.setData(objectMapper.writeValueAsString(queryData));
                    inlineKeyboardButton.setCallbackData(objectMapper.writeValueAsString(callbackData));
                    buttonsRow.add(inlineKeyboardButton);
                }
                inlineButtonsMatrix.add(buttonsRow);
            }
            inlineKeyboardMarkup.setKeyboard(inlineButtonsMatrix);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessagesConstants.ANSWERS.SUBJECTS_SEMESTERS_HAT.getValue());
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            response.setSendMessage(sendMessage);
        } catch (Exception e) {
            response.setSendMessage(new SendMessage(chatId, "Failed!"));
        }
        return response;
    }
}
