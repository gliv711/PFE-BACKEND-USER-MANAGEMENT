package com.example.userms.repository;

import com.example.userms.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface companyRepository extends JpaRepository<Company,Long> {
   Company findByEmail(String email);
}
