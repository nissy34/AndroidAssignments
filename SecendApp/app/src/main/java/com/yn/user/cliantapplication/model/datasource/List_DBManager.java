/*
package com.yn.user.cliantapplication.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.yn.user.cliantapplication.model.backend.DB_manager;
import com.yn.user.cliantapplication.model.entities.Address;
import com.yn.user.cliantapplication.model.entities.Branch;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarModel;
import com.yn.user.cliantapplication.model.entities.Client;
import com.yn.user.cliantapplication.model.entities.Manager;
import com.yn.user.cliantapplication.model.entities.Order;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by USER on 07/11/2017.
 *//*


public class List_DBManager  implements DB_manager {
    final String TAG = "List_DBManager";



    static List<Car> cars;
    static List<CarModel> carModels;
    static List<Client> clients;
    static List<Manager> managers;
    static List<Order> orders;
    static List<Branch> branches;
    
    static {

         cars=new ArrayList<>();
         carModels=new ArrayList<>();
         clients=new ArrayList<>();
         orders=new ArrayList<>();
         branches=new ArrayList<>();
         managers=new ArrayList<>();
*/
/*
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.kia_rio);
        Resources res = MainActivity.mMainActivity.getResources();
        Drawable myImage = res.getDrawable(R.drawable.kia_rio);
        Tools.drawableToBitmap(myImage);


        carModels.add(new CarModel(12,"KIA","RIO",1600, TransmissionType.AUTOMATIC,5, CarClass.C,bitmap));
     *//*

        try {
            managers.add(new Manager("Mangaer","Big","n@gmail.com",0,"9999","1234",0,0));
            managers.add(new Manager("Mangaer","Big","nr@gmail.com",1,"9999","1234",0,0));

            cars.add(new Car(1,2,3232,1));
            cars.add(new Car(3,2,3232,11));
            cars.add(new Car(1,2,3232,111));
            cars.add(new Car(1,2,3232,11113));
            cars.add(new Car(1,2,3232,11113));
            cars.add(new Car(1,2,3232,11113));
            cars.add(new Car(1991,2,3232,1311));
            cars.add(new Car(1,2,3232,2988131));
            cars.add(new Car(1,2,3232,5));
            cars.add(new Car(2,2,3232,56));
            cars.add(new Car(1,2,3232,567));
            cars.add(new Car(1,2,3232,5678));
            cars.add(new Car(1,2,3232,56789));
            cars.add(new Car(1,2,3232,567891));
            cars.add(new Car(1,2,3232,5678912));
            cars.add(new Car(3,2,3232,56789123));
            cars.add(new Car(1,2,3232,567891234));
            cars.add(new Car(1,2,3232,912345));


        branches.add(new Branch(0,5*100,new Address("Hadera","",4),"http://nheifetz.vlab.jct.ac.il/TakeAndGo/images/branches/hadera.jpg"));
        branches.add(new Branch(0,5*100,new Address("Hadera","",4),"http://nheifetz.vlab.jct.ac.il/TakeAndGo/images/branches/hadera.jpg"));

        branches.add(new Branch(1,5*100,new Address("Ashdod","",4),"http://nheifetz.vlab.jct.ac.il/TakeAndGo/images/branches/ashdod.jpg"));
        branches.add(new Branch(2,5*100,new Address("Tel Aviv","",4),"http://nheifetz.vlab.jct.ac.il/TakeAndGo/images/branches/tel_aviv.jpg"));
        branches.add(new Branch(3,5*100,new Address("Petah Tikva","",4),"http://nheifetz.vlab.jct.ac.il/TakeAndGo/images/branches/pt.jpg"));
        branches.add(new Branch(1991,5*100,new Address("אגסי 6 ירושלים","",4),"http://nheifetz.vlab.jct.ac.il/TakeAndGo/images/branches/netanya.jpg"));


            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",0,"052-4422258",3453435,0,"1234"));
            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",1,"052-4422258",3453435,0,"1234"));
            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",2,"052-4422258",3453435,0,"1234"));
            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",3,"052-4422258",3453435,0,"1234"));
            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",4,"052-4422258",3453435,0,"1234"));
            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",5,"052-4422258",3453435,0,"1234"));
            clients.add(new Client("Nagar","Yuval","yuval.nag.91@gmail.com",6,"052-4422258",3453435,0,"1234"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean hasClient(long client_id) {
        for (Client client:clients)
            if(client.getId()==client_id)
                return true;
        return false;    }


    public boolean hasCar(long car_id) {
        for (Car car:cars)
            if(car.getIdCarNumber()==car_id)
                return true;
        return false;
    }


    public boolean hasCarModel(long CarMOde_id) {
        for (CarModel carMOde:carModels)
            if(carMOde.getIdCarModel()==CarMOde_id)
                return true;
        return false;
    }

    public boolean hasBranch(long branch_id) {
        for (Branch branch:branches)
            if(branch.getBranchID()==branch_id)
                return true;
        return false;
    }
    public boolean hasManager(long manager_id) {
        for (Manager manager:managers)
            if(manager.getId()==manager_id )
                return true;
        return false;
    }
    //@Override
    public long addClient(ContentValues values){
        try {
            Client client = Tools.ContentValuesToClient(values);

            if (hasClient(client.getId())) {
                Log.i(TAG, "RegisterClient: exist"+client.getId());
                return -2;
            }
            clients.add(client);
            Log.d(TAG, "RegisterClient: "+client.getId());
            return client.getId();
        }catch (Exception e)
        {
            Log.e(TAG, "RegisterClient: "+e.getMessage(),e);
            return -1;

        }

    }

    //@Override
    public long addOrder(ContentValues contentValues) {
        return 0;
    }

    //@Override
    public long addCarModel(ContentValues values) {


        try {
            CarModel carModel = Tools.ContentValuesToCarModel(values);
            if (hasCarModel(carModel.getIdCarModel())) {
                Log.i(TAG, "addCarModel: exist"+carModel.getIdCarModel());
                return -2;
            }
            carModels.add(carModel);
            Log.d(TAG, "addCarModel: "+carModel.getIdCarModel());
            return carModel.getIdCarModel();
        } catch (Exception e) {
            Log.e(TAG, "addCarModel: " + e.getMessage(),e);
            return -1;

        }
    }




    //@Override
    public long addCar(ContentValues values) {
        try {
            Car car = Tools.ContentValuesToCar(values);
            if (hasCar(car.getIdCarNumber())) {
                Log.d(TAG, "addCar: exist"+car.getIdCarNumber());
                return -2;
            }
            cars.add(car);
            Log.d(TAG, "addCar: "+car.getIdCarNumber());
            return car.getIdCarNumber();
        }catch (Exception e)
            {
                Log.e(TAG, "addCar: "+e.getMessage(),e );
                return -1;

            }
    }

    //@Override
    public long addBranch(ContentValues values) {
        try {
            Branch branch = Tools.ContentValuesToBranch(values);
            if (hasBranch(branch.getBranchID())) {
                Log.d(TAG, "addBranch: exist"+branch.getBranchID());
                return -2;
            }
            branches.add(branch);
            Log.d(TAG, "addBranch: "+branch.getBranchID());
            return branch.getBranchID();
        }catch (Exception e)
        {
            Log.e(TAG, "addBranch: "+e.getMessage(),e );
            return -1;

        }
    }

    //@Override
    public long RegisterClient(ContentValues contentValues) {
        try {
            Manager manager = Tools.ContentValuesToManager(contentValues);
            if (hasBranch(manager.getId())) {
                Log.d(TAG, "RegisterClient: exist"+manager.getId());
                return -2;
            }
            managers.add(manager);
            Log.d(TAG, "RegisterClient: "+manager.getId());
            return manager.getId();
        }catch (Exception e)
        {
            Log.e(TAG, "RegisterClient: "+e.getMessage(),e );
            return -1;

        }
    }

    //@Override
    public boolean removeClient(long id) {
        Client client = null;
        for (Client item : clients)
            if (item.getId() == id) {
                client = item;
                //isUpdate = true;
                break;
            }
        return clients.remove(client);
    }

    //@Override
    public boolean removeCarModel(long id) {
        CarModel carModel = null;
        for (CarModel item : carModels)
            if (item.getIdCarModel() == id) {
                carModel = item;
                //isUpdate = true;
                break;
            }
        return carModels.remove(carModel);
    }

    //@Override
    public boolean removeCar(long id) {
        Car car = null;
        for (Car item : cars)
            if (item.getIdCarNumber() == id) {
                car = item;
                //isUpdate = true;
                break;
            }
        return cars.remove(car);
    }


    //@Override
    public boolean removeBranch(long id) {
        Branch branch = null;
        for (Branch item : branches)
            if (item.getBranchID() == id) {
                branch = item;
                //isUpdate = true;
                break;
            }
        return branches.remove(branch);
    }

    //@Override
    public boolean removeManager(long id) {
        Manager manager = null;
        for (Manager item : managers)
            if (item.getId() == id) {
                manager = item;
                //isUpdate = true;
                break;
            }
        return managers.remove(manager);
    }

    //@Override
    public boolean updateClient(long id, ContentValues values) {
        try {
            Client client = Tools.ContentValuesToClient(values);
            for(int i=0;i<clients.size();i++)
                if(clients.get(i).getId()==id) {
                    clients.set(i, client);
                    Log.d(TAG, "updateClient: "+id);
                    return true;
                }
        } catch (Exception e) {
            Log.e(TAG, "updateClient: "+id,e );
        }
        return false;
    }

    //@Override
    public boolean updateCar(long id, ContentValues values) {
        try {
            Car car = Tools.ContentValuesToCar(values);
            for(int i=0;i<cars.size();i++)
                if(cars.get(i).getIdCarNumber()==id) {
                    cars.set(i, car);
                    Log.d(TAG, "updateCar: "+id);
                    return true;
                }
        } catch (Exception e) {
            Log.e(TAG, "updateCar: "+id,e );
        }
        return false;
    }

    //@Override
    public boolean closeOrder(long id, ContentValues values) {
        return false;
    }

    //@Override
    public boolean orderClosedIn10sec() {
        return false;
    }

    //@Override
    public Cursor getClient(long id) {
        return null;
    }

    //@Override
    public Cursor getOrder(long id) {
        return null;
    }

    //@Override
    public Cursor getOrders() {
        return null;
    }

    //@Override
    public boolean updateCarModel(long id, ContentValues values) {
        try {
            CarModel carModel = Tools.ContentValuesToCarModel(values);
            for(int i=0;i<carModels.size();i++)
                if(carModels.get(i).getIdCarModel()==id) {
                    carModels.set(i, carModel);
                    Log.d(TAG, "updateCarModel: "+id);
                    return true;
                }
        } catch (Exception e) {
            Log.e(TAG, "updateCarModel: "+id,e);
        }
        return false;
    }

    //@Override
    public boolean updateBranch(long id, ContentValues values) {
        try {
            Branch branch = Tools.ContentValuesToBranch(values);
            for(int i=0;i<branches.size();i++)
                if(branches.get(i).getBranchID()==id){
                    branches.set(i,branch);
                    Log.d(TAG, "updateBranch: "+id);
                    return true;
                }
        } catch (Exception e) {
            Log.e(TAG, "updateBranch: "+id,e );
        }
        return false;
    }

    //@Override
    public boolean updateManager(long id, ContentValues values) {
        try {
            Manager manager = Tools.ContentValuesToManager(values);
            for(int i=0;i<managers.size();i++)
                if(managers.get(i).getId()==id) {
                    managers.set(i, manager);
                    Log.d(TAG, "updateManager: "+id);
                    return true;
                }
        } catch (Exception e) {
            Log.e(TAG, "updateManager: "+id,e );
        }
        return false;
    }

    //@Override
    public Cursor getManager(long id) {
        for (Manager manager:managers)
            if(manager.getId()==id)
            {
                List<Manager> templist=new ArrayList<Manager>();
                templist.add(manager);
                Log.d(TAG, "getManager: "+id);
                return Tools.managerListToCursor(templist);
            }
        Log.d(TAG, "getManager: dosnt exist "+id);
        return null;
    }

    //@Override
    public Cursor getCar(long id) {
        for (Car car:cars)
            if(car.getIdCarNumber()==id)
            {
                List<Car> templist=new ArrayList<Car>();
                templist.add(car);
                Log.d(TAG, "getCar: "+id);
                return Tools.CarListToCursor(templist);
            }
        Log.d(TAG, "getCar: dosnt exist "+id);
        return null;
    }

    //@Override
    public Cursor getCarModel(long id) {

        for (CarModel carMOde:carModels)
            if(carMOde.getIdCarModel()==id)
            {
                List<CarModel> templist=new ArrayList<CarModel>();
                templist.add(carMOde);
                Log.d(TAG, "getCarModel: "+id);
                return Tools.carModelsListToCursor(templist);
            }
        Log.d(TAG, "getCarModel: dosnt exist "+id);
        return null;
    }

    //@Override
    public Cursor getCarModels()  {
        return Tools.carModelsListToCursor(carModels);
    }


    //@Override
    public Cursor getClients() {
        return Tools.clientListToCursor(clients);
    }

    //@Override
    public Cursor getBranches() {
        return Tools.branchListToCursor(branches);
    }

    //@Override
    public Cursor getAvailableCars() {
        return null;
    }

    //@Override
    public Cursor getAvailableCarsByBranche() {
        return null;
    }

    //@Override
    public Cursor getAvailableCarsFromPlace() {
        return null;
    }

    //@Override
    public Cursor getBrancheOfAvailableCarsByCarModel() {
        return null;
    }

    //@Override
    public Cursor getOpenOrders() {
        return null;
    }

    //@Override
    public Cursor getCars() {
        return Tools.CarListToCursor(cars);
    }

    //@Override
    public Cursor getManagers() {
        return Tools.managerListToCursor(managers);
    }

}
*/
