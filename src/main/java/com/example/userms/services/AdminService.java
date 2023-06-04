package com.example.userms.services;

import com.example.userms.entity.Admin;
import com.example.userms.entity.Client;

import java.util.List;

public interface AdminService {


    List<Admin> getAllA() ;


    long Count() ;


    Admin deleteByIdadmin(Long id );


}
