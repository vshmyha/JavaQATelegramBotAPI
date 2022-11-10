package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.ExerciseDto;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.ExerciseClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class ExerciseCommand extends BotCommand {

    private static final String commandIdentifier = "exercises";

    private final ExerciseClient exerciseClient;

    public ExerciseCommand(ExerciseClient exerciseClient) {
        super(commandIdentifier, CommandNames.EXERCISES);
        this.exerciseClient = exerciseClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        Integer topicId = Integer.parseInt(arguments[0]);
        String topicName = Arrays.stream(arguments).skip(1).collect(Collectors.joining(" "));
        List<ExerciseDto> exercises = exerciseClient.getFullExercisesByTopicId(topicId);
        List<StringBuilder> messageBuild = new ArrayList<>();
        messageBuild.add(new StringBuilder());
        AtomicInteger numbering = new AtomicInteger();
        exercises.forEach(exercise -> {
            StringBuilder questionBuild = new StringBuilder();
            questionBuild.append(numbering.incrementAndGet());
            questionBuild.append(". ");
            questionBuild.append(exercise.getQuestion());
            questionBuild.append("\n");
            if (messageBuild.get(messageBuild.size() - 1).length() + questionBuild.length() >= 4096) {
                messageBuild.add(new StringBuilder());
            }
            messageBuild.get(messageBuild.size() - 1).append(questionBuild);
        });
        messageBuild.forEach(message -> {
            String messageBody = message.toString();
            CommandUtils.sendAnswer(absSender, chat.getId(), "Вопросы по теме: " + topicName + "\n" + messageBody);
        });
    }
}
