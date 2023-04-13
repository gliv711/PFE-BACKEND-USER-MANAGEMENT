package com.example.userms.controller;

import com.example.userms.model.User;
import com.example.userms.repository.UserRepository;
import com.example.userms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired

     private UserService userService;
    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MyHeader", "MyValue");
        return new ResponseEntity<>(users, headers, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Void> SaveUser(@RequestBody User user) {
        userService.SaveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/" + user.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteByIduser(@PathVariable(name="id") Long id) {
        userService.deleteByIduser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}