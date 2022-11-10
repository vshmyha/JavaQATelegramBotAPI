package com.lerkin.qa.bot.utils;

import com.lerkin.qa.bot.command.CommandNames;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {

    public static void sendAnswer(AbsSender absSender, Long chatId, String text) {

        SendMessage message = new SendMessage();
        message.enableMarkdown(false);
        message.setChatId(chatId);
        message.setText(text);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendAnswerWithKeyboard(AbsSender absSender, SendMessage message) {

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static SendMessage buildInlineKeyBoard(long chatId, String messageText, List<CommandMassage> commandMassages) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton leftButton = new InlineKeyboardButton();
        List<InlineKeyboardButton> row = new ArrayList<>();
        leftButton.setText(commandMassages.get(0).getMassage());
        leftButton.setCallbackData(CommandNames.TOPIC_KEYBOARD + " " + commandMassages.get(0).getCommandName());
        row.add(leftButton);
        if (commandMassages.size() > 1) {
            InlineKeyboardButton rightButton = new InlineKeyboardButton();
            rightButton.setText(commandMassages.get(1).getMassage());
            rightButton.setCallbackData(CommandNames.TOPIC_KEYBOARD + " " + commandMassages.get(1).getCommandName());
            row.add(rightButton);
        }
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        buttonRows.add(row);
        markup.setKeyboard(buttonRows);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        message.setReplyMarkup(markup);
        return message;
    }
}
