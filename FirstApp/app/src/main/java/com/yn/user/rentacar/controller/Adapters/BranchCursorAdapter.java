package com.yn.user.rentacar.controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Settings;
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
import com.yn.user.rentacar.controller.GlideApp;
import com.yn.user.rentacar.model.backend.AppContract;

import java.util.Locale;

/**
 * Created by nissy34 on 12/12/2017.
 */

public class BranchCursorAdapter extends CursorAdapter {

    public BranchCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.branch_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView address = (TextView) view.findViewById(R.id.branch_address);
        TextView parking_spaces = (TextView) view.findViewById(R.id.branch_parking_spaces);
        final ImageButton map_button = (ImageButton) view.findViewById(R.id.branch_button);
        final ImageView branch_imageView = (ImageView) view.findViewById(R.id.branch_image);
        map_button.setTag(R.id.branch_button, cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Address.CITY)) + " " + cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Address.STREET)) /*  + cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Address.NUMBER))*/);
        System.out.println(map_button.getTag(R.id.branch_button));

        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (view == map_button) {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    dialog.setContentView(R.layout.dialogmap);
                    dialog.show();
                    GoogleMap googleMap;


                    MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                    MapsInitializer.initialize(context);

                    mMapView = (MapView) dialog.findViewById(R.id.mapView);
                    mMapView.onCreate(dialog.onSaveInstanceState());
                    mMapView.onResume();// needed to get the map to display immediately
                    mMapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(final GoogleMap googleMap) {
                            try {
                                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                Address addresses = geocoder.getFromLocationName(((ImageButton) view).getTag(R.id.branch_button).toString(), 1).get(0);////your lat lng
                                LatLng position = new LatLng(addresses.getLatitude(), addresses.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(position).title(((ImageButton) view).getTag(R.id.branch_button).toString()));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                                googleMap.getUiSettings().setAllGesturesEnabled(true);
                                googleMap.getUiSettings().setMapToolbarEnabled(true);
                                googleMap.getUiSettings().setZoomControlsEnabled(true);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14), 1000, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });
        address.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Address.CITY)) + "    " + cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Address.STREET)) + "  #:" + cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Address.NUMBER)));
        parking_spaces.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Branch.NUMBER_OF_PARKING_SPACES)));
        GlideApp.with(context)
                .load(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Branch.IMAGE_URL)))
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(branch_imageView);
    }
}
