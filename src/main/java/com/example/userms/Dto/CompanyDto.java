package com.example.userms.Dto;

import lombok.Data;

@Data
public class CompanyDto {
    private Long id ;
    private String nameofCompany ;
    private String domaineofActivity ;
    private String nameofResponsible;
    private String email ;
    private String address ;
    private String phone_number;
    private String image;
}
