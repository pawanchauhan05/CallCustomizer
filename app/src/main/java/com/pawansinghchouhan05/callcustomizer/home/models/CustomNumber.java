package com.pawansinghchouhan05.callcustomizer.home.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Fitterfox-Pawan on 6/23/2016.
 */
public class CustomNumber implements Parcelable {
    private String email;
    private String name;
    private long customNumber;

    public CustomNumber() {
    }

    public CustomNumber(String name, long mobileNumber) {
        this.name = name;
        this.customNumber = mobileNumber;
    }

    public CustomNumber(String email, String name, long mobileNumber) {
        this.email = email;
        this.name = name;
        this.customNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCustomNumber() {
        return customNumber;
    }

    public void setCustomNumber(long mobileNumber) {
        this.customNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomNumber{" +
                "name='" + name + '\'' +
                ", mobileNumber=" + customNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomNumber that = (CustomNumber) o;

        return customNumber == that.customNumber;

    }

    @Override
    public int hashCode() {
        return (int) (customNumber ^ (customNumber >>> 32));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeLong(this.customNumber);
    }

    protected CustomNumber(Parcel in) {
        this.email = in.readString();
        this.name = in.readString();
        this.customNumber = in.readLong();
    }

    public static final Parcelable.Creator<CustomNumber> CREATOR = new Parcelable.Creator<CustomNumber>() {
        @Override
        public CustomNumber createFromParcel(Parcel source) {
            return new CustomNumber(source);
        }

        @Override
        public CustomNumber[] newArray(int size) {
            return new CustomNumber[size];
        }
    };
}
