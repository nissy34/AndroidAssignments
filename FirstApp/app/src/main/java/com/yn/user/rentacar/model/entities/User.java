package com.yn.user.rentacar.model.entities;

import android.util.Patterns;
import com.yn.user.rentacar.model.backend.SHA_256_Helper;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by USER on 26/11/2017.
 */

public class User implements Serializable {

    //region Properties
    private String lastName;
    private String firstName;
    private String emailAdrs;
    private long id;
    private final long salt;
    private String password;
    private String phoneNum;
    //endregion


    //region Constructor
    public User(String lastName, String firstName, String emailAdrs, long id, String phoneNum,String password,long salt) throws Exception {


        this.salt=salt;
        setPassword(password);
        setLastName(lastName);
        setFirstName(firstName);
        setEmailAdrs(emailAdrs);
        setId(id);
        setPhoneNum(phoneNum);
    }


    //endregion

    //region Getters & Setters
    public String getLastName() {
        return lastName;
    }

    public long getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {

        this.password=password;
    }

    public void setLastName(String lastName) {
        if(!lastName.isEmpty())
            this.lastName = lastName;
        else//TODO no empty last name
            this.lastName = "";



    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(!firstName.isEmpty())
            this.firstName = firstName;
        else
            this.firstName="";//TODO no empty first name
    }

    public String getEmailAdrs() {
        return emailAdrs;
    }

    public void setEmailAdrs(String emailAdrs) {
        if(Patterns.EMAIL_ADDRESS.matcher(emailAdrs).matches())
            this.emailAdrs = emailAdrs;
        else
            throw new IllegalArgumentException("email address");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNum() {

        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        if(Patterns.PHONE.matcher(phoneNum).matches())
            this.phoneNum = phoneNum;
        else
            throw new IllegalArgumentException("phone number");
    }
    //endregion



}
