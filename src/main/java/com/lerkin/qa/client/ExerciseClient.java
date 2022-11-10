package com.lerkin.qa.client;

import com.lerkin.qa.bot.command.Navigation;
import com.lerkin.qa.bot.dto.ExerciseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = "exercise-client", url = "localhost:8080")
public interface ExerciseClient {

    @GetMapping(value = Navigation.EXERCISES)
    List<ExerciseDto> getFullExercisesByTopicId(@PathVariable("topicId") Integer topicId);

    @PutMapping(value = Navigation.NOT_ASKED_EXERCISE)
    ExerciseDto askQuestion(@PathVariable("chatId") long chatId);

    @GetMapping(value = Navigation.EXERCISE_BY_ID)
    ExerciseDto getById(@PathVariable("id") int id);

    @GetMapping(value = Navigation.STATISTIC)
    HashMap<String, String> statistic(@PathVariable("chatId") long chatId);
}