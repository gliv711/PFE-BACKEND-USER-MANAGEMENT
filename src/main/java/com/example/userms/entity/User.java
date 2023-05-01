package com.example.userms.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
@Table(name= "user_info")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id ;
    private String LastName ;
    private String Name;
    @Column(name = "Email")
    private String email ;
    @Column(name = "password")

    private String password ;

    private String Role;




}

