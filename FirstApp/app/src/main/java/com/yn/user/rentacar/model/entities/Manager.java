package com.yn.user.rentacar.model.entities;

/**
 * Created by USER on 26/11/2017.
 */

public class Manager extends User {

    long branchId;
    public Manager(String lastName, String firstName, String emailAdrs, long id, String phoneNum, String password,long salt,long branchId) throws Exception {
        super(lastName, firstName, emailAdrs, id, phoneNum, password,salt);
        setBranchId(branchId);
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }
}
