package com.example.userms.controller;

import com.example.userms.entity.User;
import com.example.userms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "USER-MANAGEMENT/api")
@CrossOrigin
public class UserController {
    @Autowired
     private UserService userService;
    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Void> SaveUser(@RequestBody User user) {
        userService.SaveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/" + user.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/user/{Id}")
    public void deleteByIduser(@PathVariable(name="Id") Long Id) {
        userService.deleteByIduser(Id);
    }


    @GetMapping("/forAdmin")
    public String ForAdmin(){
        return "this url is only accesible to admin " ;
    }

    @GetMapping("/forUser")
    public String ForUser(){
        return "this url is only accesible to user " ;
    }






}