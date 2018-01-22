package com.yn.user.rentacar.controller.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yn.user.rentacar.R;
import com.yn.user.rentacar.controller.GlideApp;
import com.yn.user.rentacar.controller.car_list;
import com.yn.user.rentacar.model.backend.AppContract;
import com.yn.user.rentacar.model.entities.CarModel;

import java.util.Map;

/**
 * Created by nissy34 on 12/12/2017.
 */

public class CarCursorAdapter extends CursorAdapter {

    private Map<Long,CarModel> carModelMap;
    public CarCursorAdapter(Context context, Cursor c, int flags, Map<Long,CarModel> carModelMap) {
        super(context, c, flags);
        this.carModelMap=carModelMap;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.car_item_card, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long modelid=cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Car.CAR_MODEL_ID));

        CarModel carModel=carModelMap.get(modelid);
        if(carModel != null) {

            TextView trans = (TextView) view.findViewById(R.id.cars_transmition);
            TextView description = (TextView) view.findViewById(R.id.cars_name_description);
            TextView classa = (TextView) view.findViewById(R.id.cars_class);
            TextView engine = (TextView) view.findViewById(R.id.cars_engineCapacity);
            TextView numseats = (TextView) view.findViewById(R.id.cars_numofseats);
            ImageView imageView = (ImageView) view.findViewById(R.id.cars_carImage);


            numseats.setText(String.valueOf(carModel.getNumOfSeats()));
            trans.setText(carModel.getTransmissionType().toString());
            description.setText(carModel.getCompenyName() + " " + carModel.getModelName());
            classa.setText(carModel.getCarClass().toString());
            engine.setText(String.valueOf(carModel.getEngineCapacity()));

            GlideApp.with(context)
                    .load(carModel.getCarPic())
                    .placeholder(R.drawable.progress_animation)
                    .centerCrop()
                    .into(imageView);
        }

        TextView carid = (TextView) view.findViewById(R.id.car_id);
        TextView carkilo = (TextView) view.findViewById(R.id.car_kilo);
        TextView branch = (TextView) view.findViewById(R.id.car_branch);

        carid.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Car.ID_CAR_NUMBER)));
        carkilo.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Car.KILOMETRERS)));
        branch.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Car.BRANCH_NUM)));
    }

}
