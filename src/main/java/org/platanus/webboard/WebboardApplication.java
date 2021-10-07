package org.platanus.webboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebboardApplication.class, args);
    }

}
