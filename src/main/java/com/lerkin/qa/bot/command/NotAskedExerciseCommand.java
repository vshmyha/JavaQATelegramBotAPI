package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.ExerciseDto;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.ExerciseClient;
import com.lerkin.qa.client.FeignExceptionDecoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotAskedExerciseCommand extends BotCommand {

    private static final String commandIdentifier = "newquestion";
    private final ExerciseClient exerciseClient;

    public NotAskedExerciseCommand(ExerciseClient exerciseClient) {
        super(commandIdentifier, CommandNames.NOT_ASKED_EXERCISE);
        this.exerciseClient = exerciseClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        try {
            ExerciseDto exercise = exerciseClient.askQuestion(chat.getId());
            SendMessage sendMessage = buildShowAnswerKeyboard(chat.getId(), exercise);
            CommandUtils.sendAnswerWithKeyboard(absSender, sendMessage);
        } catch (Exception e) {
            String messageText = FeignExceptionDecoder.exceptionMessageParser(e.getMessage());
            CommandUtils.sendAnswer(absSender, chat.getId(), messageText);
        }
    }

    public SendMessage buildShowAnswerKeyboard(long chatId, ExerciseDto exercise) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton answerButton = new InlineKeyboardButton();
        answerButton.setText("Посмотреть ответ");
        answerButton.setCallbackData(CommandNames.SHOW_ANSWER + " " + exercise.getId());
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(answerButton);
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        buttonRows.add(row);
        markup.setKeyboard(buttonRows);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(exercise.getQuestion());
        message.setReplyMarkup(markup);
        return message;
    }
}

