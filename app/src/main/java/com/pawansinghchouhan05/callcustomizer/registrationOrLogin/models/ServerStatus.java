package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models;

/**
 * Created by pawan on 15/7/16.
 */
public class ServerStatus {
    String status;

    public ServerStatus(String status) {
        this.status = status;
    }

    public ServerStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServerStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
