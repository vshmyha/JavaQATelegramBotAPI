package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.ExerciseDto;
import com.lerkin.qa.bot.dto.ScheduleTimeDto;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.ExerciseClient;
import com.lerkin.qa.client.ScheduleTimeClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ShowAnswerCommand extends BotCommand {

    public static final String NON_SCHEDULED_TIME = "00:00:00";
    private final NotAskedExerciseCommand notAskedExerciseCommand;
    private final ExerciseClient exerciseClient;
    private final ScheduleTimeClient scheduleTimeClient;
    private static final String commandIdentifier = "showanswer";

    public ShowAnswerCommand(NotAskedExerciseCommand notAskedExerciseCommand, ExerciseClient exerciseClient, ScheduleTimeClient scheduleTimeClient) {
        super(commandIdentifier, CommandNames.SHOW_ANSWER);
        this.notAskedExerciseCommand = notAskedExerciseCommand;
        this.exerciseClient = exerciseClient;
        this.scheduleTimeClient = scheduleTimeClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        long chatId = chat.getId();
        ExerciseDto exercise = exerciseClient.getById(Integer.parseInt(arguments[0]));
        ScheduleTimeDto scheduleTime = scheduleTimeClient.getScheduleTime(chatId);
        if (scheduleTime.getTime().toString().equals(NON_SCHEDULED_TIME)) {
            SendMessage sendMessage = buildNotAskedQuestionKeyboard(chatId, exercise.getAnswer());
            CommandUtils.sendAnswerWithKeyboard(absSender, sendMessage);
        } else {
            int time = convertTimeToMinute(scheduleTime.getTime());
            CommandUtils.sendAnswer(absSender, chatId, exercise.getAnswer());
            scheduledCommandExecute(absSender, user, chat, arguments, time);
        }
    }

    public SendMessage buildNotAskedQuestionKeyboard(long chatId, String messageText) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton answerButton = new InlineKeyboardButton();
        answerButton.setText("Новый вопрос");
        answerButton.setCallbackData(CommandNames.NOT_ASKED_EXERCISE);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(answerButton);
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        buttonRows.add(row);
        markup.setKeyboard(buttonRows);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        message.setReplyMarkup(markup);
        return message;
    }

    private int convertTimeToMinute(Time time) {

        String t = time.toString();
        String[] m = t.split(":");
        int hours = Integer.parseInt(m[0]);
        int minutes = Integer.parseInt(m[1]);
        int allTime = hours * 60 + minutes;
        return allTime;
    }

    public void scheduledCommandExecute(AbsSender absSender, User user, Chat chat, String[] arguments, int time) {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            notAskedExerciseCommand.execute(absSender, user, chat, arguments);
        }, time, TimeUnit.MINUTES);
    }
}
