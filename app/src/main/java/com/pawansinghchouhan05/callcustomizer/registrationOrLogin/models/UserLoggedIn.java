package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models;

/**
 * Created by pawan on 12/7/16.
 */
public class UserLoggedIn {
    String name;
    String email;

    public UserLoggedIn() {
    }

    public UserLoggedIn(String name, String email) {
        this.name = name;
        this.email = email;
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

    @Override
    public String toString() {
        return "UserLoggedIn{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
