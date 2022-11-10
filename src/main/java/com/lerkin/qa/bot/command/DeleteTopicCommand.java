package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.TopicDto;
import com.lerkin.qa.bot.dto.UserTopicDto;
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
import java.util.Arrays;
import java.util.List;

@Component
public class DeleteTopicCommand extends BotCommand {

    private static final String commandIdentifier = "deletetopic";
    private final TopicClient topicClient;

    public DeleteTopicCommand(TopicClient topicClient) {
        super(commandIdentifier, CommandNames.TOPIC_DELETE);
        this.topicClient = topicClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        Integer topicId = Integer.valueOf(arguments[0]);
        StringBuilder topicName = new StringBuilder();
        Arrays.stream(arguments).skip(1).forEach(name -> topicName.append(name + " "));
        UserTopicDto userTopicDto = new UserTopicDto(chat.getId(), topicId);
        List<TopicDto> topics = topicClient.deleteTopic(userTopicDto);
        List<CommandMassage> commandMassages = new ArrayList<>();
        commandMassages.add(new CommandMassage(CommandNames.CHOSE_TOPIC, "Добавить тему"));
        StringBuilder messageBuild = new StringBuilder();
        messageBuild.append("Тема \"" +topicName + "\": была удалена из вашего списка. Весь прогресс по этой теме тоже был удалён.\n");
        messageBuild.append("Оставшийся список тем: \n");
        if (topics.isEmpty()) {
            messageBuild.append("У вас пока нет выбранных тем.");
        } else {
            topics.forEach(topic -> {
                messageBuild.append(topic.getName());
                messageBuild.append("\n");
                commandMassages.add(new CommandMassage(CommandNames.TOPIC_DELETE, "Удалить тему"));
            });
        }
        SendMessage sendMessage = CommandUtils.buildInlineKeyBoard(chat.getId(), messageBuild.toString(), commandMassages);
        CommandUtils.sendAnswerWithKeyboard(absSender, sendMessage);
    }
}
