package com.example.userms;

import com.example.userms.entity.User;
import com.example.userms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class UserMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMsApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
    @Bean
    CommandLineRunner commandLineRunner (UserRepository UserRepository){
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());


        User u1 = new User(
                null,
                "nedermfarrej@gmail.com",
                "123",
                "cite ouali",
                "55630765",
                "Administrateur",
                null,
                "Mfarrej",
                "Neder",
                "IT",
                "PARIS",
                date,
                date,
                date,
                date,
                date,
                "FSB"
        );

        User u2 = new User(
                null,
                "johndoe@gmail.com",
                "456",
                "123 Main St",
                "555-1234",
                "Utilisateur",
                null,
                "John",
                "Doe",
                "Marketing",
                "New York",
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                "NYU"
        );

        User u3 = new User(
                null,
                "jane.smith@gmail.com",
                "789",
                "456 Park Ave",
                "555-5678",
                "Entreprise",
                null,
                "Jane",
                "Smith",
                "Finance",
                "London",
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                "LSE"
        );
        User u4 = new User(
                null,
                "janedoe@example.com",
                "password456",
                "456 Elm St",
                "555-987-6543",
                "Entreprise",
                null,
                "Jane",
                "Doe",
                "Finance",
                "Los Angeles",
                new Date(2022, 8, 1),
                new Date(2023, 7, 31),
                new Date(2022, 8, 15),
                new Date(2023, 7, 15),
                new Date(),
                "UCLA"
        );

        User u5 = new User(
                null,
                "bobsmith@example.com",
                "password789",
                "789 Oak St",
                "555-555-5555",
                "Administrateur",
                null,
                "Bob",
                "Smith",
                "Human Resources",
                "Chicago",
                new Date(2022, 6, 1),
                new Date(2023, 5, 31),
                new Date(2022, 7, 15),
                new Date(2023, 5, 15),
                new Date(),
                "UIC"
        );

        return args -> {
            UserRepository.save(u1);
            UserRepository.save(u2);
            UserRepository.save(u3);
            UserRepository.save(u4);
            UserRepository.save(u5);



        };
    }


}
