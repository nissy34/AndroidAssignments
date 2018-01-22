package com.yn.user.rentacar.model.backend;

import com.yn.user.rentacar.model.datasource.List_DBManager;
import com.yn.user.rentacar.model.datasource.SQL_DBManager;

/**
 * Created by USER on 12/11/2017.
 */

public class DBManagerFactory {

    static DB_manager manager =null;

    public static DB_manager getManager() {
        if (manager == null)
            manager = new SQL_DBManager();
        return manager;
    }

}
