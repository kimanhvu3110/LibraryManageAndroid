package com.example.myfinalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private String email;
    private String userName;
    private String userFullName;
    private String passWord;
    private String userRole;
    private String phone;
    private String gender;

    public Account() {
    }

    public Account(String email, String userRole) {
        this.email = email;
        this.userRole = userRole;
    }

    public Account(String userName, String userFullName, String passWord, String userRole, String phone, String email, String gender) {
        this.userName = userName;
        this.userFullName = userFullName;
        this.passWord = passWord;
        this.userRole = userRole;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    public Account(String email) {
        this.email = email;
    }

    protected Account(Parcel in) {
        email = in.readString();
        userName = in.readString();
        userFullName = in.readString();
        passWord = in.readString();
        userRole = in.readString();
        phone = in.readString();
        gender = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userRole='" + userRole + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(userFullName);
        dest.writeString(passWord);
        dest.writeString(userRole);
        dest.writeString(phone);
        dest.writeString(gender);
    }
}
