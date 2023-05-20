package com.example.userms.repository;

import com.example.userms.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole,Long> {
    AppRole  findByRoleName(String roleName);
}