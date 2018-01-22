package com.yn.user.cliantapplication.controller;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.controller.adapters.CarModelExpandableListAdapter;
import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.entities.Branch;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by USER on 31/12/2017.
 */

public class CarModelFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    ViewGroup container;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branches_fragment, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.container = container;
        findView(view);
        return view;
    }

    private void findView(View view) {

        expListView = (ExpandableListView) view.findViewById(R.id.branch_expandable_list_view);
        progressDialog=new ProgressDialog(getActivity());

        buildAdapter();
    }

    private void buildAdapter() {
        new AsyncTask<Void, Void, ExpandableListAdapter>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showprogress();
            }
            @Override
            protected ExpandableListAdapter doInBackground(Void... voids) {


                return new CarModelExpandableListAdapter(getActivity(), DBManagerFactory.getManager().getCarModels(), DBManagerFactory.getManager().mapBranchsByCarModel());
            }

            @Override
            protected void onPostExecute(ExpandableListAdapter expandableListAdapter) {
                // setting list adapter
                super.onPostExecute(expandableListAdapter);
                listAdapter = expandableListAdapter;
                expListView.setAdapter(expandableListAdapter);
              closeProgress();
            }
        }.execute();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                openOrder(v, groupPosition, childPosition);

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getBaseContext(),
                        "group id: " + listAdapter.getGroupId(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openOrder(final View viewChild, final int groupPosition, int childPosition) {

        final Branch branch = (Branch) listAdapter.getChild(groupPosition, childPosition);


                Snackbar.make(viewChild,"Order a car from branch number " +listAdapter.getChildId(groupPosition,childPosition) +" ?" , Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {

                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onClick(final View view) {

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                final String datetime = dateformat.format(c.getTime());


                                new AsyncTask<Void, Void, Long[]>() {

                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        showprogress();
                                    }

                                    @Override
                                    protected Long[] doInBackground(Void... params) {
                                        Car car = null;
                                        Long[] results=new Long[2];
                                        //get the first car in this branch
                                        for (Car availableCar : DBManagerFactory.getManager().getAvailableCarsByBranche(branch.getBranchID())) {
                                            if (availableCar.getCarModelID() == ((CarModel) listAdapter.getGroup(groupPosition)).getIdCarModel()){
                                                car = availableCar;
                                                results[0]=car.getIdCarNumber();
                                                break;
                                            }

                                        }
                                        if (car != null) {
                                            ContentValues orderContentValues = new ContentValues();
                                            orderContentValues.put(AppContract.Order.CAR_NUM, String.valueOf(car.getIdCarNumber()));
                                            orderContentValues.put(AppContract.Order.KILOMETERS_AT_RENT, String.valueOf(car.getKilometers()));
                                            orderContentValues.put(AppContract.Order.CLIENT_ID, String.valueOf(sharedPreferences.getLong(getString(R.string.login_user_id), 1)));
                                            orderContentValues.put(AppContract.Order.RENT_DATE, datetime);
                                            orderContentValues.put(AppContract.Order.ORDER_STATUS, String.valueOf(0));
                                            results[1]= DBManagerFactory.getManager().addOrder(orderContentValues);

                                        }
                                        else
                                            results[1]= (long)-1;//if there is an error

                                        return results;

                                    }

                                    @Override
                                    protected void onPostExecute(Long[] results) {
                                        super.onPostExecute(results);


                                        if (results[1] > 0) {
                                            Snackbar.make(viewChild, "order #: " + results[1]+", car number "+results[0], Snackbar.LENGTH_LONG).show();
                                            //fab.setVisibility(View.INVISIBLE);
                                            buildAdapter();
                                        } else {
                                            Snackbar.make(viewChild, "ERROR ordering car.", Snackbar.LENGTH_LONG).show();
                                             closeProgress();
                                        }


                                    }
                                }.execute();
                            }
                        }).show();
            }
    private void showprogress() {
        if(!progressDialog.isShowing()) {
            progressDialog.setMessage("contacting the server...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

    }

    private void closeProgress() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}