package com.ms8materials.Ms8Materials.essentials;

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
                    new KeyboardButton(MessagesConstants.MAIN_KEYBOARD_BUTTONS.FILES.getValue()),
                    new KeyboardButton(MessagesConstants.MAIN_KEYBOARD_BUTTONS.DASHBOARD.getValue()))),
                new KeyboardRow(List.of(
                    new KeyboardButton(MessagesConstants.MAIN_KEYBOARD_BUTTONS.SUBJECTS.getValue()),
                    new KeyboardButton(MessagesConstants.MAIN_KEYBOARD_BUTTONS.NOTES.getValue())))
                );
        mainMenuKeyboard.setKeyboard(mainKeyboardRowList);
    }
}
