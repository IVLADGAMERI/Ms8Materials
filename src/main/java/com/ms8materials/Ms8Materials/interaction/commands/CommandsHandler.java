package com.ms8materials.Ms8Materials.interaction.commands;

import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers.CommandHandler;
import com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers.StartCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandsHandler {
    private final Map<String, CommandHandler> commandsHandlers;
    public CommandsHandler(@Autowired StartCommandHandler startCommandHandler) {
        this.commandsHandlers = Map.of(
                "/start", startCommandHandler
        );
    }
    public SendMessage handle(Update update) {
        CommandHandler commandHandler = commandsHandlers.get(update.getMessage().getText());
        if (commandHandler != null) {
            return commandHandler.process(update);
        } else {
            SendMessage responseMessage = new SendMessage();
            responseMessage.setChatId(update.getMessage().getChatId());
            responseMessage.setText(MessagesConstants.COMMANDS_LIST);
            return responseMessage;
        }
    }
}
