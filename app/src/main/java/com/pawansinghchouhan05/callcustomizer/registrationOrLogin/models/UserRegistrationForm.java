package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models;

/**
 * Created by pawan on 12/7/16.
 */
public class UserRegistrationForm {
    String name;
    String email;
    String password;
    String loginType;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String name, String email, String password, String loginType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.loginType = loginType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "UserRegistrationForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loginType='" + loginType + '\'' +
                '}';
    }
}
