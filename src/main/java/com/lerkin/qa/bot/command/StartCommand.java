package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.utils.CommandUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class StartCommand extends BotCommand {

    private static final String commandIdentifier = "start";
    private static final String description = "/start";

    public StartCommand() {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        CommandUtils.sendAnswer(absSender, chat.getId(),"Приветствую тебя. Чтобы получить навигацию по коммандам введите /help ");
    }
}
