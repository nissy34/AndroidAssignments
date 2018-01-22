package com.yn.user.rentacar.controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yn.user.rentacar.R;
import com.yn.user.rentacar.controller.BranchList;
import com.yn.user.rentacar.controller.CarModelList;
import com.yn.user.rentacar.controller.GlideApp;
import com.yn.user.rentacar.model.backend.AppContract;

/**
 * Created by nissy34 on 12/12/2017.
 */

public class CarModelCursorAdapter extends CursorAdapter {

    public CarModelCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.carmodel_card, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView trans = (TextView) view.findViewById(R.id.cars_transmition);
        TextView description = (TextView) view.findViewById(R.id.cars_name_description);
        TextView classa = (TextView) view.findViewById(R.id.cars_class);
        TextView engine = (TextView) view.findViewById(R.id.cars_engineCapacity);
        TextView numseats = (TextView) view.findViewById(R.id.cars_numofseats);
        ImageView imageView = (ImageView) view.findViewById(R.id.cars_carImage);




        numseats.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.NUM_OF_SEATS)));
        trans.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.TRANSMISSION_TYPE)));
        description.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.COMPENY_NAME))+" "+cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.MODEL_NAME)));
        classa.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.CLASS_OF_CAR)));
        engine.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.ENGINE_COPACITY)));


        GlideApp.with(context)
                .load(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.IMG)))
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(imageView);
    }


}



