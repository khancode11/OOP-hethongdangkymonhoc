package com.example.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("=================================");
        System.out.println(" He thong dang ky mon hoc - Spring Boot Server Active");
        System.out.println(" Endpoint app:    http://localhost:8080/index.html");
        System.out.println("=================================");
    }
}