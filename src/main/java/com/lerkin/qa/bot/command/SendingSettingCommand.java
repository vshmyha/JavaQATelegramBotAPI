package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.utils.CommandUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class SendingSettingCommand extends BotCommand {

    private static final String commandIdentifier = "sendingsetting";

    public SendingSettingCommand() {
        super(commandIdentifier, CommandNames.SENDING_SETTING);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        CommandUtils.sendAnswer(absSender, chat.getId(), "Пришлите время в формате hh:mm. " +
                "Это интервал, через который будут приходить вопросы. " +
                "Отсчёт интервала всегда начинается после нажатия на кнопку 'Посмотреть ответ'. " +
                "Чтобы выставить стандартные настройки(по нажатию кнопки) отправьте 00:00.");
    }
}
