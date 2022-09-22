package com.example.petplaystore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
public class PetPlayStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetPlayStoreApplication.class, args);
    }

}
