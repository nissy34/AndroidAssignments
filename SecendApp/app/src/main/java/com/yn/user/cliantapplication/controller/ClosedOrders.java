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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.controller.adapters.CloseOrderAdapter;
import com.yn.user.cliantapplication.controller.adapters.OpenOrderAdapter;
import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.entities.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nissy34 on 31/12/2017.
 */

public class ClosedOrders extends Fragment  {
    private SharedPreferences mSharedPreferences;

    private ListView carGridView;
    private TextView title;
//    private Button button_closeorder;
//    private TextInputLayout textInputLayoutKilo;
//    private TextInputLayout textInputLayoutFouled;
    private Order order;
    private ProgressDialog progressDialog;
    //private boolean fouled;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.open_orders_fragment,container,false);
        findViews(view);
         populatView();
        return view;
    }

    private void populatView() {
        new AsyncTask<Void,Void,ArrayAdapter>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showprogress();
            }

            @Override
            protected ArrayAdapter doInBackground(Void... voids) {
                return new CloseOrderAdapter(ClosedOrders.this.getActivity(), DBManagerFactory.getManager().getCars(),DBManagerFactory.getManager().getClosedOrders(mSharedPreferences.getLong(getString(R.string.login_user_id),-1)),DBManagerFactory.getManager().getCarModels());

            }

            @Override
            protected void onPostExecute(ArrayAdapter arrayAdapter) {
                super.onPostExecute(arrayAdapter);
                carGridView.setAdapter(arrayAdapter);
                closeProgress();
            }


        }.execute();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-12-31 19:44:43 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View view) {
        carGridView = (ListView)view.findViewById( R.id.car_grid_view );
        progressDialog=new ProgressDialog(getActivity());
        title=view.findViewById( R.id.textView_orders_title );
        title.setText("closed orders");
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
