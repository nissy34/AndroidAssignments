package com.yn.user.cliantapplication.model.entities;

import java.io.Serializable;

/**
 * Created by USER on 07/11/2017.
 */

public class Branch implements Serializable{


    private  long branchID;

    private  int numberOfParkingSpaces;

    private Address branchAddress;

    private String branchImgUrl;


    public Branch(long branchID, int numberOfParkingSpaces, Address branchAddress, String branchImgUrl) {

        this.branchID = branchID;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
        this.branchAddress = branchAddress;
        this.branchImgUrl=branchImgUrl;
    }


    public long getBranchID() {
        return branchID;
    }

    public String getBranchImgUrl() {
        return branchImgUrl;
    }

    public void setBranchImgUrl(String branchImgUrl) {
        this.branchImgUrl = branchImgUrl;
    }

    public void setBranchID(long branchID) {
        this.branchID = branchID;
    }

    public int getNumberOfParkingSpaces() {
        return numberOfParkingSpaces;
    }

    public void setNumberOfParkingSpaces(int numberOfParkingSpaces) {
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    public Address getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(Address branchAddress) {
        this.branchAddress = branchAddress;
    }

}
