package org.platanus.webboard;

import org.platanus.webboard.controller.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebboardApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            System.out.println("Out now!");
        };
    }
}
