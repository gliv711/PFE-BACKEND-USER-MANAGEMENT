package com.example.userms.services;

import com.example.userms.entity.Admin;
import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void SaveCompany(Company company) ;


    void SaveUser(Client client) ;
    Client SaveUser(MultipartFile picture_file, Long id, String Name, String LastName, String email, Collection<AppRole> appRoles, String password) throws Exception;



    List<Client> getAll();

    Client deleteByIduser(Long id );
    //     User login (String email,String password);
    long count();

    Optional<Client> findbyId(Long Id);
    AppRole AddRole(AppRole appRole);
    void addRoletoUser(String email,String roleName );
    void addRoletoCompany(String email,String roleName );
    Client loadUserByemail(String email);
    void addRoletoadmin(String email,String roleName );
    void Saveadmin(Admin admin) ;




}
