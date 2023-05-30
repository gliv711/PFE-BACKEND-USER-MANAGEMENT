package com.example.userms.repository;

import com.example.userms.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByEmail(String email);
}

