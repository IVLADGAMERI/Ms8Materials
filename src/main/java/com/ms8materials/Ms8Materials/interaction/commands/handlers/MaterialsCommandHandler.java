package com.ms8materials.Ms8Materials.interaction.commands.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.jpa.entities.SemesterEntity;
import com.ms8materials.Ms8Materials.data.jpa.services.SemestersService;
import com.ms8materials.Ms8Materials.interaction.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.interaction.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.data.SemesterIdCallbackData;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MaterialsCommandHandler implements CommandHandler{
    @Autowired
    private SemestersService semestersService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Response handle(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        Response response = new Response();
        response.setType(ResponseType.MESSAGE);
        response.setSource(this);
        List<SemesterEntity> semesterEntityList = semestersService.findAll();
        try {
            List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
            for (SemesterEntity item : semesterEntityList) {
                inlineKeyboardButtonDataList.add(new InlineKeyboardButtonData(
                        String.valueOf(item.getNumber()),
                        new CallbackData(
                                CallbackType.GET_SUBJECTS_LIST.getName(),
                                objectMapper.writeValueAsString(new SemesterIdCallbackData(item.getId())
                                ),
                                0
                        )
                ));
            }
            InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                    inlineKeyboardButtonDataList, 2);
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
