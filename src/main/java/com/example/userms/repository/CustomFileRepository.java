package com.example.userms.repository;

import com.example.userms.entity.CustomFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFileRepository extends JpaRepository<CustomFile,Long> {
}
