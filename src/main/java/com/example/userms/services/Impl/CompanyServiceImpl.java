package com.example.userms.services.Impl;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import com.example.userms.entity.CustomFile;
import com.example.userms.repository.CustomFileRepository;
import com.example.userms.repository.RoleRepository;
import com.example.userms.repository.companyRepository;
import com.example.userms.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private companyRepository companyRepository;

    @Autowired
    private CustomFileRepository customFileRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
    public long count() {
        return companyRepository.count();
    }

    @Override
    public void update(Company company) {
        Company company1 = companyRepository.findByEmail(company.getEmail());
        if (company.getNameofCompany() != null) {
            company1.setNameofCompany(company.getNameofCompany());
        }
        if (company.getAddress() != null) {
            company1.setAddress(company.getAddress());
        }
        if (company.getAppRoles() != null) {
            company1.setAppRoles(company.getAppRoles());
        }
        if (company.getEmail() != null) {
            company1.setEmail(company.getEmail());
        }
        if (company.getDomaineofActivity() != null) {
            company1.setDomaineofActivity(company.getDomaineofActivity());
        }
        if (company.getNameofResponsible() != null) {
            company1.setNameofResponsible(company.getNameofResponsible());
        }
        if (company.getPassword() != null) {
            company1.setPassword(passwordEncoder.encode(company.getPassword()));
        }
        if (company.getPhone_number() != null) {
            company1.setPhone_number(company.getPhone_number());
        }
        companyRepository.save(company1);
        addRoletoCompany(company1.getEmail(), "company");
        System.out.println(companyRepository.findByEmail(company1.getEmail()));
    }

    @Override
    public void addRoletoCompany(String email, String roleName) {
        Company company = companyRepository.findByEmail(email);
        AppRole appRole = roleRepository.findByRoleName(roleName);
        company.getAppRoles().add(appRole);
        companyRepository.save(company);
        System.out.println(company);
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


    @Override
    public void SaveCompany(Company company) {
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        companyRepository.save(company);
        addRoletoCompany(company.getEmail(), "company");
        System.out.println(companyRepository.findByEmail(company.getEmail()));

    }


    @Override
    public Company saveCompany(MultipartFile picture_file, Long id, String email, String address, String phone_number, String password, String domaineofActivity, String nameofResponsible, String nameofCompany) throws Exception {
        Company company = new Company();
        if (id != null) {
            company = this.companyRepository.findById(id).get();
        }
        company.setEmail(email);
        company.setAddress(address);
        company.setPhone_number(phone_number);
        company.setPassword(password);
        company.setDomaineofActivity(domaineofActivity);
        company.setNameofResponsible(nameofResponsible);
        company.setNameofCompany(nameofCompany);


        if (picture_file != null) {
            String pîcture_fileName = StringUtils.cleanPath(picture_file.getOriginalFilename());
            CustomFile picture = new CustomFile(pîcture_fileName, Base64.getEncoder().encodeToString(picture_file.getBytes()));
            CustomFile savedPicture = this.customFileRepository.save(picture);
            company.setPicture(savedPicture);
        }
        company = this.companyRepository.save(company);

        addRoletoCompany(company.getEmail(), "company");
        System.out.println(company);
        return company;
    }

}
