package com.lerkin.qa.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleTimeDto {

    int id;
    long chatId;
    Time time;
}
