package com.pawansinghchouhan05.callcustomizer.home.models;

/**
 * Created by Fitterfox-Pawan on 6/23/2016.
 */
public class CustomNumber {
    private String name;
    private long mobileNumber;

    public CustomNumber() {
    }

    public CustomNumber(String name, long mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "CustomNumber{" +
                "name='" + name + '\'' +
                ", mobileNumber=" + mobileNumber +
                '}';
    }

}
