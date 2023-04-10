package com.example.userms.services;

import com.example.userms.model.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {
    void SaveUser(User user) ;

    List<User> getAll();

    void deleteByIduser(Long id );

}
