package com.example.userms.repository;

import com.example.userms.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Client,Long> {

//    User findByEmailAndPassword (String email,String passwordUser findByEmail(String email);
      Client findByEmail(String email);
      List<Client> findByDomain(String domain);


}
