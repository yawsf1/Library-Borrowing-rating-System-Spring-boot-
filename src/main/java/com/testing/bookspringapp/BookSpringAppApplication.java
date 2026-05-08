package com.testing.bookspringapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookSpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSpringAppApplication.class, args);
    }

}
