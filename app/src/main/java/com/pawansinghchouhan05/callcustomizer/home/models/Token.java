package com.pawansinghchouhan05.callcustomizer.home.models;

/**
 * Created by pawan on 11/7/16.
 */
public class Token {
    private String email;
    private String token;

    public Token() {
    }

    public Token(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "Token{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
