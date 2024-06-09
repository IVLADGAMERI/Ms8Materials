package com.ms8materials.Ms8Materials.handlers;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class KeyboardsFactory {
    private InlineKeyboardMarkup mainMenuKeyboard;
    private InlineKeyboardMarkup setDefaultMainMenuKeyboard(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRow = new ArrayList<>();
        return inlineKeyboardMarkup;
    }
}
