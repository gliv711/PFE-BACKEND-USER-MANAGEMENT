package com.example.userms.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
@Builder
public class Lambda  {

    public String email;
    public String password;

    public String address ;

    public String phone_number ;

    public String role;



}
