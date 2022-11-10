package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.ExerciseClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashMap;
import java.util.Objects;

@Component
public class StatisticCommand extends BotCommand {

    private static final String commandIdentifier = "statistic";

    private final ExerciseClient exerciseClient;

    public StatisticCommand(ExerciseClient exerciseClient) {
        super(commandIdentifier, CommandNames.STATISTIC);
        this.exerciseClient = exerciseClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        HashMap<String, String> statistics = exerciseClient.statistic(chat.getId());
        StringBuilder messageBuild = new StringBuilder();
        messageBuild.append("Прогресс выбранных вами тем: \n");
        if (statistics.size() != 0) {
            statistics.forEach((k, v) -> messageBuild.append(k + " - " + v + "\n"));
        } else {
            messageBuild.append("Вы пока не выполнили ни одного задание.");
        }
        String message = messageBuild.toString();
        CommandUtils.sendAnswer(absSender, chat.getId(), message);
    }
}
