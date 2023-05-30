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


    public Client(Long id, String email, String password, String address, String phone_number, String role, String image, String LastName, String Name,
                  String domain, String region, Date BirthDate, Date StartofStudy, Date EndofStudy, Date StartofWork, Date EndofWork, String university){

        super(email,password,address,phone_number,role,image);
        this.id=id ;
        this.domain = domain;
        this.region = region;
        this.university = university;
        this.Name = Name;
        this.LastName = LastName;
        this.BirthDate = BirthDate;
        this.StartofStudy = StartofStudy;
        this.EndofStudy = EndofStudy;
        this.StartofWork = StartofWork;
        this.EndofWork = EndofWork;



    }
     public Client(String email, String password, String address, String phone_number, String role, String image, Long id, String lastName, String name, String domain, String region, Date BirthDate, Date startofStudy, Date endofStudy, Date startofWork, Date endofWork, String university, Collection<AppRole> appRoles) {
         super(email, password, address, phone_number, role, image);
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



}

