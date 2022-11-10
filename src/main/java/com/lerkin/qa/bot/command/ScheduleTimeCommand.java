package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.ScheduleTimeDto;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.ScheduleTimeClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.Time;

@Component
public class ScheduleTimeCommand extends BotCommand {

    private static final String commandIdentifier = "scheduletime";

    private final ScheduleTimeClient scheduleTimeClient;

    public ScheduleTimeCommand(ScheduleTimeClient scheduleTimeClient) {
        super(commandIdentifier, CommandNames.SCHEDULE_TIME);
        this.scheduleTimeClient = scheduleTimeClient;
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String tim = arguments[0] + ":00";
        Time time = Time.valueOf(tim);
        scheduleTimeClient.saveScheduleTimeSetting(new ScheduleTimeDto(0, chat.getId(), time));
        String message;
        if (tim.equals("00:00:00")) {
            message = "Были выставлены стандартные настройки. Вопросы будут присылаться по нажатию на кнопку";
        } else {
            message = "Вопросы буду присылаться раз в " + time;
        }
        CommandUtils.sendAnswer(absSender, chat.getId(), message);
    }
}
