package com.example.userms.Dto;

import com.example.userms.entity.CustomFile;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
@Data
public class ClientDto {
    private String email ;
    public String address ;
    public String phone_number ;
    private String LastName;
    private String Name;
    private String domain ;
    private String region ;
    private Date BirthDate;
    private Date StartofStudy;
    private Date EndofStudy;
    private Date StartofWork;
    private Date EndofWork;
    private String university ;
    private CustomFile picture;
}
