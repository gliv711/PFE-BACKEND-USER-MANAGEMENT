package com.example.userms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    public String email;
    public String password;
    public String address ;
    public String phone_number ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="picture_id", referencedColumnName = "id")
    private CustomFile picture;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles=new ArrayList<>() ;
    public Admin(Long id, String email, String password, String address, String phone_number, String image, Collection<AppRole> appRoles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone_number = phone_number;
        this.appRoles = appRoles;
    }
}
