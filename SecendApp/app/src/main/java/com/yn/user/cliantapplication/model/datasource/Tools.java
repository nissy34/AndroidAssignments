package com.yn.user.cliantapplication.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.entities.Address;
import com.yn.user.cliantapplication.model.entities.Branch;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarClass;
import com.yn.user.cliantapplication.model.entities.CarModel;
import com.yn.user.cliantapplication.model.entities.Client;
import com.yn.user.cliantapplication.model.entities.Manager;
import com.yn.user.cliantapplication.model.entities.Order;
import com.yn.user.cliantapplication.model.entities.TransmissionType;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by nissy34 on 07/11/2017.
 */

public class Tools {

    public static ContentValues AddressToContentValues(Address address) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.Address.CITY, address.getCity());
        contentValues.put(AppContract.Address.STREET, address.getStreet());
        contentValues.put(AppContract.Address.NUMBER, address.getNumber());
        return contentValues;
    }

    public static ContentValues OrderToContentValues(Order order) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.Order.ORDER_ID,             order.getIdOrderNum());
        contentValues.put(AppContract.Order.CLIENT_ID,            order.getClientId()  );
        contentValues.put(AppContract.Order.CAR_NUM,              order.getCarNumber() );
        contentValues.put(AppContract.Order.RENT_DATE,            order.getRentDate().toString());
        contentValues.put(AppContract.Order.RETURN_DATE,          order.getReturnDate().toString());
        contentValues.put(AppContract.Order.KILOMETERS_AT_RENT,   order.getKilometersAtRent());
        contentValues.put(AppContract.Order.KILOMETERS_AT_RETURN, order.getKilometersAtReturn());
        contentValues.put(AppContract.Order.FOULED,               order.getFouled());
        contentValues.put(AppContract.Order.AMOUNT_OF_FOUL,       order.getAmountOfFoul());
        contentValues.put(AppContract.Order.FINAL_AMOUNT,         order.getFinalAmount());
        contentValues.put(AppContract.Order.ORDER_STATUS,         order.getStatus());
        return contentValues;
    }

    public static ContentValues BranchToContentValues(Branch branch) {
        ContentValues contentValues = AddressToContentValues(branch.getBranchAddress());;
        contentValues.put(AppContract.Branch.BRANCH_ID,branch.getBranchID()) ;
        contentValues.put(AppContract.Branch.NUMBER_OF_PARKING_SPACES, branch.getNumberOfParkingSpaces());
        return contentValues;
    }
    public static ContentValues CarToContentValues(Car car) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.Car.ID_CAR_NUMBER, car.getIdCarNumber());
        contentValues.put(AppContract.Car.CAR_MODEL_ID, car.getCarModelID());
        contentValues.put(AppContract.Car.BRANCH_NUM, car.getBranchNum());
        contentValues.put(AppContract.Car.KILOMETRERS, car.getKilometers());
        return contentValues;
    }

    public static ContentValues CarModelToContentValues(CarModel carModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.CarModel.ID_CAR_MODEL, carModel.getIdCarModel());
        contentValues.put(AppContract.CarModel.COMPENY_NAME, carModel.getCompenyName());
        contentValues.put(AppContract.CarModel.ENGINE_COPACITY, carModel.getEngineCapacity());
        contentValues.put(AppContract.CarModel.MODEL_NAME, carModel.getModelName());
        contentValues.put(AppContract.CarModel.TRANSMISSION_TYPE, carModel.getTransmissionType().toString());
        contentValues.put(AppContract.CarModel.NUM_OF_SEATS, carModel.getNumOfSeats());
        contentValues.put(AppContract.CarModel.IMG, carModel.getCarPic());
        contentValues.put(AppContract.CarModel.CLASS_OF_CAR, carModel.getCarClass().toString());

        return contentValues;
    }

    public static ContentValues ClientToContentValues(Client client) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.Client.ID, client.getId());
        contentValues.put(AppContract.Client.FIRST_NAME,   client.getFirstName());
        contentValues.put(AppContract.Client.LAST_NAME,    client.getLastName());
        contentValues.put(AppContract.Client.EMAIL_ADDR,   client.getEmailAdrs());
        contentValues.put(AppContract.Client.PHONE_NUMBER, client.getPhoneNum());
        contentValues.put(AppContract.Client.CRADIT_NUMBER,client.getCraditNumber());
        contentValues.put(AppContract.Client.PASSWORD,client.getPassword());
        contentValues.put(AppContract.Manager.SALT,client.getSalt());

        return contentValues;
    }

    public static ContentValues ManagerToContentValues(Manager manager) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppContract.Manager.ID, manager.getId());
        contentValues.put(AppContract.Manager.FIRST_NAME,   manager.getFirstName());
        contentValues.put(AppContract.Manager.LAST_NAME,    manager.getLastName());
        contentValues.put(AppContract.Manager.EMAIL_ADDR,   manager.getEmailAdrs());
        contentValues.put(AppContract.Manager.PHONE_NUMBER, manager.getPhoneNum());
        contentValues.put(AppContract.Manager.BRANCH_ID,manager.getBranchId());
        contentValues.put(AppContract.Manager.PASSWORD,manager.getPassword());
        contentValues.put(AppContract.Manager.SALT,manager.getSalt());
        return contentValues;
    }

    public static Order ContentValuesToOrder(ContentValues contentValues)
    {
        Timestamp timestamp=null;
        if(!contentValues.getAsString(AppContract.Order.RETURN_DATE).contains("null"))
            timestamp=  Timestamp.valueOf(contentValues.getAsString(AppContract.Order.RETURN_DATE));


        return   new Order(
                contentValues.getAsLong(AppContract.Order.ORDER_ID)                 ,
                contentValues.getAsLong(AppContract.Order.CLIENT_ID           ),
                contentValues.getAsLong(AppContract.Order.CAR_NUM            ),
                Timestamp.valueOf(contentValues.getAsString(AppContract.Order.RENT_DATE)),
                timestamp,
                contentValues.getAsLong(AppContract.Order.KILOMETERS_AT_RENT  ),
                contentValues.getAsLong(AppContract.Order.KILOMETERS_AT_RETURN),
                contentValues.getAsBoolean(AppContract.Order.FOULED               ),
                contentValues.getAsBoolean(AppContract.Order.ORDER_STATUS),
                contentValues.getAsLong(AppContract.Order.AMOUNT_OF_FOUL   ),
                contentValues.getAsLong(AppContract.Order.FINAL_AMOUNT      )
        );
    }
    public static Address ContentValuesToAddress(ContentValues contentValues)
    {
        Integer number = contentValues.getAsInteger(AppContract.Address.NUMBER);

        return new Address(
                contentValues.getAsString(AppContract.Address.CITY),
                contentValues.getAsString(AppContract.Address.STREET),
                number);
    }

    public static Branch ContentValuesToBranch(ContentValues contentValues)
    {
        Long id = contentValues.getAsLong(AppContract.Branch.BRANCH_ID);
        Integer numberOfParkingSpaces = contentValues.getAsInteger(AppContract.Branch.NUMBER_OF_PARKING_SPACES);
        String imageurl=contentValues.getAsString(AppContract.Branch.IMAGE_URL);

        return new Branch(id, numberOfParkingSpaces,ContentValuesToAddress(contentValues),imageurl);
    }
    public static Car ContentValuesToCar(ContentValues contentValues)
    {

               Long branchnuum= contentValues.getAsLong(AppContract.Car.BRANCH_NUM);
               Long carmodelId= contentValues.getAsLong(AppContract.Car.CAR_MODEL_ID);
               Long kilo= contentValues.getAsLong(AppContract.Car.KILOMETRERS);
               Long carNum= contentValues.getAsLong(AppContract.Car.ID_CAR_NUMBER);



        return new Car(
                branchnuum,
                carmodelId,
                kilo,
                carNum
        );

    }
    public static CarModel ContentValuesToCarModel(ContentValues contentValues) {

              Long id=  contentValues.getAsLong(AppContract.CarModel.ID_CAR_MODEL);
              String compenyName=  contentValues.getAsString(AppContract.CarModel.COMPENY_NAME);
              String modalName=  contentValues.getAsString(AppContract.CarModel.MODEL_NAME);
              Long engine=  contentValues.getAsLong(AppContract.CarModel.ENGINE_COPACITY);
              TransmissionType transmissionType =  TransmissionType.valueOf(contentValues.getAsString(AppContract.CarModel.TRANSMISSION_TYPE));
              Long numofseats=  contentValues.getAsLong(AppContract.CarModel.NUM_OF_SEATS);
              String imageUrl=contentValues.getAsString(AppContract.CarModel.IMG);
              CarClass classofcar= CarClass.valueOf(contentValues.getAsString(AppContract.CarModel.CLASS_OF_CAR));
             // if(id==null || id<0 ||
             //         numofseats==null || numofseats<0)
             //     throw new IllegalArgumentException();

              return new CarModel(
                id,
                compenyName,
                modalName,
                engine,
                transmissionType,
                numofseats,
                      classofcar ,
                      imageUrl
                );
    }
    public static Client ContentValuesToClient(ContentValues contentValues) throws Exception {
        return  new Client(
                contentValues.getAsString(AppContract.Client.LAST_NAME),
                contentValues.getAsString(AppContract.Client.FIRST_NAME),
                contentValues.getAsString(AppContract.Client.EMAIL_ADDR),
                contentValues.getAsLong(AppContract.Client.ID),
                contentValues.getAsString(AppContract.Client.PHONE_NUMBER),
                contentValues.getAsLong(AppContract.Client.CRADIT_NUMBER),
                contentValues.getAsLong(AppContract.Client.SALT),
                contentValues.getAsString(AppContract.Client.PASSWORD)

                );
    }
    public static Manager ContentValuesToManager(ContentValues contentValues) throws Exception {
        return  new Manager(
                contentValues.getAsString(AppContract.Manager.LAST_NAME),
                contentValues.getAsString(AppContract.Manager.FIRST_NAME),
                contentValues.getAsString(AppContract.Manager.EMAIL_ADDR),
                contentValues.getAsLong(AppContract.Manager.ID),
                contentValues.getAsString(AppContract.Manager.PHONE_NUMBER),
                contentValues.getAsString(AppContract.Manager.PASSWORD),
                contentValues.getAsLong(AppContract.Manager.SALT),
                contentValues.getAsLong(AppContract.Manager.BRANCH_ID)

        );
    }
    public static Cursor CarListToCursor(List<Car> cars) {
        String[] columns = new String[]
                {
                        AppContract.Car.ID_CAR_NUMBER,
                        AppContract.Car.CAR_MODEL_ID,
                        AppContract.Car.BRANCH_NUM,
                        AppContract.Car.KILOMETRERS,
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Car car : cars) {
            matrixCursor.addRow(new Object[]
                    {
                            car.getIdCarNumber(),
                            car.getCarModelID(),
                            car.getBranchNum(),
                            car.getKilometers()
                    });
        }

        return matrixCursor;
    }
    public static Cursor ordresListToCursor(List<Order> orders) {
        String[] columns = new String[]
                {
                        AppContract.Order.ORDER_ID,
                        AppContract.Order.CLIENT_ID,
                        AppContract.Order.CAR_NUM,
                        AppContract.Order.RENT_DATE,
                        AppContract.Order.RETURN_DATE,
                        AppContract.Order.KILOMETERS_AT_RENT,
                        AppContract.Order.KILOMETERS_AT_RETURN,
                        AppContract.Order.FOULED,
                        AppContract.Order.AMOUNT_OF_FOUL,
                        AppContract.Order.FINAL_AMOUNT,
                        AppContract.Order.ORDER_STATUS
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Order order : orders) {
            matrixCursor.addRow(new Object[]
                    {
                            order.getIdOrderNum(),
                            order.getClientId()  ,
                            order.getCarNumber() ,
                            order.getRentDate(),
                            order.getReturnDate(),
                            order.getKilometersAtRent(),
                            order.getKilometersAtReturn(),
                            order.getFouled(),
                            order.getAmountOfFoul(),
                            order.getFinalAmount(),
                            order.getStatus()
                    });
        }

        return matrixCursor;
    }
    public static Cursor carModelsListToCursor(List<CarModel> carModels) {
        String[] columns = new String[]
                {
                        AppContract.CarModel.ID_CAR_MODEL,
                        AppContract.CarModel.COMPENY_NAME,
                        AppContract.CarModel.ENGINE_COPACITY,
                        AppContract.CarModel.MODEL_NAME,
                        AppContract.CarModel.NUM_OF_SEATS,
                        AppContract.CarModel.TRANSMISSION_TYPE,
                        AppContract.CarModel.CLASS_OF_CAR,
                        AppContract.CarModel.IMG

                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (CarModel carModel : carModels) {
            matrixCursor.addRow(new Object[]
                    {
                            carModel.getIdCarModel(),
                            carModel.getCompenyName(),
                            carModel.getEngineCapacity(),
                            carModel.getModelName(),
                            carModel.getNumOfSeats(),
                            carModel.getTransmissionType(),
                            carModel.getCarClass(),
                            carModel.getCarPic()
                    });
        }

        return matrixCursor;
    }
    public static Cursor clientListToCursor(List<Client> clients) {
        String[] columns = new String[]
                {
                        AppContract.Client.ID,
                        AppContract.Client.CRADIT_NUMBER,
                        AppContract.Client.EMAIL_ADDR,
                        AppContract.Client.FIRST_NAME,
                        AppContract.Client.LAST_NAME,
                        AppContract.Client.PHONE_NUMBER,
                        AppContract.Client.PASSWORD,
                        AppContract.Client.SALT


                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Client client : clients) {
            matrixCursor.addRow(new Object[]
                    {
                            client.getId(),
                            client.getCraditNumber(),
                            client.getEmailAdrs(),
                            client.getFirstName(),
                            client.getLastName(),
                            client.getPhoneNum(),
                            client.getPassword(),
                            client.getSalt()
                    });
        }

        return matrixCursor;
    }
    public static Cursor managerListToCursor(List<Manager> managers) {
        String[] columns = new String[]
                {
                        AppContract.Manager.ID,
                        AppContract.Manager.BRANCH_ID,
                        AppContract.Manager.EMAIL_ADDR,
                        AppContract.Manager.FIRST_NAME,
                        AppContract.Manager.LAST_NAME,
                        AppContract.Manager.PHONE_NUMBER,
                        AppContract.Manager.PASSWORD,
                        AppContract.Manager.SALT
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Manager manager : managers) {
            matrixCursor.addRow(new Object[]
                    {
                            manager.getId(),
                            manager.getBranchId(),
                            manager.getEmailAdrs(),
                            manager.getFirstName(),
                            manager.getLastName(),
                            manager.getPhoneNum(),
                            manager.getPassword(),
                            manager.getSalt(),
                    });
        }

        return matrixCursor;
    }
    public static Cursor branchListToCursor(List<Branch> branches) {
        String[] columns = new String[]
                {
                        AppContract.Branch.BRANCH_ID,
                        AppContract.Branch.NUMBER_OF_PARKING_SPACES,
                        AppContract.Branch.IMAGE_URL,
                        AppContract.Address.CITY,
                        AppContract.Address.NUMBER,
                        AppContract.Address.STREET
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Branch branch : branches) {
            matrixCursor.addRow(new Object[]
                    {
                            branch.getBranchID(),
                            branch.getNumberOfParkingSpaces(),
                            branch.getBranchImgUrl(),
                            branch.getBranchAddress().getCity(),
                            branch.getBranchAddress().getNumber(),
                            branch.getBranchAddress().getStreet()
                    });



        }
        return matrixCursor;
    }
    public static Cursor addressListToCursor(List<Address> addresses) {
        String[] columns = new String[]
                {
                        AppContract.Address.CITY,
                        AppContract.Address.NUMBER,
                        AppContract.Address.STREET
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Address address : addresses) {
            matrixCursor.addRow(new Object[]
                    {
                            address.getCity(),
                            address.getNumber(),
                            address.getStreet()
                    });



        }
        return matrixCursor;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
        return ((BitmapDrawable)drawable).getBitmap();
    }

    int width = drawable.getIntrinsicWidth();
    width = width > 0 ? width : 1;
    int height = drawable.getIntrinsicHeight();
    height = height > 0 ? height : 1;

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);

    return bitmap;
}
    public static byte[] imageToByte(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static Bitmap byteToImage(byte[] imageArray) {
      return  BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        String  s=Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        return s;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}



