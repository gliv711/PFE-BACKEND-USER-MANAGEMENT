package com.example.userms.services.Impl;

import com.example.userms.entity.Company;
import com.example.userms.repository.companyRepository;
import com.example.userms.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private companyRepository companyRepository ;
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


}
