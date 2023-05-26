package com.example.userms.entity;



import com.example.userms.model.Lambda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


 @Table(name= "user_info")@Entity @NoArgsConstructor @Data @AllArgsConstructor
public class User extends Lambda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String LastName ;

    private String Name;
    private String username;
    @Column(name="domain")
    private String domain ;
    @Column(name="region")
    private String region ;
    private Date BirthDate;
    private Date StartofStudy;
    private Date EndofStudy;    
    private Date StartofWork;  
    private Date EndofWork;
    private String university ;
     @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles ;





     public User(String email, String password, String address, String phone_number, String role, String image, Long id, String lastName, String username, String domain, String region, Date birthDate, Date startofStudy, Date endofStudy, Date startofWork, Date endofWork, String university, Collection<AppRole> appRoles) {
         super(email, password, address, phone_number, role, image);
         this.id = id;
        this. LastName = lastName;
         this.username=username;
         this.domain = domain;
         this.region = region;
        this. BirthDate = birthDate;
        this. StartofStudy = startofStudy;
       this.  EndofStudy = endofStudy;
       this.  StartofWork = startofWork;
         this.EndofWork = endofWork;
         this.university = university;
         this.appRoles = appRoles;
     }
 }

