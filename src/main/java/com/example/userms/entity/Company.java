package com.example.userms.entity;

import com.example.userms.model.Lambda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "Company_info")
public class Company extends Lambda {
    private String NameofCompany ;


}
