package com.example.userms.services;

import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CompanyService {


    List<Company> getAllC();

    Optional<Company> getCompanyById(Long id) ;

    Optional<Company> getCompanyByEmail(String email);
    Company deleteByIdCompany(Long id );
    boolean checkIfCompanyEmailExists(String email);


    long count();
    void  update(Company company);
    void addRoletoCompany(String email,String roleName );

    Company updateCompany(MultipartFile pictureFile, Long id, String nameofCompany, String domaineofActivity, String email, String nameofResponsible, String address, String phoneNumber, String appRoles, String password) throws Exception;
}
