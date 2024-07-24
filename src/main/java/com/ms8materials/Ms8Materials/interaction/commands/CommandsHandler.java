package com.ms8materials.Ms8Materials.interaction.commands;

import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Handler;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.commands.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandsHandler implements Handler {
    private final Map<String, CommandHandler> commandsHandlers;
    public CommandsHandler(@Autowired StartCommandHandler startCommandHandler,
                           @Autowired MaterialsCommandHandler filesCommandHandler,
                           @Autowired NotesCommandHandler notesCommandHandler) {
        this.commandsHandlers = Map.of(
                "/start", startCommandHandler,
                    MessagesConstants.MAIN_KEYBOARD_BUTTONS.FILES.getValue(), filesCommandHandler,
                    MessagesConstants.MAIN_KEYBOARD_BUTTONS.NOTES.getValue(), notesCommandHandler
        );
    }
    public Response handle(Update update, Object payload) {
        Response response;
        CommandHandler commandHandler = commandsHandlers.get(update.getMessage().getText());
        if (commandHandler != null) {
            response = commandHandler.handle(update);
        } else {
            SendMessage responseMessage = new SendMessage();
            responseMessage.setChatId(update.getMessage().getChatId());
            responseMessage.setText("Выбери нужный пункт из меню.");
            response = new Response(responseMessage, ResponseType.MESSAGE, this,  null, null);
        }
        return response;
    }
}
