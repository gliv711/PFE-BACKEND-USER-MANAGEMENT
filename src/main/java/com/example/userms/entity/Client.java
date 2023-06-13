package com.example.userms.entity;



import com.example.userms.model.Lambda;
import com.example.userms.entity.AppRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


 @Table(name= "user_info")@Entity @NoArgsConstructor @Data
public class Client extends Lambda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String LastName ;

    private String Name;
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
     private Collection<AppRole> appRoles=new ArrayList<>() ;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name="picture_id", referencedColumnName = "id")
     private CustomFile picture;

     public Client(String email, String password, String address, String phone_number, String role, Long id, String lastName, String name, String domain, String region, Date BirthDate, Date startofStudy, Date endofStudy, Date startofWork, Date endofWork, String university, Collection<AppRole> appRoles) {
         super(email, password, address, phone_number, role);
         this.id = id;
         LastName = lastName;
         Name = name;
         this.domain = domain;
         this.region = region;
         this.BirthDate = BirthDate;
         StartofStudy = startofStudy;
         EndofStudy = endofStudy;
         StartofWork = startofWork;
         EndofWork = endofWork;
         this.university = university;
         this.appRoles = appRoles;
     }
     public Client(Long id, String Name, String LastName, String email, Collection<AppRole> appRoles,String password){
         this.id = id;
         this.Name = Name;
         this.LastName = LastName;
         this.email = email;
         this.appRoles = appRoles;
         this.password = password;
     }



 }

