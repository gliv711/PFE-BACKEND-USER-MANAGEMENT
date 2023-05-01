package com.example.userms.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Lambda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id ;
    @Column(name = "Email")
    public String email;
    @Column(name = "password")
    public String password;
    @Column(name="address")
    public String address ;
    @Column(name="telephone")
    public String phone_number ;
    public String role;
    @Lob
    @Column(name = "Image")
    public String image;

}
