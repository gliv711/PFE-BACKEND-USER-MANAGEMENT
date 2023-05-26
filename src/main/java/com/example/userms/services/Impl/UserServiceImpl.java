package com.example.userms.services.Impl;

import com.example.userms.entity.AppRole;
import com.example.userms.entity.User;
import com.example.userms.repository.RoleRepository;
import com.example.userms.repository.UserRepository;
import com.example.userms.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository  ;
    private final RoleRepository roleRepository ;
    private PasswordEncoder passwordEncoder ;



    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void SaveUser(User user) {

      String password= user.getPassword();
     user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
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

    public Optional<User> findbyId(Long Id){
        return userRepository.findById(Id);
    }

    @Override
    public AppRole AddRole(AppRole appRole) {
       return roleRepository.save(appRole );

    }

    @Override
    public void addRoletoUser(String email, String roleName) {
     User user= userRepository.findByEmail(email);
     AppRole appRole=roleRepository.findByRoleName(roleName);
      user.getAppRoles().add(appRole);
    }

    @Override
    public User loadUserByemail(String email) {

        return  userRepository.findByEmail(email );

    }

}
