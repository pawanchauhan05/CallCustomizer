package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models;

/**
 * Created by pawan on 12/7/16.
 */
public class UserLoggedIn {
    String name;
    String email;
    int numberStatus;

    public UserLoggedIn() {
    }

    public UserLoggedIn(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserLoggedIn(String name, String email, int numberStatus) {
        this.name = name;
        this.email = email;
        this.numberStatus = numberStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberStatus() {
        return numberStatus;
    }

    public void setNumberStatus(int numberStatus) {
        this.numberStatus = numberStatus;
    }

    @Override
    public String toString() {
        return "UserLoggedInService{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
