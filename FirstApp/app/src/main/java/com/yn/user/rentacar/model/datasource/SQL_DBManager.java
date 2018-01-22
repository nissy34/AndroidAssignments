package com.yn.user.rentacar.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.yn.user.rentacar.model.backend.AppContract;
import com.yn.user.rentacar.model.backend.DB_manager;
import com.yn.user.rentacar.model.entities.Branch;
import com.yn.user.rentacar.model.entities.Car;
import com.yn.user.rentacar.model.entities.CarModel;
import com.yn.user.rentacar.model.entities.Client;
import com.yn.user.rentacar.model.entities.Manager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nissy34 on 10/12/2017.
 */

public class SQL_DBManager implements DB_manager {

    private final String WEB_URL="http://nheifetz.vlab.jct.ac.il/TakeAndGo/";
    public void printLog(String message)
    {
        Log.d(this.getClass().getName(),"\n"+message);
    }
    public void printLog(Exception message)
    {
        Log.d(this.getClass().getName(),"Exception-->\n"+message);
    }
    @Override
    public boolean hasManager(long manager_id) {
        try {
            printLog("has manager");
            return getManager(manager_id).getCount() > 0;
        } catch (Exception e) {
          printLog(e);
          return false;
        }
    }

    @Override
    public long addClient(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "addClient.php", values);
            long id = Long.parseLong(result);
            printLog("addClient:\n" + result);
            return id;
        } catch (Exception e) {
            printLog("addClient Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long addCarModel(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "addCarModel.php", values);
            long id = Long.parseLong(result);
            printLog("addCarModel:\n" + result);
            return id;
        } catch (Exception e) {
            printLog("addCarModel Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long addCar(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "addCar.php", values);
            long id = Long.parseLong(result);
            printLog("addCar:\n" + result);
            return id;
        } catch (Exception e) {
            printLog("addCar Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long addBranch(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "addBranch.php", values);
            long id = Long.parseLong(result);
            printLog("addBranch:\n" + result);
            return id;
        } catch (Exception e) {
            printLog("addBranch Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long addManager(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "addManager.php", values);
            long id = Long.parseLong(result);
            printLog("addManager:\n" + result);
            return id;
        } catch (Exception e) {
            printLog("addManager Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public boolean removeClient(long id) {
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(AppContract.Client.ID,id);
            String result=PHPtools.POST(WEB_URL + "deleteclient.php", contentValues);
            printLog("removeClient:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("removeClient Exception:\n" + e);
            return false;
        }
    }


    @Override
    public boolean removeCarModel(long id) {
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(AppContract.CarModel.ID_CAR_MODEL,id);
            String result=PHPtools.POST(WEB_URL + "deleteCarModel.php", contentValues);
            printLog("removeCarModel:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("removeCarModel Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean removeCar(long id) {
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(AppContract.Car.ID_CAR_NUMBER,id);
            String result=PHPtools.POST(WEB_URL + "deleteCar.php", contentValues);
            printLog("removeCar:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("removeCar Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean removeBranch(long id) {
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(AppContract.Branch.BRANCH_ID,id);
            String result=PHPtools.POST(WEB_URL + "deleteBranches.php", contentValues);
            printLog("removeBranch:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("removeBranch Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean removeManager(long id) {
        try {
            ContentValues contentValues=new ContentValues();
            contentValues.put(AppContract.Manager.ID,id);
            String result=PHPtools.POST(WEB_URL + "deleteManager.php", contentValues);
            printLog("removeManager:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("removeManager Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean updateClient(long id, ContentValues values) {
        return false;
    }

    @Override
    public boolean updateCar(long id, ContentValues values) {
        try {
            String result=PHPtools.POST(WEB_URL + "updateCar.php", values);
            printLog("updateCar:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("updateCar Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean updateCarModel(long id, ContentValues values) {
        try {
            String result=PHPtools.POST(WEB_URL + "updateCarModel.php", values);
            printLog("updateCarModel:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("updateCarModel Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean updateBranch(long id, ContentValues values) {
        /*try {
            String result=PHPtools.POST(WEB_URL + "updateBranch.php", values);
            printLog("updateCarModel:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("updateCarModel Exception:\n" + e);
            return false;
        }*/
        return false;
    }

    @Override
    public boolean updateManager(long id, ContentValues values) {
        try {
            String result=PHPtools.POST(WEB_URL + "updateManager.php", values);
            printLog("updateManager:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("updateManager Exception:\n" + e);
            return false;
        }
    }

    @Override
    public Cursor getManager(long id) {
        List<Manager> result = new ArrayList<>();

        try {
            ContentValues where=new ContentValues();
            where.put(AppContract.Manager.ID,id);
            String str = PHPtools.POST(WEB_URL + "managers.php",where);
            JSONArray array = new JSONObject(str).getJSONArray("managers");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToManager(contentValues));

            }
            return Tools.managerListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }


    @Override
    public Cursor getCar(long id) {
        List<Car> result = new ArrayList<>();

        try {
            ContentValues where=new ContentValues();
            where.put(AppContract.Car.ID_CAR_NUMBER,id);
            String str = PHPtools.POST(WEB_URL + "cars.php",where);
            JSONArray array = new JSONObject(str).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToCar(contentValues));

            }
            return Tools.CarListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }

    @Override
    public Cursor getCarModel(long id) {
        List<CarModel> result = new ArrayList<>();
        try {
            ContentValues where=new ContentValues();
            where.put(AppContract.CarModel.ID_CAR_MODEL,id);
            String str = PHPtools.POST(WEB_URL + "carModels.php",where);
            JSONArray array = new JSONObject(str).getJSONArray("car_models");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToCarModel(contentValues));

            }
            return Tools.carModelsListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }

    @Override
    public Cursor getCarModels() {
        List<CarModel> result = new ArrayList<>();

        try {

            String str = PHPtools.GET(WEB_URL + "carModels.php");
            JSONArray array = new JSONObject(str).getJSONArray("car_models");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToCarModel(contentValues));

            }
            return Tools.carModelsListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }

    @Override
    public Cursor getClients() {
        List<Client> result = new ArrayList<>();

        try {

            String str = PHPtools.GET(WEB_URL + "clients.php");
            JSONArray array = new JSONObject(str).getJSONArray("clients");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToClient(contentValues));

            }
            return Tools.clientListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }

    @Override
    public Cursor getBranches() {
        List<Branch> result = new ArrayList<>();

        try {

        String str = PHPtools.GET(WEB_URL + "branches.php");
        JSONArray array = new JSONObject(str).getJSONArray("branches");
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);

            ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
            result.add(Tools.ContentValuesToBranch(contentValues));

        }
        return Tools.branchListToCursor(result);
    } catch (Exception e) {
        e.printStackTrace();     }
        return null;

    }

    @Override
    public Cursor getCars() {
        List<Car> result = new ArrayList<>();

        try {

            String str = PHPtools.GET(WEB_URL + "cars.php");
            JSONArray array = new JSONObject(str).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToCar(contentValues));

            }
            return Tools.CarListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }

    @Override
    public Cursor getManagers() {
        List<Manager> result = new ArrayList<>();

        try {

            String str = PHPtools.GET(WEB_URL + "managers.php");
            JSONArray array = new JSONObject(str).getJSONArray("managers");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                result.add(Tools.ContentValuesToManager(contentValues));

            }
            return Tools.managerListToCursor(result);
        } catch (Exception e) {
            e.printStackTrace();     }
        return null;
    }
}
