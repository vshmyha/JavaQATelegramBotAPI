package com.lerkin.qa.bot.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.lerkin.qa")
@ComponentScan(basePackages = "com.lerkin.qa")
public class BotAutoConfiguration {
}
