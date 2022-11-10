package com.lerkin.qa.bot;

import com.lerkin.qa.bot.command.CommandNames;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Bot extends TelegramLongPollingCommandBot {

    private final String TOKEN = "token";
    private final String BOT_USERNAME = "bot user name";
    private static final String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public Bot(List<BotCommand> commands) {

        commands.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        try {
            String callBackData = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            update.setMessage(message);
            message.setText(callBackData);
            message.setEntities(List.of(new MessageEntity(EntityType.BOTCOMMAND, 0, 0)));
        } catch (NullPointerException e) {
            String messageText = update.getMessage().getText();
            Pattern pattern = Pattern.compile(TIME_PATTERN);
            Matcher matcher = pattern.matcher(messageText);
            Message message = update.getMessage();
            message.setEntities(List.of(new MessageEntity(EntityType.BOTCOMMAND, 0, 0)));
            update.setMessage(message);
            if (matcher.matches()) {
                message.setText(CommandNames.SCHEDULE_TIME + " " + messageText);
            } else {
                message.setText(CommandNames.NON_COMMAND);
            }
        }
        onUpdateReceived(update);
    }
}
