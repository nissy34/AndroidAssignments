package com.yn.user.rentacar.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yn.user.rentacar.R;
import com.yn.user.rentacar.controller.Adapters.CarModelCursorAdapter;
import com.yn.user.rentacar.model.backend.AppContract;


public class CarModelList extends AppCompatActivity {

    private long carModel_id;

    private ProgressBar carModelProgressBar;

    private ListView carModelListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model_list);

        carModelListView= ((ListView)findViewById(R.id.model_listview));
        carModelProgressBar = (ProgressBar)findViewById(R.id.carModel_pb);

        showCarModels();
        manageDeleteOrEdit();

    }

    @SuppressLint("StaticFieldLeak")
    private void showCarModels()
    {
        new AsyncTask<Void, Void, Cursor>() {


            @Override
            protected void onPreExecute() {
                carModelProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Cursor doInBackground(Void... params) {
               return getContentResolver().query(AppContract.CarModel.CAR_MODEL_URI, null, null, null, null, null);



            }

            @Override
            protected void onPostExecute(final Cursor cursor) {
                super.onPostExecute(cursor);
                CursorAdapter adapter = new CarModelCursorAdapter(CarModelList.this, cursor, 0);



                adapter.changeCursor(cursor);
                carModelListView.setAdapter(adapter);
                carModelProgressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    private  void manageDeleteOrEdit() {
        final FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabDelete.setVisibility(View.INVISIBLE);
        fabEdit.setVisibility(View.INVISIBLE);



        carModelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fabDelete.setVisibility(View.VISIBLE);
                fabEdit.setVisibility(View.VISIBLE);
                carModel_id =l;
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

                                final Uri uri = ContentUris.withAppendedId(AppContract.CarModel.CAR_MODEL_URI, carModel_id);
                                new AsyncTask<Void, Void, Integer>() {
                                    @Override
                                    protected void onPreExecute() {
                                        carModelProgressBar.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    protected Integer doInBackground(Void... params) {
                                        return getContentResolver().delete(uri, null, null);
                                    }

                                    @Override
                                    protected void onPostExecute(Integer result) {
                                        super.onPostExecute(result);


                                        if (result > 0) {
                                            //Toast.makeText(getBaseContext(), "insert car   id: " + id, Toast.LENGTH_LONG).show();
                                            Snackbar.make(findViewById(android.R.id.content), "delete car model   id: " + carModel_id, Snackbar.LENGTH_LONG).show();
                                            showCarModels();
                                            fabDelete.setVisibility(View.INVISIBLE);
                                            fabEdit.setVisibility(View.INVISIBLE);

                                        } else {
                                            //Toast.makeText(getBaseContext(), "error insert car  id: " + id, Toast.LENGTH_LONG).show();
                                            Snackbar.make(findViewById(android.R.id.content), "ERROR deleting car model id: " + carModel_id, Snackbar.LENGTH_LONG).show();
                                            carModelProgressBar.setVisibility(View.GONE);
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


                                 Intent intent=new Intent(CarModelList.this,UpdateCarModel.class);
                                 intent.putExtra(AppContract.CarModel.ID_CAR_MODEL,carModel_id);

                                startActivityForResult(intent,1);

                            }


        });
    }

    //add a car
    public void onClick(View view) {
        startActivityForResult(new Intent(this,addCarModel.class),2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //update
        if(requestCode==1&&resultCode==1)
        {
            showCarModels();
            Snackbar.make(findViewById(android.R.id.content), "updated car model", Snackbar.LENGTH_LONG)
                    .show();
            final FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab);
            final FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
            fabDelete.setVisibility(View.INVISIBLE);
            fabEdit.setVisibility(View.INVISIBLE);
        }
        //add
        else if (requestCode==2&&resultCode==1)
        {
            data.getLongExtra(AppContract.CarModel.ID_CAR_MODEL,0);
            Snackbar.make(findViewById(android.R.id.content), "insert car model  id: "+data.getLongExtra(AppContract.CarModel.ID_CAR_MODEL,0), Snackbar.LENGTH_LONG).show();
            showCarModels();
        }



    }

}

