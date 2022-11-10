package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.utils.CommandUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class NonCommand extends BotCommand {

    private static final String commandIdentifier = "noncommand";

    public NonCommand() {
        super(commandIdentifier, CommandNames.NON_COMMAND);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        CommandUtils.sendAnswer(absSender, chat.getId(), "Сообщение не подходит формату. Попробуйте ещё раз.");
    }
}
