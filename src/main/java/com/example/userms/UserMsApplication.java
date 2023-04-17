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

import java.util.Arrays;
import java.util.Collections;

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
        User u1 = new User(null, "mfarrej", "neder", "nedermfarrej@gmail.com", "123","Employée");
        User u2 = new User(null, "Naghmouchi", "karim", "karimnaghmouchi@gmail.com", "123","Employeur");
        User u3 = new User(null, "jelidi", "dali", "dalijelidi@gmail.com", "123","Employeur");
        User u4 = new User(null, "Hedhli", "khalil", "hedhlikhalil@gmail.com", "123","Employeur");
        User admin = new User(null, "admin", "admin", "admin@admin.com", "admin","admin");
        User root = new User(null, "root", "root", "root", "root","root");


        return args -> {
            UserRepository.save(u1);
            UserRepository.save(u2);
            UserRepository.save(u3);
            UserRepository.save(u4);
            UserRepository.save(admin);
            UserRepository.save(root);


        };
    }


}
