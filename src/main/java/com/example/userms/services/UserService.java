package com.example.userms.services;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void SaveUser(Client client) ;

    List<Client> getAll();

    void deleteByIduser(Long id );
    //     User login (String email,String password);
    long count();

    Optional<Client> findbyId(Long Id);
    AppRole AddRole(AppRole appRole);
    void addRoletoUser(String email,String roleName );
    Client loadUserByemail(String email);


}
