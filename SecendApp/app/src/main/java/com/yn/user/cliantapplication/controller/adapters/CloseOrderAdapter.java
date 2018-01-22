package com.yn.user.cliantapplication.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.controller.GlideApp;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarModel;
import com.yn.user.cliantapplication.model.entities.Order;

import java.util.List;

/**
 * Created by nissy34 on 31/12/2017.
 */

public class CloseOrderAdapter extends ArrayAdapter {

private List<Car> carList;
private List<CarModel> listModels;

    public CloseOrderAdapter(@NonNull Context context, @NonNull List<Car> cars, List<Order> Orders, List<CarModel> carModels) {
        super(context, 0, Orders);
        carList=cars;
        listModels=carModels;
    }

    @Override
    public long getItemId(int position) {
        return ((Order)getItem(position)).getIdOrderNum();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Order order=(Order)getItem(position);

        Car car = null;
        for(Car carItem:carList)
        {
            if(carItem.getIdCarNumber()==order.getCarNumber()) {
                car = carItem;
                break;
            }
        }

        CarModel carModel=null;

        if(car!=null)
        for (CarModel carModelItem:listModels)
            if(carModelItem.getIdCarModel()==car.getCarModelID()) {
                carModel = carModelItem;
                 break;
        }


        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_item_card_closed_order, parent, false);




        if (carModel != null) {

            TextView trans = (TextView) convertView.findViewById(R.id.cars_transmition);
            TextView description = (TextView) convertView.findViewById(R.id.cars_name_description);
            TextView classa = (TextView) convertView.findViewById(R.id.cars_class);
            TextView engine = (TextView) convertView.findViewById(R.id.cars_engineCapacity);
            TextView numseats = (TextView) convertView.findViewById(R.id.cars_numofseats);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.cars_carImage);


            numseats.setText(String.valueOf(carModel.getNumOfSeats()));
            trans.setText(carModel.getTransmissionType().toString());
            description.setText(carModel.getCompenyName() + " " + carModel.getModelName());
            classa.setText(carModel.getCarClass().toString());
            engine.setText(String.valueOf(carModel.getEngineCapacity()));

            GlideApp.with(getContext())
                    .load(carModel.getCarPic())
                    .placeholder(R.drawable.progress_animation)
                    .centerCrop()
                    .into(imageView);
        }

        if(car!=null) {
            TextView carid = (TextView) convertView.findViewById(R.id.car_id);
            TextView carkilo = (TextView) convertView.findViewById(R.id.car_kilo);
            TextView branch = (TextView) convertView.findViewById(R.id.car_branch);

            carid.setText(String.valueOf(car.getIdCarNumber()));
            carkilo.setText(String.valueOf(car.getKilometers()));
            branch.setText(String.valueOf(car.getBranchNum()));
        }

        TextView rentDate = (TextView) convertView.findViewById(R.id.textView_order_date);
        rentDate.setText(order.getRentDate().toString());


        TextView returnDate = (TextView) convertView.findViewById(R.id.textView_return_date);
        returnDate.setText(order.getReturnDate().toString());

        TextView amount = (TextView) convertView.findViewById(R.id.textView_amount);
        amount.setText(String.valueOf(order.getFinalAmount()));


        return convertView;

    }
}
