package com.example.userms.services;

import com.example.userms.entity.Admin;
import com.example.userms.entity.AppRole;
import com.example.userms.entity.Client;
import com.example.userms.entity.Company;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void SaveCompany(Company company) ;
    Company saveCompany(MultipartFile picture_file, Long id , String email , String address,String phone_number,String password,String domaineofActivity , String nameofResponsible,String nameofCompany)throws Exception;

    void SaveUser(Client client) ;

    Client SaveUser(MultipartFile picture_file, Long id, String Name, String LastName, String email, String password) throws Exception;
    Client SaveUser(MultipartFile picture_file, String email, String password, String address, String phone_number, Long id, String lastName, String name, String domain, String region, Date BirthDate, Date startofStudy, Date endofStudy, Date startofWork, Date endofWork, String university) throws Exception;


    List<Client> getAll();

    Client deleteByIduser(Long id );
    //     User login (String email,String password);
    long count();

    Optional<Client> findbyId(Long Id);
    AppRole AddRole(AppRole appRole);
    void addRoletoUser(String email,String roleName );
    void addRoletoCompany(String email,String roleName );
    Client loadUserByemail(String email);
    void addRoletoadmin(String email,String roleName );
    void Saveadmin(Admin admin) ;
    boolean checkIfUserEmailExists(String email);
   void  update(Client client);
    public Company updateCompany(MultipartFile picture_file, Long id, String email, String address, String phone_number, String password, String domaineofActivity, String nameofResponsible, String nameofCompany);


    Admin getAdminByEmail(String email);
    List<Client> getUsersBydomain(String domain) ;

}
