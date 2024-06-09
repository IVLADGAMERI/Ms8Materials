package com.ms8materials.Ms8Materials;

import com.ms8materials.Ms8Materials.config.BotConfig;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Component("tgBotMain")
@Slf4j
public class TgBotMain extends TelegramLongPollingBot {

    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
    private Map<Long, String> conversationContext = new HashMap<>();
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startBot(chatId, conversationContext);
                    break;
                case "/test":
                    testBot(chatId, conversationContext);
                    break;
                default: log.info("Unexpected message");
            }
        }
    }

    private void startBot(long chatId, Map<Long, String> conversationContext) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello! I'm a Telegram bot.");

        try {
            execute(message);
            conversationContext.put(chatId, "/start");
            log.info(conversationContext.toString());
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
    private void testBot(long chatId, Map<Long, String> conversationContext) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("This is a test.");

        try {
            execute(message);
            conversationContext.put(chatId, "/test");
            log.info(conversationContext.toString());
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
    @Override
    public String getBotToken() {
        return botToken;
    }
}
