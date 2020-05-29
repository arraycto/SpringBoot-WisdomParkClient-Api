package com.niit.soft.client.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableRabbit //开启基于注解的RabbitMQ模式
//@ComponentScan(basePackages = {"com.niit.soft.client.api"})
//@EnableAsync  //作用于启动类，放置在启动类上开启异步任务注解
@EnableScheduling   //开启定时
@SpringBootApplication
public class ClientApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApiApplication.class, args);
    }
}
