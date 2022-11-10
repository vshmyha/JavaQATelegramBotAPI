package com.lerkin.qa.client;

import com.lerkin.qa.bot.command.Navigation;
import com.lerkin.qa.bot.dto.ScheduleTimeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "schedule-time-client", url = "localhost:8080")
public interface ScheduleTimeClient {

    @PostMapping(value = Navigation.SCHEDULE_TIME)
    void saveScheduleTimeSetting(@RequestBody ScheduleTimeDto scheduleTimeDto);

    @GetMapping(value = Navigation.SCHEDULE_TIME_BY_CHAT_ID)
    ScheduleTimeDto getScheduleTime(@PathVariable("chatId") long chatId);
}

