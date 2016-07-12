package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models;

/**
 * Created by pawan on 12/7/16.
 */
public class UserLoginForm {
    String email;
    String password;

    public UserLoginForm() {
    }

    public UserLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
