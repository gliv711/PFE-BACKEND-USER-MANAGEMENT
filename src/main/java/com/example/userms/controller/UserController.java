package com.example.userms.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
@Slf4j
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
//

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
    @GetMapping("/refreshtoken")
    public void refreshtoken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader=request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring(7);
                Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT =verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                Client client=userService.loadUserByemail(email);
                String acces_token= JWT.create().withSubject(client.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis()+
                                1*60*1000)).withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",client.getAppRoles().stream().map(AppRole::getRoleName ).collect(Collectors.joining()))
                        .sign(algorithm);

//        response.setHeader("access_token",acces_token);
//        response.setHeader("refresh_token",refresh_token);
                Map<String,String> tokens =new HashMap<>();
                tokens.put("access_token",acces_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);




            } catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> error =new HashMap<>();
                error.put("error_message",exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else {
            throw new RuntimeException("refresh token is missing");
        }
    }
    }


@Data  class RoleTouserFORM {

    private String email;
    private String roleName;
}