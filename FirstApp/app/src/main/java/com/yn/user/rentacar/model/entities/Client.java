package com.yn.user.rentacar.model.entities;

import android.util.Patterns;

import com.yn.user.rentacar.model.backend.SHA_256_Helper;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by USER on 07/11/2017.
 */

    public class Client extends User {

        private long craditNumber;

        public Client(String lastName, String firstName, String emailAdrs, long id, String phoneNum, long craditNumber,long salt, String password) throws Exception {
            super(lastName, firstName, emailAdrs, id, phoneNum, password,salt);
            setCraditNumber(craditNumber);
            }



        public long getCraditNumber() {
            return craditNumber;
        }

        public void setCraditNumber(long craditNumber) {
            if(craditNumber>0)
             this.craditNumber = craditNumber;
            else
                throw new IllegalArgumentException("Cradit card Number");

        }


}
