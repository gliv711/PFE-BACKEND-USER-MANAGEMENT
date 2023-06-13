package com.example.userms.services.Impl;

import com.example.userms.entity.*;
import com.example.userms.repository.*;
import com.example.userms.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static org.apache.tomcat.jni.SSL.setPassword;

@Service
@Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository  ;
    private final RoleRepository roleRepository ;
    private final PasswordEncoder passwordEncoder;
    private final companyRepository companyRepository;
    private final AdminRepository adminRepository;
    private final CustomFileRepository customFileRepository;




    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, com.example.userms.repository.companyRepository companyRepository, AdminRepository adminRepository,CustomFileRepository customFileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.adminRepository = adminRepository;
        this.customFileRepository = customFileRepository;

    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client=userRepository.findByEmail(email);
       if (client==null){
           Company company = companyRepository.findByEmail(email);
           if (company==null){
               Admin admin =adminRepository.findByEmail(email);
               if(admin==null){
                   throw new RuntimeException("not found");

               }
               Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
               admin.getAppRoles().forEach(appRole -> {
                   authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
               });
               return new User(admin.getEmail(),admin.getPassword(),authorities);
           }
           Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
           company.getAppRoles().forEach(appRole -> {
               authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
           });
           return new User(company.getEmail(),company.getPassword(),authorities);
       }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
       client.getAppRoles().forEach(appRole -> {
           authorities.add(new SimpleGrantedAuthority(appRole.getRoleName()));
       });
        return new User(client.getEmail(),client.getPassword(),authorities);
    }


    @Override
    public void SaveUser(Client client) {
        System.out.println(client);
        userRepository.delete(client);

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        userRepository.save(client);

        addRoletoUser(client.getEmail(),"user");
        System.out.println(userRepository.findByEmail(client.getEmail()));

    }

    @Override
    public Client SaveUser (MultipartFile picture_file, Long id, String Name, String LastName, String email,  String password) throws Exception {
        Client client =new Client();
          if(id!=null){
                 client = this.userRepository.findById(id).get();

          }
          client.setName(Name);
          client.setLastName(LastName);
          client.setEmail(email);
          client.setPassword(password);

        if (picture_file != null) {
            String pîcture_fileName = StringUtils.cleanPath(picture_file.getOriginalFilename());
            CustomFile picture = new CustomFile(pîcture_fileName, Base64.getEncoder().encodeToString(picture_file.getBytes()));
            CustomFile savedPicture = this.customFileRepository.save(picture);
            client.setPicture(savedPicture);
        }


        client = this.userRepository.save(client);

        addRoletoUser(client.getEmail(), "user");
            System.out.println(client);
        return client;


    }




//    public void SaveCompany(MultipartFile picture_file,Long id, String nameofCompany, String domaineofActivity,String nameofResponsible, String email, Collection<AppRole> appRoles, String password) throws IOException {
//
//        System.out.println(appRoles);
//        Company company = new Company(id, nameofCompany, domaineofActivity,nameofResponsible,email, null, password);
//        if (picture_file != null) {
//            String pîcture_fileName = StringUtils.cleanPath(picture_file.getOriginalFilename());
//            CustomFile picture = new CustomFile(pîcture_fileName, Base64.getEncoder().encodeToString(picture_file.getBytes()));
//            CustomFile savedPicture = this.customFileRepository.save(picture);
//            company.setPicture(savedPicture);
//        }
//        company = this.companyRepository.save(company);
//
//
//    }




    @Override
    public Company updateCompany(MultipartFile picture_file, Long id, String email, String address, String phone_number, String password, String domaineofActivity, String nameofResponsible, String nameofCompany) {
        return new Company();
    }


    public void Saveadmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        addRoletoadmin(admin.getEmail(),"admin");
        System.out.println(companyRepository.findByEmail(admin.getEmail()));





    }

    @Override
    public List<Client> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Client deleteByIduser(Long Id) {
        userRepository.deleteById(Id);
        return null;
    }

//    public User login (String email,String password){
//        return userRepository.findByEmailAndPassword(email,password);
//
//
//    }

    public long count(){
        return userRepository.count();
    }

    public Optional<Client> findbyId(Long Id){
        return userRepository.findById(Id);
    }

    @Override
    public AppRole AddRole(AppRole appRole) {
        return roleRepository.save(appRole );

    }

    @Override
    public void addRoletoUser(String email, String roleName) {
        Client user= userRepository.findByEmail(email);
        AppRole appRole=roleRepository.findByRoleName(roleName);
        user.getAppRoles().add(appRole);
        userRepository.save(user);
    }


    @Override
    public void addRoletoCompany(String email, String roleName) {
        Company company=companyRepository.findByEmail(email);
        AppRole appRole=roleRepository.findByRoleName(roleName);
        company.getAppRoles().add(appRole);
        companyRepository.save(company);
        System.out.println(company);

    }


    @Override
    public Client loadUserByemail(String email) {

        return  userRepository.findByEmail(email);

    }

    @Override
    public void addRoletoadmin(String email, String roleName) {
        Admin admin=adminRepository.findByEmail(email);
        AppRole appRole=roleRepository.findByRoleName(roleName);
        admin.getAppRoles().add(appRole);
        adminRepository.save(admin);
    }

    @Override
    public Admin getAdminByEmail(String email){
        return adminRepository.findByEmail(email);
    }
    public boolean checkIfUserEmailExists(String email) {
        Client user = userRepository.findByEmail(email);
        Company company = companyRepository.findByEmail(email);
        Admin admin = adminRepository.findByEmail(email);

        if (user == null && company == null && admin == null) {
            return false;
        }

        return true;
    }


    @Override
    public void update(Client client) {
Client client1 =userRepository.findByEmail(client.email);
if(client.getName()!=null){
    client1.setName(client.getName());
}
if(client.getPassword()!=null){
    client1.setPassword(passwordEncoder.encode(client.getPassword()));
}
if(client.getDomain()!=null){
    client1.setDomain(client.getDomain());
}
if(client.getLastName()!=null){
    client1.setLastName(client.getLastName());
}
if(client.getRegion()!=null){
    client1.setRegion(client.getRegion());
}
if(client.getUniversity()!=null){
    client1.setUniversity(client.getUniversity());
}
if(client.getAddress()!=null){
    client1.setAddress(client.getAddress());
}
 if(client.getPhone_number()!=null){
     client1.setPhone_number(client.getPhone_number());

 }
if(client.getEmail()!=null){
   client1.setEmail(client.getEmail());
}
if(client.getAppRoles()!=null){
    client1.setAppRoles(client.getAppRoles());
}
if(client.getBirthDate()!=null){
    client1.setBirthDate(client.getBirthDate());
}
if(client.getEndofStudy()!=null){
            client1.setEndofStudy(client.getEndofStudy());
        }
 if(client.getStartofStudy()!=null){
     client1.setStartofStudy(client.getStartofStudy());

 }
 if(client.getStartofWork()!=null){
     client1.setStartofWork(client.getStartofWork());
 }
 if(client.getEndofWork()!=null){
     client1.setEndofWork(client.getEndofWork());
 }
 userRepository.save(client1);
        addRoletoUser(client1.getEmail(),"user");
        System.out.println(userRepository.findByEmail(client1.getEmail()));


    }




}
