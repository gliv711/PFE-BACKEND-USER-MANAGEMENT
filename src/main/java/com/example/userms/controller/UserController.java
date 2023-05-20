package com.example.userms.controller;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class UserController {
    @Autowired
     private UserService userService;
    @GetMapping("/user/all")
    public ResponseEntity<List<Client>> getAll() {

        List<Client> users = userService.getAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PostMapping("/user")
    public ResponseEntity<Void> SaveUser(@RequestBody Client client) {
        userService.SaveUser(client);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/" + client.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @PostMapping("/AddRole")
    public ResponseEntity<Void> AddRole(@RequestBody AppRole appRole) {
        userService.AddRole(appRole);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/AddRole/" + appRole.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{Id}")
    public void deleteByIduser(@PathVariable(name="Id") Long Id) {
        userService.deleteByIduser(Id);
    }

    @GetMapping("/user/{Id}")
    public Optional<Client> getbyId(@PathVariable(name="Id") Long Id){
        return userService.findbyId(Id);
    }



    @GetMapping("/forAdmin")
    public String ForAdmin(){
        return "this url is only accesible to admin " ;
    }

    @GetMapping("/forUser")
    public String ForUser(){
        return "this url is only accesible to user " ;
    }

    @GetMapping("/user/count")
    public long UserCounter(){
        return userService.count();
    }

   /* @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.login(email, password);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }*/

    @PostMapping("/AddRoleToClient")
    public ResponseEntity<Void>AddRoleToClient (@RequestBody RoleTouserFORM roleTouserFORM) {
        userService.addRoletoUser(roleTouserFORM.getEmail(),roleTouserFORM.getRoleName());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
//    @PostMapping("/AddRoleToClient")
//    public void refreshtoken (HttpServletRequest request, HttpServletResponse response) {
//        String authorizationHeader=request.getHeader(AUTHORIZATION);
//
//    }

}
@Data  class RoleTouserFORM {

    private String email;
    private String roleName;
}