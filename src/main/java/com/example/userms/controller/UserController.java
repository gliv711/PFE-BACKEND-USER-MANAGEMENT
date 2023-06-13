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
import com.example.userms.model.UserRandomInfo;
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
import javax.servlet.http.HttpSession;
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
    private AdminService adminService;
    @Autowired
    private HttpSession session;


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
    @PutMapping("/user/update")
    public  void updateuser(@RequestBody Client client){
  userService.update(client);
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

    @PutMapping("/user")
    public Client updateUser(
            @RequestParam(name = "picture_file", required = false) MultipartFile pictureFile,
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) throws Exception {

        return this.userService.SaveUser(pictureFile, id, name, lastName, email, password);
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
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        // Example usage: Sending an email
        String to = email;
        String subject = "Réinitialisation du mot de passe";

        String body = "Bonjour voici votre code" + randomNumber;
        UserRandomInfo userRandomInfo = new UserRandomInfo();
        userRandomInfo.setEmail(email);
        userRandomInfo.setRandomNumber(randomNumber);

        session.setAttribute("userRandomInfo", userRandomInfo);
        System.out.println(userRandomInfo.getRandomNumber());
        System.out.println(userRandomInfo.getEmail());



        if (userService.loadUserByemail(email) != null) {
            emailService.sendEmail(to, subject, body);
            return ResponseEntity.ok("Email envoyé avec succès   " + " "+randomNumber);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
    }
    @PostMapping("/user/verify-random-number/{number}/{email}")
    public ResponseEntity<String> verifyRandomNumber(
            @PathVariable("number") int number,
            @PathVariable("email") String email,
            HttpSession session) {

        UserRandomInfo userRandomInfo = (UserRandomInfo) session.getAttribute("userRandomInfo");

        if (userRandomInfo != null &&
                number == userRandomInfo.getRandomNumber() &&
                email.equals(userRandomInfo.getEmail())) {
            // La valeur entrée par l'utilisateur correspond au randomNumber et à l'email
            // Faites ici ce que vous devez faire lorsque la vérification réussit
            return ResponseEntity.ok("Le numéro et l'email sont corrects !");
        } else {
            // La valeur entrée par l'utilisateur ne correspond pas au randomNumber ou à l'email
            // Faites ici ce que vous devez faire lorsque la vérification échoue
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le numéro ou l'email est incorrect !");
        }
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
        }
    }





    @GetMapping("/user/check-email/{email}")
    public boolean checkUserEmailExists(@PathVariable String email) {
        return userService.checkIfUserEmailExists(email);
    }






}


    @Data
    class RoleTouserFORM {

        private String email;
        private String roleName;
    }


//
//@PutMapping("/company")
//    public Company updateCompany(
//            @RequestParam(name = "picture_file") MultipartFile pictureFile,
//            @RequestParam("id") Long id,
//            @RequestParam("nameofCompany") String nameofCompany,
//            @RequestParam("domaineofActivity") String domaineofActivity,
//            @RequestParam("email") String email,
//            @RequestParam("nameofResponsible") String nameofResponsible,
//            @RequestParam("address") String address,
//            @RequestParam("phone_number") String phoneNumber,
//            @RequestParam("appRoles") String appRoles,
//            @RequestParam("password") String password) throws Exception {
//
//        return this.companyService.updateCompany(pictureFile, id, nameofCompany, domaineofActivity, email, nameofResponsible, address, phoneNumber, appRoles, password);
//    }
//
//    @GetMapping("/company/email/{email}")
//    public CompanyDto getCompanyByEmail(@PathVariable("email") String email) {
//        Company company = companyService.getAllC().stream()
//                .filter(c -> c.getEmail().equals(email))
//                .findFirst()
//                .orElse(null);
//
//        if (company != null) {
//            ModelMapper modelMapper = new ModelMapper();
//            CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);
//            return companyDto;
//        } else {
//            return null;
//        }
//
//    }
//    @GetMapping("/admin/email/{email}")
//    public AdminDto getAdminbyEmail(@PathVariable("email") String email) {
//        Admin admin = adminService.getAllA().stream()
//                .filter(c -> c.getEmail().equals(email))
//                .findFirst()
//                .orElse(null);
//
//        if (admin != null) {
//            ModelMapper modelMapper = new ModelMapper();
//            AdminDto adminDto  = modelMapper.map(admin, AdminDto.class);
//            return adminDto;
//        } else {
//            return null;
//        }    }
//

//    @GetMapping("company/check-email/{email}")
//    public boolean checkcompanyEmailExists(@PathVariable String email) {
//        return companyService.checkIfCompanyEmailExists(email);
//    }
//
//}


