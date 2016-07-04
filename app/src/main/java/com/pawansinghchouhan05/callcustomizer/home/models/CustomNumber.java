package com.pawansinghchouhan05.callcustomizer.home.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Fitterfox-Pawan on 6/23/2016.
 */
public class CustomNumber implements Serializable {
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

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomNumber that = (CustomNumber) o;

        if (mobileNumber != that.mobileNumber) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomNumber that = (CustomNumber) o;

        return mobileNumber == that.mobileNumber;

    }

    @Override
    public int hashCode() {
        return (int) (mobileNumber ^ (mobileNumber >>> 32));
    }
}
