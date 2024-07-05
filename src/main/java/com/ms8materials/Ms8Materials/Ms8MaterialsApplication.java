package com.ms8materials.Ms8Materials;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@Slf4j
public class Ms8MaterialsApplication {

	public static void main(String[] args) throws NoSuchFieldException {
		ApplicationContext ctx = SpringApplication.run(Ms8MaterialsApplication.class, args);
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot((LongPollingBot) ctx.getBean("tgBotMain"));
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
		}
	}

}
