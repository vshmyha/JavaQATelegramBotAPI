package com.lerkin.qa.bot.command;

import com.lerkin.qa.bot.dto.UserTopicDto;
import com.lerkin.qa.bot.utils.CommandUtils;
import com.lerkin.qa.client.FeignExceptionDecoder;
import com.lerkin.qa.client.TopicClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ChoseTopicCommand extends BotCommand {

    private static final String commandIdentifier = "chosetopic";

    private final TopicClient topicClient;

    public ChoseTopicCommand(TopicClient topicClient) {
        super(commandIdentifier, CommandNames.CHOSE_TOPIC);
        this.topicClient = topicClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        Integer topicId = Integer.parseInt(arguments[0]);
        String topicName = Arrays.stream(arguments).skip(1).collect(Collectors.joining(" "));
        UserTopicDto userTopicDto = new UserTopicDto(chat.getId(), topicId);
        try {
            topicClient.choseTopic(userTopicDto);
            CommandUtils.sendAnswer(absSender, chat.getId(), topicName + " - тема была добавлена.");
        } catch (Exception e) {
            String messageText = FeignExceptionDecoder.exceptionMessageParser(e.getMessage());
            CommandUtils.sendAnswer(absSender, chat.getId(), topicName + " - " + messageText + "\n Хотите увидеть добавленные вами темы? /showchosentopics");
        }
    }
}