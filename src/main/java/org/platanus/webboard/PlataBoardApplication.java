package org.platanus.webboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ConfigurationPropertiesScan("org.platanus.webboard.config")
public class PlataBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlataBoardApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            System.out.println("Out now!");
        };
    }
}
