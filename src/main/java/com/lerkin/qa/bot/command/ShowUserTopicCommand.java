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
public class ShowUserTopicCommand extends BotCommand {

    private static final String commandIdentifier = "showchosentopics";
    private final TopicClient topicClient;

    public ShowUserTopicCommand(TopicClient topicClient) {
        super(commandIdentifier, CommandNames.CHOSEN_TOPICS);
        this.topicClient = topicClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        List<TopicDto> topics = topicClient.getChosenTopics(chat.getId());
        StringBuilder messageBuild = new StringBuilder();
        messageBuild.append("Выбранные темы: \n");
        List<CommandMassage> commandMassages = new ArrayList<>();
        commandMassages.add(new CommandMassage(CommandNames.CHOSE_TOPIC, "Добавить тему"));
        if (!topics.isEmpty()) {
            topics.forEach(topic -> {
                String topicName = topic.getName();
                messageBuild.append(topicName);
                messageBuild.append("\n");
            });
            commandMassages.add(new CommandMassage(CommandNames.TOPIC_DELETE, "Удалить тему"));
        }
        SendMessage message = CommandUtils.buildInlineKeyBoard(chat.getId(), messageBuild.toString(), commandMassages);
        CommandUtils.sendAnswerWithKeyboard(absSender, message);
    }
}
