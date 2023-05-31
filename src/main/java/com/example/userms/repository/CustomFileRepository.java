package com.example.userms.repository;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.CustomFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFileRepository extends JpaRepository<CustomFile,Long> {
}
