package com.ms8materials.Ms8Materials;

import com.ms8materials.Ms8Materials.events.EditMessageEvent;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbacksHandler;
import com.ms8materials.Ms8Materials.interaction.commands.CommandsHandler;
import com.ms8materials.Ms8Materials.interaction.commands.handlers.StartCommandHandler;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import com.ms8materials.Ms8Materials.interaction.messages.MessagesHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("tgBotMain")
@Slf4j
public class TgBotMain extends TelegramLongPollingBot implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
    @Autowired
    private CallbacksHandler callbacksHandler;
    @Autowired
    private CommandsHandler commandsHandler;
    @Autowired
    private MessagesHandler messagesHandler;
    private Map<Long, MessageHandlerType> conversationContext = new HashMap<>();
    @Override
    public void onUpdateReceived(Update update) {
        long chatId;
        int messageId = 0;
        Response response;
        if (update.hasCallbackQuery()) {
            messageId = update.getCallbackQuery().getMessage().getMessageId();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            log.info(update.getCallbackQuery().getData());
            response = callbacksHandler.handle(update, null);
            answerCallbackQuery(update.getCallbackQuery().getId());
        } else if(update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().startsWith("/")){
            chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            response = commandsHandler.handle(update, null);
        } else if (update.hasMessage() && update.getMessage().hasText()){
            chatId = update.getMessage().getChatId();
            response = messagesHandler.handle(update, conversationContext.get(chatId));
        } else {
            chatId = update.getMessage().getChatId();
            response = new Response(new SendMessage(String.valueOf(chatId), "Error!"),
                    ResponseType.MESSAGE, this, null, null);
        }
        conversationContext.put(chatId, response.getMessageHandlerType());
        switch (response.getType()) {
            case PHOTO:
                sendResponse(response.getSendMediaGroup(), response.getSource(), response.getPayload());
                break;
            case MESSAGE:
                sendResponse(response.getSendMessage(), response.getSource(), response.getPayload());
                break;
            case DOCUMENT:
                sendResponse(response.getSendDocumentList(), response.getSource(), response.getPayload());
                break;
            case EDIT:
                sendResponse(response.getEditMessageText());
                break;
        }
    }

    private void sendResponse(EditMessageText response) {
        try {
            log.info(response.toString());
            execute(response);
        } catch (TelegramApiException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(SendMessage response, Object source, Object payload) {
        try {
            log.info(response.toString());
            Message message = execute(response);
            applicationEventPublisher.publishEvent(new MessageSentEvent(source, message.getMessageId(), message.getChatId(), payload));
        } catch (TelegramApiException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(List<SendDocument> response, Object source, Object payload) {
        try {
            for (SendDocument item : response) {
                Message message = execute(item);
            }
        } catch (TelegramApiException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(SendMediaGroup response, Object source, Object payload) {
        try {
            execute(response);
        } catch (TelegramApiException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void answerCallbackQuery(String callbackQueryId) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQueryId);
        answerCallbackQuery.setShowAlert(false);
        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException | NullPointerException e) {
            e.printStackTrace();
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


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void handleEditMessageEvent(EditMessageEvent event) {
        try {
            execute(event.getEditMessageText());
        } catch (TelegramApiException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
