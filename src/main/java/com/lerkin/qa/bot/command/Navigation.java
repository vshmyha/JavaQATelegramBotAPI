package com.lerkin.qa.bot.command;

public interface Navigation {

    String TOPIC = "/topic";
    String EXERCISE = "/exercise";
    String SCHEDULE_TIME = "/scheduletime";
    String CHOSE_TOPIC = TOPIC + "/chose";
    String TOPIC_DELETE = TOPIC + "/delete";
    String CHOSEN_TOPICS = CHOSE_TOPIC + "/{chatId}";
    String EXERCISES = EXERCISE + TOPIC + "/{topicId}";
    String NOT_ASKED_EXERCISE = EXERCISE + "/question/ask/{chatId}";
    String EXERCISE_BY_ID = EXERCISE + "/{id}";
    String STATISTIC = EXERCISE + "/statistic/{chatId}";
    String SCHEDULE_TIME_BY_CHAT_ID = SCHEDULE_TIME + "/{chatId}";
}
