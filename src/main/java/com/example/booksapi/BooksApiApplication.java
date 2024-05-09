package com.example.booksapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApiApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("URL", dotenv.get("URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("PASSWORD", dotenv.get("PASSWORD"));

        SpringApplication.run(BooksApiApplication.class, args);
    }
}
