package com.example.userms.services;

import com.example.userms.entity.Client;
import com.example.userms.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {


    List<Company> getAllC();

    Optional<Company> getCompanyById(Long id) ;

    Optional<Company> getCompanyByEmail(String email);
    Company deleteByIdCompany(Long id );
    boolean checkIfCompanyEmailExists(String email);


    long count();

}
