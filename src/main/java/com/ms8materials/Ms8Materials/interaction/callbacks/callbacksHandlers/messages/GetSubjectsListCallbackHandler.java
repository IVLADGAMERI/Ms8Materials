package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.repositories.SubjectsRepository;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.CallbackHandler;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class GetSubjectsListCallbackHandler implements CallbackHandler{
    @Autowired
    private SubjectsRepository subjectsRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Data
    @NoArgsConstructor
    private static class SemesterIdCallbackData {
        private int semesterId;
    }

    @Override
    public Response handle(CallbackData callbackData, long chatId) {
        try {
            SemesterIdCallbackData semesterIdCallbackData = objectMapper.readValue(callbackData.getData(),
                    SemesterIdCallbackData.class);
            List<SubjectEntity> subjectEntityList =
                    subjectsRepository.findAllBySemesterEntityId(semesterIdCallbackData.getSemesterId());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MessagesConstants.ANSWERS.SUBJECTS_LIST_HAT.getValue());
            stringBuilder.append(semesterIdCallbackData.getSemesterId());
            stringBuilder.append("\n");
            if (subjectEntityList.isEmpty()) {
                stringBuilder.append(MessagesConstants.ANSWERS.EMPTY_LIST.getValue());
                return new Response(new SendMessage(String.valueOf(chatId),stringBuilder.toString()),
                        ResponseType.MESSAGE, this);
            }

            for (SubjectEntity item : subjectEntityList) {
                stringBuilder
                        .append(MessagesConstants.ANSWERS.MARKER.getValue())
                        .append(item.getName())
                        .append("\n")
                        .append(item.getTeachersData())
                        .append("\n");
            }
            return new Response(new SendMessage(String.valueOf(chatId),
                    stringBuilder.toString()),
                    ResponseType.MESSAGE, this);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(new SendMessage(String.valueOf(chatId),
                    "Failed to handle callback!"), ResponseType.MESSAGE,
                    this);
        }

    }
}
