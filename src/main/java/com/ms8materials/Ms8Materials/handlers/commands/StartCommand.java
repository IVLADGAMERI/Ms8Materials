package com.ms8materials.Ms8Materials.handlers.commands;

import com.ms8materials.Ms8Materials.handlers.KeyboardsFactory;
import com.ms8materials.Ms8Materials.handlers.MessagesConstants;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(MessagesConstants.START_COMMAND_MESSAGE);
        KeyboardsFactory keyboardsFactory = new KeyboardsFactory();
        return sendMessage;
    }
}
