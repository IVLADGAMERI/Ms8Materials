package com.ms8materials.Ms8Materials.essentials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class KeyboardsFactory {
    public static final InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
    public static final ReplyKeyboardMarkup mainMenuKeyboard = new ReplyKeyboardMarkup();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        List<List<InlineKeyboardButton>> mainMenuKeyboardRows = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<InlineKeyboardButton> mainMenuKeyboardCol = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
                keyboardButton.setText(i + String.valueOf(j));
                keyboardButton.setCallbackData("{\n  \"type\": \"GET_SUBJECTS_LIST\",\n  \"data\": \"25\"\n}");
                mainMenuKeyboardCol.add(keyboardButton);
            }
            mainMenuKeyboardRows.add(mainMenuKeyboardCol);
        }
        inlineKeyboard.setKeyboard(mainMenuKeyboardRows);

        mainMenuKeyboard.setOneTimeKeyboard(false);
        mainMenuKeyboard.setResizeKeyboard(false);
        List<KeyboardRow> mainKeyboardRowList = List.of(
                new KeyboardRow(List.of(
                        new KeyboardButton("/start"),
                        new KeyboardButton(MessagesConstants.MAIN_KEYBOARD_BUTTONS.FILES.getValue())
                    )),
                new KeyboardRow(List.of(
                        new KeyboardButton(MessagesConstants.MAIN_KEYBOARD_BUTTONS.NOTES.getValue())))
                );
        mainMenuKeyboard.setKeyboard(mainKeyboardRowList);
    }

    public static InlineKeyboardMarkup generateInlineKeyboard(List<List<InlineKeyboardButtonData>> buttonDataMatrix) throws JsonProcessingException {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtonsMatrix = new ArrayList<>();
        for (List<InlineKeyboardButtonData> buttonDataList : buttonDataMatrix) {
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
            for (InlineKeyboardButtonData buttonData : buttonDataList) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(buttonData.getText());
                inlineKeyboardButton.setCallbackData(objectMapper.writeValueAsString(buttonData.getCallbackData()));
                buttonsRow.add(inlineKeyboardButton);
            }
            inlineButtonsMatrix.add(buttonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(inlineButtonsMatrix);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup generateInlineKeyboard(List<InlineKeyboardButtonData> buttonData, int colsNumber) throws JsonProcessingException {
        List<List<InlineKeyboardButtonData>> buttonDataMatrix = new ArrayList<>();
        int rowsNumber = buttonData.size() / colsNumber;
        int currentButtonDataIndex = 0;
        for (int i = 0; i < rowsNumber; i++) {
            List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
            for (int j = 0; j < colsNumber; j++, currentButtonDataIndex++) {
                InlineKeyboardButtonData inlineKeyboardButtonData = buttonData.get(currentButtonDataIndex);
                if (inlineKeyboardButtonData == null) break;
                inlineKeyboardButtonDataList.add(inlineKeyboardButtonData);
            }
            buttonDataMatrix.add(inlineKeyboardButtonDataList);
        }
        return generateInlineKeyboard(buttonDataMatrix);
    }

}
