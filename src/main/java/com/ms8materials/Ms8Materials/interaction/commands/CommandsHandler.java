package com.ms8materials.Ms8Materials.interaction.commands;

import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandsHandler {
    private final Map<String, CommandHandler> commandsHandlers;
    public CommandsHandler(@Autowired StartCommandHandler startCommandHandler,
                           @Autowired FilesCommandHandler filesCommandHandler,
                           @Autowired DashboardCommandHandler dashboardCommandHandler,
                           @Autowired SubjectsCommandHandler subjectsCommandHandler,
                           @Autowired NotesCommandHandler notesCommandHandler) {
        this.commandsHandlers = Map.of(
                "/start", startCommandHandler,
                    MessagesConstants.MAIN_KEYBOARD_BUTTONS.FILES.getValue(), filesCommandHandler,
                    MessagesConstants.MAIN_KEYBOARD_BUTTONS.DASHBOARD.getValue(), dashboardCommandHandler,
                    MessagesConstants.MAIN_KEYBOARD_BUTTONS.SUBJECTS.getValue(), subjectsCommandHandler,
                    MessagesConstants.MAIN_KEYBOARD_BUTTONS.NOTES.getValue(), notesCommandHandler
        );
    }
    public Response handle(Update update) {
        Response response;
        CommandHandler commandHandler = commandsHandlers.get(update.getMessage().getText());
        if (commandHandler != null) {
            response = commandHandler.handle(update);
        } else {
            SendMessage responseMessage = new SendMessage();
            responseMessage.setChatId(update.getMessage().getChatId());
            responseMessage.setText("Выбери нужный пункт из меню.");
            response = new Response(responseMessage, ResponseType.MESSAGE, this);
        }
        return response;
    }
}
