package com.pawansinghchouhan05.callcustomizer.home.models;

/**
 * Created by Fitterfox-Pawan on 6/24/2016.
 */
public class User {
    private String email;
    private String name;
    private String token;
    private String date;
    private String uniqueID;

    public User() {
    }

    public User(String email, String name, String token, String date, String uniqueID) {
        this.email = email;
        this.name = name;
        this.token = token;
        this.date = date;
        this.uniqueID = uniqueID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", date='" + date + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                '}';
    }
}
