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
import com.yn.user.cliantapplication.controller.adapters.OpenOrderAdapter;
import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.entities.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nissy34 on 31/12/2017.
 */

public class CloseOpenOrders extends Fragment implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;

    private ListView carGridView;
    private TextView textView2;
    private Button button_closeorder;
    private TextInputLayout textInputLayoutKilo;
    private TextInputLayout textInputLayoutFouled;
    private Order order;
    private boolean fouled;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=     inflater.inflate(R.layout.open_orders_fragment,container,false);
        findViews(view);
         populatView();
        return view;
    }

    private void populatView() {
        new AsyncTask<Void,Void,ArrayAdapter>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                visibiltyopenOrders(View.GONE);
                showprogress();
            }

            @Override
            protected ArrayAdapter doInBackground(Void... voids) {
                return new OpenOrderAdapter(CloseOpenOrders.this.getActivity(), DBManagerFactory.getManager().getCars(),DBManagerFactory.getManager().getOpenOrders(mSharedPreferences.getLong(getString(R.string.login_user_id),-1)),DBManagerFactory.getManager().getCarModels());

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

        carGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                visibiltyopenOrders(View.VISIBLE);
                order=(Order)adapterView.getItemAtPosition(i);
            }
        });
        textInputLayoutKilo = (TextInputLayout)view.findViewById( R.id.textInputLayout_kilo );

        textInputLayoutKilo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               try
               {
                   Long.valueOf(charSequence.toString().trim());
                   textInputLayoutKilo.setErrorEnabled(false);
                   button_closeorder.setEnabled(true);

               }
               catch (Exception e)
               {
                   textInputLayoutKilo.setErrorEnabled(true);
                   textInputLayoutKilo.setError("you must enter your kilo");
                   button_closeorder.setEnabled(false);

               }

            }
        });
        textInputLayoutFouled = (TextInputLayout)view.findViewById( R.id.textInputLayout_fouled );


        button_closeorder = (Button)view.findViewById( R.id.button_closeorder );
        button_closeorder.setOnClickListener( this );
    }

    private void visibiltyopenOrders(int visibility)
    {
        button_closeorder.setVisibility(visibility);
        textInputLayoutKilo.setVisibility(visibility);
        textInputLayoutFouled.setVisibility(visibility);
    }
    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-12-31 19:44:43 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(final View v) {
        if ( v == button_closeorder) {

            button_closeorder.setEnabled(false);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String datetime = dateformat.format(c.getTime());


            final ContentValues orderContentValues= new ContentValues();
            orderContentValues.put(AppContract.Order.CAR_NUM,order.getCarNumber());
            orderContentValues.put(AppContract.Order.ORDER_ID,order.getIdOrderNum());
            orderContentValues.put(AppContract.Order.KILOMETERS_AT_RETURN,Long.valueOf(textInputLayoutKilo.getEditText().getText().toString())+order.getKilometersAtRent());
            orderContentValues.put(AppContract.Order.RETURN_DATE,datetime);
            orderContentValues.put(AppContract.Order.FOULED,textInputLayoutFouled.getEditText().getText().toString().trim().length()!=0);
            orderContentValues.put(AppContract.Order.ORDER_STATUS,String.valueOf(1));
            orderContentValues.put(AppContract.Order.AMOUNT_OF_FOUL,textInputLayoutFouled.getEditText().getText().toString());

            new AsyncTask<Void,Void,Double>()
            {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showprogress();
                }

                @Override
                protected void onPostExecute(Double aDouble) {
                    super.onPostExecute(aDouble);
                    if(aDouble>0) {
                        Snackbar.make(v, "Total Amount: " + aDouble, Snackbar.LENGTH_LONG).show();
                        populatView();
                        button_closeorder.setEnabled(true);


                    }
                    else {
                        Snackbar.make(v, "error closing order: " + order.getIdOrderNum(), Snackbar.LENGTH_LONG).show();
                        button_closeorder.setEnabled(true);
                        closeProgress();

                    }
                }

                @Override
                protected Double doInBackground(Void... voids) {
                   return DBManagerFactory.getManager().closeOrder(order.getIdOrderNum(),orderContentValues);

                }
            }.execute();



        }
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
