package com.example.userms.services;

import com.example.userms.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void SaveUser(User user) ;

    List<User> getAll();

    void deleteByIduser(Long id );
     User login (String email,String password);
     long count();

    Optional<User> findbyId(Long Id);
    }
