package com.example.userms.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userms.Dto.AdminDto;
import com.example.userms.Dto.ClientDto;
import com.example.userms.Dto.CompanyDto;
import com.example.userms.entity.Admin;
import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import com.example.userms.repository.AdminRepository;
import com.example.userms.services.AdminService;
import com.example.userms.services.CompanyService;
import com.example.userms.services.Impl.EmailService;
import com.example.userms.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

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
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AdminService adminService ;




    @GetMapping("/user/all")
    public ResponseEntity<List<Client>> getAll() {

        List<Client> users = userService.getAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/admin/all")
    public List<Admin> getAllAdmins() {

        return adminService.getAllA();

    }


    @PostMapping("/user")
    public ResponseEntity<Void> SaveUser(@RequestBody Client client) {
        userService.SaveUser(client);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/" + client.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/user/image")
    public Client SaveUser(@Nullable @RequestParam(name = "picture_file") MultipartFile picture_file,
                           @Nullable @RequestParam("id") Long id,
                           @Nullable @RequestParam("name") String Name,
                           @Nullable @RequestParam("lastName") String LastName,
                           @Nullable @RequestParam("email") String email,
                           @Nullable @RequestParam("appRoles") String appRoles,
                           @Nullable @RequestParam("password") String password) throws Exception {

        return this.userService.SaveUser(picture_file, id, Name, LastName, email, password);
    }



    @PostMapping("/AddRole")
    public ResponseEntity<Void> AddRole(@RequestBody AppRole appRole) {
        userService.AddRole(appRole);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/AddRole/" + appRole.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{Id}")
    public void deleteByIduser(@PathVariable(name = "Id") Long Id) {
        userService.deleteByIduser(Id);
    }

    @GetMapping("/user/{Id}")
    public Optional<Client> getbyId(@PathVariable(name = "Id") Long Id) {
        return userService.findbyId(Id);
    }

    /*@PostMapping("/user/reset-password/{email")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email) {
    Client c = userService.loadUserByemail(email);
    if (c!=null){

    }

    }*/
    @PostMapping("/user/send-email/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable("email") String email) {
        // Example usage: Sending an email
        String to = email;
        String subject = "Réinitialisation du mot de passe";
        String body = "<html>"
                + "<body>"
                + "<p>Cher utilisateur,</p>"
                + "<p>Nous avons reçu une demande de réinitialisation de votre mot de passe. Veuillez cliquer sur le lien ci-dessous pour procéder au changement de votre mot de passe :</p>"
                + "<p><a href=\"http://localhost:4200/reset-password\">Réinitialiser le mot de passe</a></p>"
                + "<p>Si vous n'avez pas demandé cette réinitialisation de mot de passe, veuillez ignorer cet e-mail.</p>"
                + "<p>Cordialement,<br>"
                + "Ennajim</p>"
                + "</body>"
                + "</html>";

        if (userService.loadUserByemail(email) != null) {
            emailService.sendEmail(to, subject, body);
            return ResponseEntity.ok("Email envoyé avec succès");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
    }


    @GetMapping("/user/count")
    public long UserCounter() {
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

    @PostMapping("/user/AddRoleToClient")
    public ResponseEntity<Void> AddRoleToClient(@RequestBody RoleTouserFORM roleTouserFORM) {
        userService.addRoletoUser(roleTouserFORM.getEmail(), roleTouserFORM.getRoleName());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/user/refreshtoken")
    public void refreshtoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring(7);
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                Client client = userService.loadUserByemail(email);
                String acces_token = JWT.create().withSubject(client.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() +
                                1 * 60 * 1000)).withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", client.getAppRoles().stream().map(AppRole::getRoleName).collect(Collectors.joining()))
                        .sign(algorithm);

//        response.setHeader("access_token",acces_token);
//        response.setHeader("refresh_token",refresh_token);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", acces_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("refresh token is missing");
        }
    }


    @GetMapping("/user/email/{email}")
    public ClientDto getUserByEmail(@PathVariable("email") String email) {
        Client client = userService.getAll().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (client != null) {
            ModelMapper modelMapper = new ModelMapper();
            ClientDto clientDto = modelMapper.map(client, ClientDto.class);
            return clientDto;
        } else {
            return null;
        }

    }




    @GetMapping("/company/all")
    public List<Company> getAllCompany(){
        return companyService.getAllC();
    }

    @GetMapping("/company/count")
    public Long CompanyCount(){
        return companyService.count();
    }

    @GetMapping("/admin/count")
    public long AdminCount(){
        return adminService.Count();
    }


    @GetMapping("/company/{id}")
    public Optional<Company> GetCompanyById(@PathVariable("id") Long id){
        return companyService.getCompanyById(id);
    }

    @PostMapping("/company")
    public ResponseEntity<Void> SaveCompany(@RequestBody Company company) {
        userService.SaveCompany(company);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/" + company.getId()));
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
    @GetMapping("/admin/email/{email}")
    public AdminDto getAdminbyEmail(@PathVariable("email") String email) {
        Admin admin = adminService.getAllA().stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (admin != null) {
            ModelMapper modelMapper = new ModelMapper();
            AdminDto adminDto  = modelMapper.map(admin, AdminDto.class);
            return adminDto;
        } else {
            return null;
        }    }

    @DeleteMapping("/company/{Id}")
    public void deleteByIdCompany(@PathVariable(name = "Id") Long Id) {
        companyService.deleteByIdCompany(Id);
    }
    @PostMapping("/admin")
    public ResponseEntity<Void> Saveadmin(@RequestBody Admin admin) {
        userService.Saveadmin(admin);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/admin/" + admin.getId()));
        System.out.println(admin);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/{Id}")
    public void deleteByIdAdmin(@PathVariable(name = "Id") Long Id) {
          adminService.deleteByIdadmin(Id) ;   }
    @GetMapping("user/check-email/{email}")
    public boolean checkUserEmailExists(@PathVariable String email) {
        return userService.checkIfUserEmailExists(email);
    }
    @GetMapping("company/check-email/{email}")
    public boolean checkcompanyEmailExists(@PathVariable String email) {
        return companyService.checkIfCompanyEmailExists(email);
    }

}

@Data  class RoleTouserFORM {

    private String email;
    private String roleName;
}
