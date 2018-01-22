package com.yn.user.cliantapplication.model.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DB_manager;
import com.yn.user.cliantapplication.model.backend.SimpleLocation;
import com.yn.user.cliantapplication.model.entities.Branch;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarModel;
import com.yn.user.cliantapplication.model.entities.Client;
import com.yn.user.cliantapplication.model.entities.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nissy34 on 10/12/2017.
 */

public class SQL_DBManager implements DB_manager {


    static List<Car> availableCars;
    static List<Car> cars;
    static List<CarModel> carModels;
    static List<Client> clients;
    static List<Order> orders;
    static List<Branch> branches;

    private boolean isUpdatedClient;
    private boolean isUpdatedOrder;
    private boolean isUpdatedCar;


    static {

        availableCars = new ArrayList<>();
        cars = new ArrayList<>();
        carModels = new ArrayList<>();
        clients = new ArrayList<>();
        orders = new ArrayList<>();
        branches = new ArrayList<>();
    }

    public SQL_DBManager() {
        this.isUpdatedClient = false;
        this.isUpdatedOrder = false;
        this.isUpdatedCar = false;
    }

    public void printLog(String message) {
        Log.d(this.getClass().getName(), "\n" + message);
    }

    public void printLog(Exception message) {
        Log.d(this.getClass().getName(), "Exception-->\n" + message);
    }


    @Override
    public void updateCarModellist() {

        List<CarModel> temp = carModels;
        carModels = new ArrayList<>();
        try {
            ContentValues where = new ContentValues();
            String str = PHPtools.POST(AppContract.WEB_URL + "carModels.php", where);
            JSONArray array = new JSONObject(str).getJSONArray("car_models");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                carModels.add(Tools.ContentValuesToCarModel(contentValues));

            }

        } catch (Exception e) {
            e.printStackTrace();
            carModels = temp;
        }
    }

    @Override
    public void updateOrderList() {

        List<Order> temp = orders;
        orders = new ArrayList<>();

        try {
            String str = PHPtools.GET(AppContract.WEB_URL + "orders.php");
            JSONArray array = new JSONObject(str).getJSONArray("orders");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                orders.add(Tools.ContentValuesToOrder(contentValues));

            }

        } catch (Exception e) {
            e.printStackTrace();
            isUpdatedOrder = true;
            orders = temp;
        }
    }

    @Override
    public void updateCarlist() {
        List<Car> temp = cars;
        cars = new ArrayList<>();

        try {

            String str = PHPtools.GET(AppContract.WEB_URL + "cars.php");
            JSONArray array = new JSONObject(str).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                cars.add(Tools.ContentValuesToCar(contentValues));
            }

        } catch (Exception e) {
            e.printStackTrace();
            isUpdatedCar = true;
            cars = temp;
        }

    }

    @Override
    public void updateAvailablecarList() {
        List<Car> temp = availableCars;
        availableCars = new ArrayList<>();

        try {

            String str = PHPtools.GET(AppContract.WEB_URL + "availableCars.php");
            JSONArray array = new JSONObject(str).getJSONArray("cars");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                availableCars.add(Tools.ContentValuesToCar(contentValues));
            }

        } catch (Exception e) {
            e.printStackTrace();
            isUpdatedCar = true;
            availableCars = temp;
        }

    }

    @Override
    public void updateBranchesList() {

        List<Branch> temp = branches;
        branches = new ArrayList<>();

        try {

            String str = PHPtools.GET(AppContract.WEB_URL + "branches.php");
            JSONArray array = new JSONObject(str).getJSONArray("branches");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                branches.add(Tools.ContentValuesToBranch(contentValues));

            }
        } catch (Exception e) {
            e.printStackTrace();
            branches = temp;
        }
    }

    @Override
    public void updateClientList() {

        List<Client> temp = clients;

        clients = new ArrayList<>();

        try {

            String str = PHPtools.GET(AppContract.WEB_URL + "clients.php");
            JSONArray array = new JSONObject(str).getJSONArray("clients");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                ContentValues contentValues = PHPtools.JsonToContentValues(jsonObject);
                clients.add(Tools.ContentValuesToClient(contentValues));

            }
        } catch (Exception e) {
            e.printStackTrace();
            clients = temp;
            isUpdatedClient = true;
        }
    }


    @Override
    public boolean hasClient(long client_id) {
        try {
            printLog("has client");
            return (getClient(client_id) != null);
        } catch (Exception e) {
            printLog(e);
        }
        return false;
    }

    @Override
    public long addClient(ContentValues values) {
        try {
            String result = PHPtools.POST(AppContract.WEB_URL + "addClient.php", values);
            long id = Long.parseLong(result);
            printLog("addClient:\n" + result);
            isUpdatedClient = true;
            return id;
        } catch (Exception e) {
            printLog("addClient Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public long addOrder(ContentValues values) {
        try {
            String result = PHPtools.POST(AppContract.WEB_URL + "addOrder.php", values);
            long id = Long.parseLong(result);
            printLog("addOrder:\n" + result);
            isUpdatedOrder = true;
            isUpdatedCar = true;
            return id;
        } catch (Exception e) {
            printLog("addOrder Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public boolean removeClient(long id) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AppContract.Client.ID, id);
            String result = PHPtools.POST(AppContract.WEB_URL + "deleteclient.php", contentValues);
            isUpdatedClient = true;
            printLog("removeClient:\n" + result);
            return true;
        } catch (Exception e) {
            printLog("removeClient Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean updateClient(long id, ContentValues values) {
        try {
            String result = PHPtools.POST(AppContract.WEB_URL + "updateClient.php", values);
            printLog("updateClient:\n" + result);
            isUpdatedClient = true;
            return true;
        } catch (Exception e) {
            printLog("updateClient Exception:\n" + e);
            return false;
        }
    }

    @Override
    public boolean updateCar(long id, ContentValues values) {
        try {
            String result = PHPtools.POST(AppContract.WEB_URL + "updateCar.php", values);
            printLog("updateCar:\n" + result);
            isUpdatedCar = true;
            return true;
        } catch (Exception e) {
            printLog("updateCar Exception:\n" + e);
            return false;
        }
    }

    @Override
    public List<Car> getCars() {
        return cars;
    }

    @Override
    public List<Car> getAvailableCars() {
        if (isUpdatedCar) {
            updateAvailablecarList();
            isUpdatedCar = false;
        }
        return availableCars;
    }

    @Override
    public List<Order> getOrders() {
        if (isUpdatedOrder) {
            updateOrderList();
            isUpdatedOrder = false;
        }
        return orders;
    }

    @Override
    public List<CarModel> getCarModels() {
        return carModels;
    }

    @Override
    public List<Client> getClients() {
        if (isUpdatedClient) {
            updateClientList();
            isUpdatedClient = false;
        }
        return clients;
    }

    @Override
    public List<Branch> getBranches() {
        return branches;
    }

    @Override
    public List<Car> getAvailableCarsByBranche(long branch_id) {
        List<Car> availableCarsByBranche = new ArrayList<>();
        for (Car car : getAvailableCars()) {
            if (car.getBranchNum() == branch_id)
                availableCarsByBranche.add(car);
        }
        return availableCarsByBranche;
    }

    @Override
    public List<Car> getAvailableCarsFromPlace(Context context, long distance) {
        List<Branch> branchesInDiastance = new ArrayList<>();

        // construct a new instance of SimpleLocation
        SimpleLocation location = new SimpleLocation(context);

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(context);
        }

        double startLatitude = location.getLatitude();
        double startLongitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(context);
        Address addresses;
        for (Branch branch : branches) {
            try {
                addresses = geocoder.getFromLocationName(branch.getBranchAddress().getCity() + " " + branch.getBranchAddress().getStreet() + " " + branch.getBranchAddress().getNumber(), 1).get(0);
                if (((int) (SimpleLocation.calculateDistance(startLatitude, startLongitude, addresses.getLatitude(), addresses.getLongitude()) / 1000)) <= distance)
                    branchesInDiastance.add(branch);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        return null;
    }

    @Override
    public List<Branch> getBrancheOfAvailableCarsByCarModel(long carModel_id) {

        List<Long> branchesIdByModel = new ArrayList<>();
        List<Branch> branchesByModel = new ArrayList<>();
        for (Car car : availableCars) {
            if (car.getCarModelID() == carModel_id)
                branchesIdByModel.add(car.getBranchNum());
        }

        Set<Long> uniqueValues = new HashSet(branchesIdByModel); //now unique

        for (long id : uniqueValues) {
            for (Branch branch : branches) {
                if (id == branch.getBranchID())
                    branchesByModel.add(branch);
            }
        }
        return branchesByModel;

    }

    @Override
    public Map<Long, List<Car>> mapCarsByBranch() {
        Map<Long, List<Car>> mapCarsByBranch = new HashMap<>();
        for (Branch branch : branches) {
            mapCarsByBranch.put(branch.getBranchID(), getAvailableCarsByBranche(branch.getBranchID()));
        }
        return mapCarsByBranch;

    }

    @Override
    public Map<Long, List<Branch>> mapBranchsByCarModel() {
        Map<Long, List<Branch>> mapBranchsByCarModel = new HashMap<>();
        for (CarModel carModel : carModels) {
            mapBranchsByCarModel.put(carModel.getIdCarModel(), getBrancheOfAvailableCarsByCarModel(carModel.getIdCarModel()));

        }
        return mapBranchsByCarModel;
    }

    /**
     * needs
     * return date
     * fouled? if yes also amount
     * total amount to pay
     * kilometars
     */
    @Override
    public double closeOrder(long id, ContentValues values) {
        try {
            values.put(AppContract.Order.FINAL_AMOUNT, 100);
            String result = PHPtools.POST(AppContract.WEB_URL + "CloseOrder.php", values);
            printLog("closeOrder:\n" + result);
            isUpdatedOrder = true;
            return 100;
        } catch (Exception e) {
            printLog("closeOrder Exception:\n" + e);
            return -1;
        }
    }

    @Override
    public boolean orderClosedIn10sec() {

        try {
            ContentValues where = new ContentValues();
            where.put("interval", AppContract.TIMEINTERVAL);
            String str = PHPtools.POST(AppContract.WEB_URL + "orderChangedStatus.php", where);
            return (Integer.valueOf(str)) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Client getClient(long id) {
        for (Client client : clients) {
            if (client.getId() == id)
                return client;
        }
        return null;
    }

    @Override
    public CarModel getCarModel(long id) {
        for (CarModel carModel : carModels) {
            if (carModel.getIdCarModel() == id)
                return carModel;
        }
        return null;
    }


    @Override
    public Order getOrder(long id) {
        for (Order order : orders) {
            if (order.getIdOrderNum() == id)
                return order;
        }
        return null;
    }


    @Override
    public List<Order> getOpenOrders(long client_id) {
        List<Order> openOrders = new ArrayList<>();
        for (Order order : getOrders()) {
            if (!order.getStatus() && order.getClientId() == client_id)
                openOrders.add(order);
        }
        return openOrders;
    }

    @Override
    public List<Order> getClosedOrders(long client_id) {
        List<Order> closedOrders = new ArrayList<>();
        for (Order order : getOrders()) {
            if (order.getStatus() && order.getClientId() == client_id)
                closedOrders.add(order);
        }
        return closedOrders;
    }


}
