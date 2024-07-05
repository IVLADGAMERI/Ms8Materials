package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;

import com.ms8materials.Ms8Materials.interaction.Response;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NotesCommandHandler implements CommandHandler{
    @Override
    public Response handle(Update update) {
        return null;
    }
}
