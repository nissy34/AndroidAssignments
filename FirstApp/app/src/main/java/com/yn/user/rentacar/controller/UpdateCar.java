package com.yn.user.rentacar.controller;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.database.Cursor;
import android.os.AsyncTask;
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

public class UpdateCar extends AppCompatActivity {

    ListView branchListView;
    ListView carModelListView;
    TextInputLayout idCar;
    TextInputLayout kilometers;
    Long branch_id;
    Long carModel_id;
    Long car_id;
    MKLoader progress;
    MKLoader progress_carmodel;
    MKLoader progress_branch;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);


        findViews();

        populateViews();

        branchListView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        branchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                branch_id=l;
            }
        });

        carModelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                carModel_id=l;
            }
        });
    }


    private void findViews() {
        branchListView = (ListView) findViewById(R.id.branch_listview);
        carModelListView = (ListView) findViewById(R.id.model_listview);
        idCar = (TextInputLayout) findViewById(R.id.textInputLayout_car_id);
        kilometers = (TextInputLayout) findViewById(R.id.textInputLayout_kilo);
        progress= ((MKLoader)findViewById(R.id.MKLoader));
        progress_branch= ((MKLoader)findViewById(R.id.MKLoader_branche));
        progress_carmodel= ((MKLoader)findViewById(R.id.MKLoader_carModel));
        addButton=(Button)findViewById(R.id.car_button);
    }


    @SuppressLint("StaticFieldLeak")
    private void populateViews() {


        final Intent intent = getIntent();
        car_id = intent.getLongExtra(AppContract.Car.ID_CAR_NUMBER, -1);

        //TODO check if -1
        new AsyncTask<Long, Void, Cursor>() {


            @Override
            protected Cursor doInBackground(Long... longs) {

                return getContentResolver().query(ContentUris.withAppendedId(AppContract.Car.CAR_URI, longs[0]), null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

                if (cursor != null) {
                    cursor.moveToFirst();

                    branch_id = cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Car.BRANCH_NUM));
                    carModel_id = cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Car.CAR_MODEL_ID));

                    showBranches();
                    showCarModel();

                    kilometers.getEditText().setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Car.KILOMETRERS)));

                    idCar.getEditText().setText(String.valueOf(car_id));

                }
            }
        }.execute(car_id);


    }


    private int findPosition(CursorAdapter cursorAdapter,long id) {
        for(int i=0;i<cursorAdapter.getCount();i++)
        if(cursorAdapter.getItemId(i)==id)
            return i;
        return -1;
    }



    @SuppressLint("StaticFieldLeak")
    public void onClick(View view) {



        final ContentValues carContentValues= new ContentValues();
        carContentValues.put(AppContract.Car.ID_CAR_NUMBER,idCar.getEditText().getText().toString());
        carContentValues.put(AppContract.Car.KILOMETRERS,kilometers.getEditText().getText().toString());
        carContentValues.put(AppContract.Car.BRANCH_NUM,branch_id);
        carContentValues.put(AppContract.Car.CAR_MODEL_ID,carModel_id);



        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected void onPreExecute() {
                progress.setVisibility(View.VISIBLE);
                addButton.setEnabled(false);

            }

            @Override
            protected Integer doInBackground(Void... params) {
                return getContentResolver().update(ContentUris.withAppendedId(AppContract.Car.CAR_URI,car_id),carContentValues,null,null );

            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);


                if (result == 1) {
                    //Toast.makeText(getBaseContext(), "insert car   id: " + id, Toast.LENGTH_LONG).show();
                    Intent data=new Intent();
                    data.putExtra("result",true);
                    setResult(1,data);
                    finish();
                }
                else {
                    //Toast.makeText(getBaseContext(), "error insert car  id: " + id, Toast.LENGTH_LONG).show();
                    Snackbar.make(findViewById(android.R.id.content), "ERROR updating car" , Snackbar.LENGTH_LONG).show();
                    progress.setVisibility(View.GONE);
                    addButton.setEnabled(true);
                }

            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private void showCarModel() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected void onPreExecute() {
                progress_carmodel.setVisibility(View.VISIBLE);
            }

            @Override
            protected Cursor doInBackground(Void... params) {
                return   getContentResolver().query(AppContract.CarModel.CAR_MODEL_URI, null, null, null, null, null);

            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

                CursorAdapter adapterCarModel = new CarModelCursorAdapter(UpdateCar.this, cursor, 0);

                adapterCarModel.changeCursor(cursor);
                carModelListView.setAdapter(adapterCarModel);

                //find the chosen car and choose it
                final int position=findPosition(adapterCarModel,carModel_id);
                carModelListView.post(new Runnable() {
                    @Override
                    public void run() {
                        carModelListView.requestFocusFromTouch();
                        carModelListView.setSelection(position);
                       progress_carmodel.setVisibility(View.GONE);
                    }
                });

                }
            }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void showBranches() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected void onPreExecute() {
                progress_branch.setVisibility(View.VISIBLE);
            }

            @Override
            protected Cursor doInBackground(Void... params) {
                return getContentResolver().query(AppContract.Branch.BRANCH_URI, null, null, null, null, null);

            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

                CursorAdapter adapterBranch = new BranchCursorAdapter(UpdateCar.this, cursor, 0) ;

                adapterBranch.changeCursor(cursor);
                branchListView.setAdapter(adapterBranch);
                branchListView.setItemChecked(findPosition(adapterBranch,branch_id),true);
                final int position=findPosition(adapterBranch,carModel_id);

               branchListView.post(new Runnable() {
                    @Override
                    public void run() {
                        branchListView.requestFocusFromTouch();
                        branchListView.setSelection(position);
                        progress_branch.setVisibility(View.GONE);
                    }
                });

            }
        }.execute();




    }


}
