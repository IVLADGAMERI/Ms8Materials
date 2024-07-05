package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;

import com.ms8materials.Ms8Materials.interaction.Response;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    Response handle(Update update);
}
