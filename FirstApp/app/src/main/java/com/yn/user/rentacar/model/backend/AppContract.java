package com.yn.user.rentacar.model.backend;

import android.net.Uri;

/**
 * Created by USER on 07/11/2017.
 */

public class AppContract {

    /**
     * The authority for the contacts provider
     */
    public static final String AUTHORITY = "com.yn.user.rentacar.model.backend.AppContentProvider";
    /**
     * A content:// style uri to the authority for the contacts provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class Branch {

        public static final String BRANCH_ID = "_id";
        public static final String NUMBER_OF_PARKING_SPACES = "number_of_parking_spaces";
        public static final String ADDRESS = "address";
        public static final String IMAGE_URL = "branchImgUrl";
        /**
         * The content:// style URI for this table
         */
        public static final Uri BRANCH_URI = Uri.withAppendedPath(AUTHORITY_URI, "Branch");
    }

    public static class Address {
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String NUMBER = "number";
        /**
         * The content:// style URI for this table
         */
        public static final Uri ADDRESS_URI = Uri.withAppendedPath(AUTHORITY_URI, "Address");
    }

    public static class Car {

        public static final String ID_CAR_NUMBER = "_id";
        public static final String KILOMETRERS = "kilometers";
        public static final String CAR_MODEL_ID = "carModelID";
        public static final String BRANCH_NUM = "branchNum";


        
        /**
         * The content:// style URI for this table
         */
        public static final Uri CAR_URI = Uri.withAppendedPath(AUTHORITY_URI, "Car");
    }

    public static class CarModel {

        public static final String ID_CAR_MODEL = "_id";
        public static final String COMPENY_NAME = "compenyName";
        public static final String MODEL_NAME = "modelName";
        public static final String ENGINE_COPACITY = "engineCapacity";
        public static final String TRANSMISSION_TYPE = "transmissionType";
        public static final String NUM_OF_SEATS = "numOfSeats";
        public static final String IMG = "carPic";
        public static final String CLASS_OF_CAR = "carClass";
        /**
         * The content:// style URI for this table
         */
        public static final Uri CAR_MODEL_URI = Uri.withAppendedPath(AUTHORITY_URI, "CarModel");
    }

    public static class Order {

        public static final String ORDER_ID="_id";
        public static final String CLIENT_ID="Client_Id";
        public static final String ORDER_STATUS="order_Status";
        public static final String CAR_NUM="carNumber";
        public static final String RENT_DATE="rentDate";
        public static final String RETURN_DATE="returnDate";
        public static final String KILOMETERS_AT_RENT="kilometersAtRent";
        public static final String KILOMETERS_AT_RETURN="kilometersAtReturn";
        public static final String FOULED="fouled";
        public static final String AMOUNT_OF_FOUL="amountOfFoul";
        public static final String FINAL_AMOUNT="finalAmount";

        /**
         * The content:// style URI for this table
         */
        public static final Uri ORDER_URI = Uri.withAppendedPath(AUTHORITY_URI, "Order");
    }
    public static class Client {

        public static final String ID = "_id";
        public static final String LAST_NAME = "lastName";
        public static final String FIRST_NAME = "firstName";
        public static final String EMAIL_ADDR = "emailAdrs";
        public static final String PHONE_NUMBER = "phoneNum";
        public static final String CRADIT_NUMBER = "craditCard";
        public static final String SALT = "salt";
        public static final String PASSWORD = "password";


        /**
         * The content:// style URI for this table
         */
        public static final Uri CLIENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "Client");
    }
    public static class Manager {

        public static final String ID = "_id";
        public static final String LAST_NAME = "lastName";
        public static final String FIRST_NAME = "firstName";
        public static final String EMAIL_ADDR = "emailAdrs";
        public static final String PHONE_NUMBER = "phoneNum";
        public static final String BRANCH_ID = "branchId";
        public static final String SALT = "salt";
        public static final String PASSWORD = "password";

        /**
         * The content:// style URI for this table
         */
        public static final Uri MANAGER_URI = Uri.withAppendedPath(AUTHORITY_URI, "Manager");
    }
}
