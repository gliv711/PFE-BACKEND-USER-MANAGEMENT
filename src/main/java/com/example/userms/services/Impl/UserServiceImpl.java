package com.example.userms.services.Impl;

import com.example.userms.entity.Admin;
import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import com.example.userms.repository.AdminRepository;
import com.example.userms.repository.RoleRepository;
import com.example.userms.repository.UserRepository;
import com.example.userms.repository.companyRepository;
import com.example.userms.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository  ;
    private final RoleRepository roleRepository ;
    private final PasswordEncoder passwordEncoder;
    private final companyRepository companyRepository;
    private final AdminRepository adminRepository;



    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, com.example.userms.repository.companyRepository companyRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.adminRepository = adminRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client=userRepository.findByEmail(email);
       if (client==null){
           Company company = companyRepository.findByEmail(email);
           if (company==null){
               Admin admin =adminRepository.findByEmail(email);
               if(admin==null){
                   throw new RuntimeException("not foud");

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



     client.setPassword(passwordEncoder.encode(client.getPassword()));
       userRepository.save(client);

        addRoletoUser(client.getEmail(),"user");
       System.out.println(userRepository.findByEmail(client.getEmail()));

    }

    @Override
    public void SaveCompany(Company company) {
         company.setPassword(passwordEncoder.encode(company.getPassword()));
        companyRepository.save(company);
         addRoletoCompany(company.getEmail(),"company");
        System.out.println(companyRepository.findByEmail(company.getEmail()));

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
        return user != null;
    }

}
