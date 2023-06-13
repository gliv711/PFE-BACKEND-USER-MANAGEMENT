package com.example.userms.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class UserRandomInfo {


    private String email;
    private int randomNumber;
    public UserRandomInfo(String email, int randomNumber) {
        this.email = email;
        this.randomNumber = randomNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }
}
