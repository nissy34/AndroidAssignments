package com.yn.user.cliantapplication.model.entities;

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
