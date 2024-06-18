package com.ms8materials.Ms8Materials.essentials;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardsFactory {
    public static final InlineKeyboardMarkup mainMenuKeyboard;
    static {
        mainMenuKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> mainMenuKeyboardRows = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<InlineKeyboardButton> mainMenuKeyboardCol = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
                keyboardButton.setText(i + String.valueOf(j));
                keyboardButton.setCallbackData("Aboba: 25");
                mainMenuKeyboardCol.add(keyboardButton);
            }
            mainMenuKeyboardRows.add(mainMenuKeyboardCol);
        }
        mainMenuKeyboard.setKeyboard(mainMenuKeyboardRows);
    }

}
