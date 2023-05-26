package com.example.userms;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.User;
import com.example.userms.repository.UserRepository;
import com.example.userms.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class UserMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMsApplication.class, args);
    }
   @Bean
   PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
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
    CommandLineRunner commandLineRunner (UserService userService ){
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());


        User u1 = new User(

                "nedermfarrej@gmail.com",
                "123",
                "cite ouali",
                "55630765",
                "Administrateur",
                null,
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
                "FSB",
                new ArrayList<>()

        );

        User u2 = new User(

                "johndoe@gmail.com",
                "456",
                "123 Main St",
                "555-1234",
                "Utilisateur",
                null,
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
                "NYU" ,new ArrayList<>()
        );

        User u3 = new User(

                "jane.smith@gmail.com",
                "789",
                "456 Park Ave",
                "555-5678",
                "Entreprise",
                null,
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
                "LSE" ,new ArrayList<>() 
        );
        User u4 = new User(

                "janedoe@example.com",
                "password456",
                "456 Elm St",
                "555-987-6543",
                "Entreprise",
                null,
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
                "UCLA",new ArrayList<>()
        );






        return args -> {
            userService.AddRole(new AppRole(1,"user"));
            userService.AddRole(new AppRole(2,"admin"));
            userService.AddRole(new AppRole(3,"superAdmin"));

            userService.SaveUser(u1);
            userService.SaveUser(u2);
            userService.SaveUser(u3);
            userService.SaveUser(u4);

                    //userService.addRoletoUser("Doe","superAdmin");
            //userService.addRoletoUser("neder","admin");
          // userService.addRoletoUser("slim","user");



        };
    }


}
