package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;
import com.ms8materials.Ms8Materials.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandHandler implements CommandHandler {
    @Autowired
    private KeyboardsFactory keyboardsFactory;

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(MessagesConstants.START_COMMAND_MESSAGE);
        sendMessage.setReplyMarkup(KeyboardsFactory.mainMenuKeyboard);
        return sendMessage;
    }
}
