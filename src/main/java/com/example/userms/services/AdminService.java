package com.example.userms.services;

import com.example.userms.entity.Admin;
import com.example.userms.entity.Client;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {


    List<Admin> getAllA() ;


    long Count() ;


    Admin deleteByIdadmin(Long id );
    void  update(Admin admin);
    void addRoletoadmin(String email,String roleName );
    void Saveadmin(Admin admin) ;
    public Admin SaveAdmin(MultipartFile picture_file,Long id,String address,String email,String password,String phone_number) throws Exception ;



}
