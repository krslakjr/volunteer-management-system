package com.example.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Čitanje SQL skripte iz resources foldera
        String sql = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("data.sql").toURI())), StandardCharsets.UTF_8);
        
        // Izvršavanje SQL skripte
        jdbcTemplate.execute(sql);
    }
}