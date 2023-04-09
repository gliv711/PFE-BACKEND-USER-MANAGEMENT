package com.example.userms.controller;

import com.example.userms.model.User;
import com.example.userms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    UserRepository userRepository ;

    @PostMapping("/user")
    public void user(@RequestBody User user){
        userRepository.save(user);
    }

    @DeleteMapping("/user/{id}")
    public void user(@PathVariable(name="id") Long id ){
        userRepository.deleteById(id);
    }


    @GetMapping("/user/all")
    public List<User> getAll(){
        return userRepository.findAll();
    }


}