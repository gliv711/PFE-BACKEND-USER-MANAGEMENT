package com.example.userms.services;

import com.example.userms.entity.User;

import java.util.List;

public interface UserService {
    void SaveUser(User user) ;

    List<User> getAll();

    void deleteByIduser(Long id );

}
