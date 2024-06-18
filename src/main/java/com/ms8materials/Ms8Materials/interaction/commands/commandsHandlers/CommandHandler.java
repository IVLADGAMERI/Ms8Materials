package com.ms8materials.Ms8Materials.interaction.commands.commandsHandlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    SendMessage process(Update update);
}
