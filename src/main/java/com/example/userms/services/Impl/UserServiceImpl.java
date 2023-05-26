package com.example.userms.services.Impl;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.repository.RoleRepository;
import com.example.userms.repository.UserRepository;
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



    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client=userRepository.findByEmail(email);
       if (client==null){
           log.error("user not found in the database");
           throw new UsernameNotFoundException("user not found in the database");
       }else {
           log.info("user  found in the database");
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
    }

    @Override
    public List<Client> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteByIduser(Long Id) {
        userRepository.deleteById(Id);
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
    }

    @Override
    public Client loadUserByemail(String email) {

        return  userRepository.findByEmail(email );

    }

}
