package com.example.upla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UplaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UplaApplication.class, args);
    }

}
