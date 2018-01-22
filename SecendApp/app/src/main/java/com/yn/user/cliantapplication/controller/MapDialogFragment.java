package com.yn.user.cliantapplication.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yn.user.cliantapplication.R;

import java.util.Locale;

/**
 * Created by USER on 31/12/2017.
 */

public class MapDialogFragment extends DialogFragment {

    String address;
    Context context;


    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                    Address addresses = geocoder.getFromLocationName(address, 1).get(0);////your lat lng
                    LatLng position = new LatLng(addresses.getLatitude(), addresses.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(position).title(address));
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
        return dialog;
    }
}/* return new AlertDialog.Builder(getActivity())
                    .setTitle("Title")


                    .create();
*/


