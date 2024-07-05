package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;
import com.ms8materials.Ms8Materials.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandHandler implements CommandHandler {

    @Override
    public Response handle(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(MessagesConstants.ANSWERS.START_COMMAND_MESSAGE.getValue());
        sendMessage.setReplyMarkup(KeyboardsFactory.mainMenuKeyboard);
        return new Response(sendMessage, ResponseType.MESSAGE, this);
    }
}
