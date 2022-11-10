package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.TopicDto;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.TopicClient;
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
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TopicKeyboardCommand extends BotCommand {

    private final TopicClient topicClient;
    private static final String commandIdentifier = "topickeyboard";

    public TopicKeyboardCommand(TopicClient topicClient) {
        super(commandIdentifier, CommandNames.TOPIC_KEYBOARD);
        this.topicClient = topicClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        List<TopicDto> topics;
        String commandName = arguments[0];
        if (commandName.equals(CommandNames.TOPIC_DELETE)) {
            topics = topicClient.getChosenTopics(chat.getId());
        } else {
            topics = topicClient.getAllTopics();
        }
        SendMessage message = buildTopicKeyboard(chat.getId(), topics, commandName);
        CommandUtils.sendAnswerWithKeyboard(absSender, message);
    }

    public SendMessage buildTopicKeyboard(long chatId, List<TopicDto> topics, String commandName) {

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        buttonRows.add(new ArrayList<>());
        AtomicInteger counter = new AtomicInteger();
        topics.forEach(topic -> {
            if (counter.incrementAndGet() == 4) {
                buttonRows.add(new ArrayList<>());
                counter.set(0);
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(topic.getName());
            button.setCallbackData(commandName + " " + topic.getId() + " " + topic.getName());
            List<InlineKeyboardButton> row = buttonRows.get(buttonRows.size() - 1);
            row.add(button);
        });
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttonRows);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите тему:");
        message.setReplyMarkup(markup);
        return message;
    }
}
