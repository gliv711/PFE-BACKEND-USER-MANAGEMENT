package com.example.userms.controller;

import com.example.userms.model.User;
import com.example.userms.repository.UserRepository;
import com.example.userms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired

     private UserService userService;
    @GetMapping("/user/all")
    public List<User> getAll(){

        return userService.getAll();


    }

    @PostMapping("/user")
    public void SaveUser(@RequestBody User user){
         userService.SaveUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteByIduser(@PathVariable(name="id") Long id ){
        userService.deleteByIduser(id);

    }





}