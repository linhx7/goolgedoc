package com.example.googledoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GoogledocApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogledocApplication.class, args);
        System.out.println("123");
    }

}
