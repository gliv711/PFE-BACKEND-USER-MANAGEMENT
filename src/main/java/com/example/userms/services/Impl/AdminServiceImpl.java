package com.example.userms.services.Impl;

import com.example.userms.entity.Admin;
import com.example.userms.repository.AdminRepository;
import com.example.userms.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository ;

    @Override
    public List<Admin> getAllA() {

        return adminRepository.findAll();
    }

    @Override
    public long Count(){
        return adminRepository.count();
    }



    @Override
    public Admin deleteByIdadmin(Long id) {
        adminRepository.deleteById(id);
        return null;
    }

}
