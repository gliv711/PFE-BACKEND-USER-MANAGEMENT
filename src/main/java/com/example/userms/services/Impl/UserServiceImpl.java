package com.example.userms.services.Impl;

import com.example.userms.entity.User;
import com.example.userms.repository.UserRepository;
import com.example.userms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository ;
    @Override
    public void SaveUser(User user) {


        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteByIduser(Long Id) {
        userRepository.deleteById(Id);
    }

    public User login (String email,String password){
        return userRepository.findByEmailAndPassword(email,password);
    }



}
