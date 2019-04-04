package com.example.yash.projectwork;

/**
 * Created by yash on 1/30/2019.
 */
//to save whatever information we want to save in dB
public class UserInformation {
    public String name;
    public String address;

    public UserInformation(){
    //default constructor
    }

    public UserInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}

