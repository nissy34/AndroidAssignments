package com.yn.user.rentacar.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yn.user.rentacar.R;
import com.yn.user.rentacar.controller.Adapters.CarCursorAdapter;
import com.yn.user.rentacar.model.backend.AppContract;
import com.yn.user.rentacar.model.entities.CarClass;
import com.yn.user.rentacar.model.entities.CarModel;
import com.yn.user.rentacar.model.entities.TransmissionType;

import java.util.HashMap;
import java.util.Map;

public class car_list extends AppCompatActivity implements View.OnClickListener{
    private  Map<Long,CarModel> carModelMap;

//TODO onstate..
    Long car_id;
    ListView carListView;
    ProgressBar carProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        carListView=((ListView) findViewById(R.id.car_grid_view));
        ((Button)findViewById(R.id.button_addcar)).setOnClickListener(this);
        carProgressBar = (ProgressBar)findViewById(R.id.car_pb);

        showCars();
        manageDeleteOrEdit();
    }



    @SuppressLint("StaticFieldLeak")
    private void showCars() {
        new AsyncTask<Void, Void, Cursor>() {

            @Override
            protected void onPreExecute() {
                carProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Cursor doInBackground(Void... params) {
                Cursor cursorCar = getContentResolver().query(AppContract.Car.CAR_URI, null, null, null, null, null);
                Cursor cursorModel = getContentResolver().query(AppContract.CarModel.CAR_MODEL_URI, null, null, null, null, null);
                carModelMap = new HashMap<>();
                if (cursorModel != null) {
                    



                    cursorModel.moveToFirst();
                    while (!cursorModel.isAfterLast()) {


                        carModelMap.put(cursorModel.getLong(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.ID_CAR_MODEL)), new CarModel(cursorModel.getLong(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.ID_CAR_MODEL)),
                                cursorModel.getString(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.COMPENY_NAME)),
                                cursorModel.getString(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.MODEL_NAME)),
                                cursorModel.getLong(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.ENGINE_COPACITY)),
                                TransmissionType.valueOf(cursorModel.getString(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.TRANSMISSION_TYPE))),
                                cursorModel.getLong(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.NUM_OF_SEATS)),
                                CarClass.valueOf(cursorModel.getString(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.CLASS_OF_CAR))),
                                cursorModel.getString(cursorModel.getColumnIndexOrThrow(AppContract.CarModel.IMG))));


                        cursorModel.moveToNext();
                    }
                    cursorModel.close();
                }
                return cursorCar;

            }






            @Override
            protected void onPostExecute( Cursor cursor) {
                super.onPostExecute(cursor);

                Cursor cursorCar=cursor;

                CursorAdapter adapter = new CarCursorAdapter(car_list.this, cursorCar, 0,carModelMap);

                adapter.changeCursor(cursorCar);
                carListView.setAdapter(adapter);
                carProgressBar.setVisibility(View.GONE);
            }
        }.execute();



    }


    private  void manageDeleteOrEdit() {
        final FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabDelete.setVisibility(View.INVISIBLE);
        fabEdit.setVisibility(View.INVISIBLE);


        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fabDelete.setVisibility(View.VISIBLE);
                fabEdit.setVisibility(View.VISIBLE);
                car_id =l;
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Are You Sure", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {

                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onClick(View view) {
                                final Uri uri=ContentUris.withAppendedId(AppContract.Car.CAR_URI,car_id);
                                new AsyncTask<Void, Void, Integer>() {
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        carProgressBar.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    protected Integer doInBackground(Void... params) {
                                        return getContentResolver().delete(uri, null,null);
                                    }

                                    @Override
                                    protected void onPostExecute(Integer result) {
                                        super.onPostExecute(result);


                                        if (result > 0) {
                                            //Toast.makeText(getBaseContext(), "insert car   id: " + id, Toast.LENGTH_LONG).show();
                                            Snackbar.make(findViewById(android.R.id.content), "delete car   id: " + car_id, Snackbar.LENGTH_LONG).show();
                                            showCars();
                                            fabDelete.setVisibility(View.INVISIBLE);
                                            fabEdit.setVisibility(View.INVISIBLE);

                                        }
                                        else {
                                            //Toast.makeText(getBaseContext(), "error insert car  id: " + id, Toast.LENGTH_LONG).show();
                                            Snackbar.make(findViewById(android.R.id.content), "ERROR deleting car" , Snackbar.LENGTH_LONG).show();
                                            carProgressBar.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }.execute();
                            }
                        }).show();
            }
        });



        fabEdit.setOnClickListener(new View.OnClickListener() {



            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(car_list.this,UpdateCar.class);
                intent.putExtra(AppContract.Car.ID_CAR_NUMBER,car_id);
                startActivityForResult(intent,1);

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==1) {
            showCars();
            Snackbar.make(findViewById(android.R.id.content), "Updated car id: " + car_id, Snackbar.LENGTH_LONG).show();
            final FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab);
            final FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
            fabDelete.setVisibility(View.INVISIBLE);
            fabEdit.setVisibility(View.INVISIBLE);
        }
        else if (requestCode==2&&resultCode==1) {
            data.getLongExtra(AppContract.Car.ID_CAR_NUMBER,0);

            Snackbar.make(findViewById(android.R.id.content), "Inserted car id: "+data.getLongExtra(AppContract.CarModel.ID_CAR_MODEL,0), Snackbar.LENGTH_LONG).show();

            showCars();
            final FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab);
            final FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
            fabDelete.setVisibility(View.INVISIBLE);
            fabEdit.setVisibility(View.INVISIBLE);

        }
    }

    public void onClick(View view) {
        if(view.getId()==R.id.button_addcar) {
            Intent intent = new Intent(this, addCarActivity.class);
            startActivityForResult(intent, 2);
            //startActivity(intent);
        }
    }
}
