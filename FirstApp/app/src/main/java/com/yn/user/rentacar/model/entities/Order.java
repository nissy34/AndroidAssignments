package com.yn.user.rentacar.model.entities;

/**
 * Created by nissy34 on 07/11/2017.
 */
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{

    private  long idOrderNum;

    private  long clientId;

    private  long carNumber;

    private Date rentDate;

    private Date returnDate;

    private  long kilometersAtRent;

    private  long  kilometersAtReturn;

    private  Boolean fouled;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private  Boolean status;

    private  long amountOfFoul;

    private  long finalAmount;

    public Order(long idOrderNum, long clientId, long carNumber, Date rentDate, Date returnDate, long kilometersAtRent, long kilometersAtReturn, Boolean fouled, Boolean status, long amountOfFoul, long finalAmount) {
        this.idOrderNum = idOrderNum;
        this.clientId = clientId;
        this.carNumber = carNumber;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.kilometersAtRent = kilometersAtRent;
        this.kilometersAtReturn = kilometersAtReturn;
        this.fouled = fouled;
        this.status = status;
        this.amountOfFoul = amountOfFoul;
        this.finalAmount = finalAmount;
    }


    public long getIdOrderNum() {
        return idOrderNum;
    }

    public void setIdOrderNum(long idOrderNum) {
        this.idOrderNum = idOrderNum;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(long carNumber) {
        this.carNumber = carNumber;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public long getKilometersAtRent() {
        return kilometersAtRent;
    }

    public void setKilometersAtRent(long kilometersAtRent) {
        this.kilometersAtRent = kilometersAtRent;
    }

    public long getKilometersAtReturn() {
        return kilometersAtReturn;
    }

    public void setKilometersAtReturn(long kilometersAtReturn) {
        this.kilometersAtReturn = kilometersAtReturn;
    }

    public Boolean getFouled() {
        return fouled;
    }

    public void setFouled(Boolean fouled) {
        this.fouled = fouled;
    }

    public long getAmountOfFoul() {
        return amountOfFoul;
    }

    public void setAmountOfFoul(long amountOfFoul) {
        this.amountOfFoul = amountOfFoul;
    }

    public long getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(long finalAmount) {
        this.finalAmount = finalAmount;
    }
}
