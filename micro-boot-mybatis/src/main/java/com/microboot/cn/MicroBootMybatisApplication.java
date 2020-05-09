package com.microboot.cn;

import org.apache.catalina.core.AprLifecycleListener;
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
public class MicroBootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroBootMybatisApplication.class, args);
    }

}
