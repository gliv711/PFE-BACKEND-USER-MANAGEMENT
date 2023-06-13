package com.example.userms.controller;

import com.example.userms.entity.Admin;
import com.example.userms.entity.Client;
import com.example.userms.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/admin")
    public ResponseEntity<Void> Saveadmin(@RequestBody Admin admin) {
        adminService.Saveadmin(admin);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/" + admin.getId()));
        System.out.println(admin);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @PostMapping("/admin/image")
    public Admin SaveUser(@Nullable @RequestParam(name = "picture_file") MultipartFile picture_file,
                           @Nullable @RequestParam("id") Long id,
                           @Nullable @RequestParam("address") String address,
                           @Nullable @RequestParam("email") String email,
                           @Nullable @RequestParam("appRoles") String appRoles,
                           @Nullable @RequestParam("phone_number") String phone_number,
                           @Nullable @RequestParam("password") String password) throws Exception {

        return this.adminService.SaveAdmin(picture_file,id,address,email,password,phone_number);
    }
    @PutMapping("/admin/update")
    public  void updateadmin(@RequestBody Admin admin){
        adminService.update(admin);
    }
    @GetMapping("/admin/count")
    public long AdminCount() {
        return adminService.Count();
    }
    @DeleteMapping("/admin/{Id}")
    public void deleteByIdAdmin(@PathVariable(name = "Id") Long Id) {
        adminService.deleteByIdadmin(Id) ;   }
    @GetMapping("/admin/all")
    public List<Admin> getAllAdmins() {

        return adminService.getAllA();

    }

}
