package com.example.userms.entity;

import com.example.userms.model.Lambda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "Company_info")
public class Company extends Lambda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nameofCompany ;
    private String domaineofActivity ;
    private String nameofResponsible;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles=new ArrayList<>() ;

    public Company(String email, String password, String address, String phone_number, String role, String image, Long id, String nameofCompany, String domaineofActivity, Collection<AppRole> appRoles) {
        super(email, password, address, phone_number, role, image);
        this.id = id;
        this.nameofCompany = nameofCompany;
        this.domaineofActivity = domaineofActivity;
        this.appRoles = appRoles;
    }
}
