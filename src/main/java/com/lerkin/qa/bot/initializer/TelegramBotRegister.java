package com.lerkin.qa.bot.initializer;

import com.lerkin.qa.bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class TelegramBotRegister {

    private final Bot bot;

    @EventListener
    private void botRegistration(ContextRefreshedEvent event) {

        try {
//            ApplicationContext applicationContext = event.getApplicationContext();
//            String[] commands = applicationContext.getBeanNamesForType(BotCommand.class);
//            List<BotCommand> commandObjects = Arrays.stream(commands)
//                    .map(applicationContext::getBean)
//                    .map(object -> (BotCommand) object)
//                    .toList();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
