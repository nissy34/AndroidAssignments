package com.yn.user.rentacar.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.tuyenmonkey.mkloader.MKLoader;
import com.yn.user.rentacar.R;
import com.yn.user.rentacar.controller.Adapters.BranchCursorAdapter;
import com.yn.user.rentacar.controller.Adapters.CarModelCursorAdapter;
import com.yn.user.rentacar.model.backend.AppContract;


public class addCarActivity extends AppCompatActivity {

    ListView branchListView;
    ListView carModelListView;
    TextInputLayout idCar;
    TextInputLayout kilometers;
    MKLoader progress;
    MKLoader progress_car;
    MKLoader progress_branch;
    Long branch_id;
    Long carModel_id;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        findViews();
        showBranches();
        showCarModel();

        branchListView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        branchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                branch_id = l;
            }
        });

        carModelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                carModel_id = l;
            }
        });


    }

    private void findViews() {
        branchListView = (ListView) findViewById(R.id.branch_listview);
        carModelListView = (ListView) findViewById(R.id.model_listview);
        idCar = (TextInputLayout) findViewById(R.id.textInputLayout_car_id);
        kilometers = (TextInputLayout) findViewById(R.id.textInputLayout_kilo);
        progress = (MKLoader) findViewById(R.id.MKLoader);
        progress_car = (MKLoader) findViewById(R.id.MKLoader_carModel);
        progress_branch = (MKLoader) findViewById(R.id.MKLoader_branche);
        addButton=(Button)findViewById(R.id.car_button);

    }

    @SuppressLint("StaticFieldLeak")
    public void onClick(View view) {


        if (view == findViewById(R.id.car_button)) {
            final ContentValues carContentValues = new ContentValues();
            carContentValues.put(AppContract.Car.ID_CAR_NUMBER, idCar.getEditText().getText().toString());
            carContentValues.put(AppContract.Car.KILOMETRERS, kilometers.getEditText().getText().toString());
            carContentValues.put(AppContract.Car.BRANCH_NUM, branch_id);
            carContentValues.put(AppContract.Car.CAR_MODEL_ID, carModel_id);

            new AsyncTask<Void, Void, Uri>() {

                @Override
                protected void onPreExecute() {
                    progress.setVisibility(View.VISIBLE);
                    addButton.setEnabled(false);
                }

                @Override
                protected Uri doInBackground(Void... params) {
                    return getContentResolver().insert(AppContract.Car.CAR_URI, carContentValues);

                }

                @Override
                protected void onPostExecute(Uri uriResult) {
                    super.onPostExecute(uriResult);

                    long id = ContentUris.parseId(uriResult);
                    if (id > 0) {
                        Intent data = new Intent();
                        data.putExtra(AppContract.Car.ID_CAR_NUMBER, id);
                        setResult(1, data);
                        finish();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "ERROR inserting car.", Snackbar.LENGTH_LONG).show();

                    }

                    addButton.setEnabled(true);
                    progress.setVisibility(View.GONE);
                }
            }.execute();
        }
    }
        @SuppressLint("StaticFieldLeak")
        private void showCarModel () {
            new AsyncTask<Void, Void, Cursor>() {
                @Override
                protected void onPreExecute() {
                    progress_car.setVisibility(View.VISIBLE);
                }

                @Override
                protected Cursor doInBackground(Void... params) {
                    return getContentResolver().query(AppContract.CarModel.CAR_MODEL_URI, null, null, null, null, null);
                }

                @Override
                protected void onPostExecute(Cursor cursor) {
                    super.onPostExecute(cursor);

                    CursorAdapter adapter = new CarModelCursorAdapter(addCarActivity.this, cursor, 0);


                    adapter.changeCursor(cursor);
                    carModelListView.setAdapter(adapter);
                    progress_car.setVisibility(View.GONE);

                }

            }.execute();
        }

        @SuppressLint("StaticFieldLeak")
        private void showBranches () {
            new AsyncTask<Void, Void, Cursor>() {
                @Override
                protected void onPreExecute() {
                    progress_branch.setVisibility(View.VISIBLE);

                }

                @Override
                protected Cursor doInBackground(Void... params) {
                    Cursor cursor = getContentResolver().query(AppContract.Branch.BRANCH_URI, null, null, null, null, null);
                    return cursor;
                }

                @Override
                protected void onPostExecute(Cursor cursor) {
                    super.onPostExecute(cursor);

                    CursorAdapter adapter = new BranchCursorAdapter(addCarActivity.this, cursor, 0);


                    adapter.changeCursor(cursor);


                    branchListView.setAdapter(adapter);
                    progress_branch.setVisibility(View.GONE);


                }
            }.execute();


        }
    }




