package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.utils.CommandUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class HelpCommand extends BotCommand {

    private static final String commandIdentifier = "help";
    private static final String description = "/help";

    public HelpCommand() {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        CommandUtils.sendAnswer(absSender, chat.getId(),
                "Вот все возможности, на которые способен бот: " +
                        "/start - старт приложения,\n" +
                        "/help - посмотреть список коммнад,\n" +
                        "/topic - посмотреть список всех тем, \n" +
                        "/showchosentopics - посмотреть cписок добавленных тем, \n" +
                        "/newquestion - задаёт новый вопрос, \n" +
                        "/statistic - посмотреть прогресс по выбранным темам, \n" +
                        "/sendingsetting - настройки таймингов отправки вопросов."
                );
    }
}
