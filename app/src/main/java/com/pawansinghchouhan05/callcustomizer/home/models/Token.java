package com.pawansinghchouhan05.callcustomizer.home.models;

/**
 * Created by pawan on 11/7/16.
 */
public class Token {
    private String email;
    private String token;
    private long date;

    public Token() {
    }

    public Token(String email, String token, long date) {
        this.email = email;
        this.token = token;
        this.date = date;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Token{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", date=" + date +
                '}';
    }
}
