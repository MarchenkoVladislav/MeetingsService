package ru.marchenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Vladislav Marchenko
 */
@SpringBootApplication
@EnableScheduling
public class MeetingsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingsApplication.class, args);
    }
}

