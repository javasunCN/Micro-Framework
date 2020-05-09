package com.microboot.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;

@SpringBootApplication(exclude = {
        OAuth2ResourceServerAutoConfiguration.class,
        TaskExecutionAutoConfiguration.class,
        TaskSchedulingAutoConfiguration.class,
        WebSocketServletAutoConfiguration.class
})
public class MicroBootJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroBootJdbcApplication.class, args);
    }

}
