package com.yn.user.rentacar.model.entities;

import java.io.Serializable;

/**
 * Created by nissy34 on 07/11/2017.
 */

public class Car implements Serializable {

    private long branchNum;

    private long carModelID;

    private long kilometers;

    private long idCarNumber;

    public Car(long branchNum, long carModelID, long kilometers, long idCarNumber) {
        setBranchNum(branchNum);
        setCarModelID(carModelID);
        setKilometers(kilometers);
        setIdCarNumber(idCarNumber);

    }

    public long getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(long branchNum) {
        this.branchNum = branchNum;
    }

    public long getCarModelID() {
        return carModelID;
    }

    public void setCarModelID(long carModelID) {
        this.carModelID = carModelID;
    }

    public long getKilometers() {
        return kilometers;
    }

    public void setKilometers(long kilometers) {
        this.kilometers = Math.max(kilometers,0);
    }

    public long getIdCarNumber() {
        return idCarNumber;
    }

    public void setIdCarNumber(long idCarNumber) {
        this.idCarNumber = idCarNumber;
    }



}
