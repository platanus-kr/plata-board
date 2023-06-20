package org.platanus.webboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlataBoardV2Application {

    public static void main(String[] args) {
        SpringApplication.run(PlataBoardV2Application.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            System.out.println("Out now!");
        };
    }
}
