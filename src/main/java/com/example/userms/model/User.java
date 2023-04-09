package com.example.userms.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Data @ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom ;
    private String prenom;
    private String email ;
    private String mdp ;
    private String Usertype;




}

