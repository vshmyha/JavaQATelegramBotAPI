package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.TopicDto;
import com.lerkin.qa.bot.utils.CommandMassage;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.TopicClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopicCommand extends BotCommand {

    private static final String commandIdentifier = "topic";

    private final TopicClient topicClient;

    public TopicCommand(TopicClient topicClient) {
        super(commandIdentifier, CommandNames.TOPIC);
        this.topicClient = topicClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        List<TopicDto> topics = topicClient.getAllTopics();
        StringBuilder messageBuild = new StringBuilder();
        topics.forEach(topic -> {
            messageBuild.append(topic.getName());
            messageBuild.append("\n");
        });
        String messageText = messageBuild.toString();
        List<CommandMassage> commandMassages = new ArrayList<>();
        commandMassages.add(new CommandMassage(CommandNames.CHOSE_TOPIC, "Добавить тему"));
        commandMassages.add(new CommandMassage(CommandNames.EXERCISES, "Посмотреть вопросы по теме"));
        SendMessage message = CommandUtils.buildInlineKeyBoard(chat.getId(), messageText, commandMassages);
        CommandUtils.sendAnswerWithKeyboard(absSender, message);
    }
}
