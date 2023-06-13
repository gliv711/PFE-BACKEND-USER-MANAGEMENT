package com.example.userms.controller;

import com.example.userms.Dto.CompanyDto;
import com.example.userms.entity.Company;
import com.example.userms.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(value = "/api")
public class CompanyController {
    @Autowired
    private CompanyService companyService;


    @DeleteMapping("/company/{Id}")
    public void deleteByIdCompany(@PathVariable(name = "Id") Long Id) {
        companyService.deleteByIdCompany(Id);
    }

    @PostMapping("/company")
    public ResponseEntity<Void> SaveCompany(@RequestBody Company company) {
        companyService.SaveCompany(company);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/company/" + company.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/company/email/{email}")
    public CompanyDto getCompanyByEmail(@PathVariable("email") String email) {
        Company company = companyService.getAllC().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (company != null) {
            ModelMapper modelMapper = new ModelMapper();
            CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
            return companyDto;
        } else {
            return null;
        }

    }

    @GetMapping("/company/{id}")
    public Optional<Company> GetCompanyById(@PathVariable("id") Long id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/company/all")
    public List<Company> getAllCompany() {
        return companyService.getAllC();
    }

    @GetMapping("/company/count")
    public Long CompanyCount() {
        return companyService.count();
    }





    @PutMapping("/company/update")
    public  void updateCompany(@RequestBody Company company){
        companyService.update(company);
    }



    @PostMapping("/company/image")
    public Company SaveCompany(@Nullable @RequestParam(name = "picture_file") MultipartFile picture_file,
                            @Nullable @RequestParam("id") Long id,
                               @Nullable @RequestParam("address") String address,
                               @Nullable @RequestParam("domaineofActivity") String domaineofActivity,
                               @Nullable @RequestParam("email") String email,
                               @Nullable @RequestParam("nameofCompany") String nameofCompany,
                               @Nullable @RequestParam("nameofResponsible")String nameofResponsible,
                            @Nullable @RequestParam("phone_number") String phone_number,
                            @Nullable @RequestParam("password") String password) throws Exception {

        return this.companyService.saveCompany(picture_file, id, email, password,address,phone_number,domaineofActivity,nameofCompany,nameofResponsible);
    }

}
