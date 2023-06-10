package com.example.userms.services.Impl;

import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import com.example.userms.entity.CustomFile;
import com.example.userms.repository.CustomFileRepository;
import com.example.userms.repository.companyRepository;
import com.example.userms.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private companyRepository companyRepository ;

    @Autowired
    private CustomFileRepository customFileRepository ;
    @Override
    public List<Company> getAllC() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Optional<Company> getCompanyByEmail(String email) {
        return Optional.ofNullable(companyRepository.findByEmail(email));
    }

    @Override
    public Company deleteByIdCompany(Long id) {
        companyRepository.deleteById(id);
        return null;
    }

    @Override
    public long count(){
        return companyRepository.count();
    }

    public boolean checkIfCompanyEmailExists(String email) {
        Company user = companyRepository.findByEmail(email);
        return user != null;
    }

    public Company updateCompany(MultipartFile pictureFile, Long id, String nameofCompany, String domaineofActivity,
                                 String email, String nameofResponsible, String address, String phoneNumber,
                                 String appRoles, String password) throws Exception {

        // Retrieve the existing company from the database based on the given id
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new Exception("Company not found"));

        // Update the company properties with the provided values
        existingCompany.setNameofCompany(nameofCompany);
        existingCompany.setDomaineofActivity(domaineofActivity);
        existingCompany.setEmail(email);
        existingCompany.setNameofResponsible(nameofResponsible);
        existingCompany.setAddress(address);
        existingCompany.setPhone_number(phoneNumber);
        existingCompany.setPassword(password);

        // Handle the picture file, if provided
        if (pictureFile != null) {
            String pictureFileName = StringUtils.cleanPath(pictureFile.getOriginalFilename());
            CustomFile picture = new CustomFile(pictureFileName, Base64.getEncoder().encodeToString(pictureFile.getBytes()));
            CustomFile savedPicture = customFileRepository.save(picture);
            existingCompany.setPicture(savedPicture);
        }

        // Save the updated company to the database
        Company updatedCompany = companyRepository.save(existingCompany);

        return updatedCompany;
    }




}
