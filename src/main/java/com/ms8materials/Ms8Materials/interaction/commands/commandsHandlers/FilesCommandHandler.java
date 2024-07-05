package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;

import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FilesCommandHandler implements CommandHandler{
    @Override
    public Response handle(Update update) {
        return new Response(new SendMessage(String.valueOf(update.getMessage().getChatId()), "ABOBA"), ResponseType.MESSAGE, this);
    }
}
