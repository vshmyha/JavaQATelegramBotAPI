package com.lerkin.qa.client;

import com.lerkin.qa.bot.command.Navigation;
import com.lerkin.qa.bot.dto.TopicDto;
import com.lerkin.qa.bot.dto.UserTopicDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "topic-client", url = "localhost:8080")
public interface TopicClient {

    @GetMapping(value = Navigation.TOPIC)
    List<TopicDto> getAllTopics();

    @GetMapping(value = Navigation.CHOSEN_TOPICS)
    List<TopicDto> getChosenTopics(@PathVariable("chatId") Long chatId);
    @PostMapping(value = Navigation.CHOSE_TOPIC)
    void choseTopic(@RequestBody UserTopicDto userTopicDto);

    @DeleteMapping(value = Navigation.TOPIC_DELETE)
    List<TopicDto> deleteTopic(@RequestBody UserTopicDto userTopicDto);
}
