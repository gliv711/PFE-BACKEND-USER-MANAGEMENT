package com.example.userms.entity;



import com.example.userms.model.Lambda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;


 @Table(name= "user_info")@Entity @NoArgsConstructor @Data
public class User extends Lambda {

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


    public User(Long id,String email,String password,String address,String phone_number,String role,String image,String LastName,String Name,
                String domain,String region,Date BirthDate,Date StartofStudy,Date EndofStudy, Date StartofWork,Date EndofWork,String university){
        super(id,email,password,address,phone_number,role,image);

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

}

