package com.example.userms.services.Impl;

import com.example.userms.entity.Admin;
import com.example.userms.entity.AppRole;
import com.example.userms.entity.Company;
import com.example.userms.repository.AdminRepository;
import com.example.userms.repository.RoleRepository;
import com.example.userms.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository ;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

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

    @Override
    public void update(Admin admin) {
        Admin admin1=adminRepository.findByEmail(admin.getEmail());


        if(admin.getPassword()!=null){
            admin1.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        if(admin.getAppRoles()!=null){
            admin1.setAppRoles(admin.getAppRoles());
        }
        if(admin.getAddress()!=null){
            admin1.setAddress(admin.getAddress());
        }
        if(admin.getPhone_number()!=null){
            admin1.setPhone_number(admin.getPhone_number());
        }
        adminRepository.save(admin1);
        addRoletoadmin(admin1.getEmail(),"admin");
        System.out.println(adminRepository.findByEmail(admin1.getEmail()));


    }

    @Override
    public void addRoletoadmin(String email, String roleName) {
        Admin admin=adminRepository.findByEmail(email);
        AppRole appRole=roleRepository.findByRoleName(roleName);
        admin.getAppRoles().add(appRole);
        adminRepository.save(admin);

    }

    @Override
    public void Saveadmin(Admin admin) {

            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            adminRepository.save(admin);
            addRoletoadmin(admin.getEmail(),"admin");
            System.out.println(adminRepository.findByEmail(admin.getEmail()));






    }

}
