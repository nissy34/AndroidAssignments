package com.yn.user.cliantapplication.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.backend.updateService;
import com.yn.user.cliantapplication.model.datasource.PHPtools;
import com.yn.user.cliantapplication.model.datasource.Tools;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class StartActivity extends AppCompatActivity {

    ProgressBar progressBar;
    SharedPreferences mSharedPreferences;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_start);
        progressBar =findViewById(R.id.progressBar2);

        //Remove title bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // smoothProgressBar = (SmoothProgressBar)findViewById(R.id.pb_start);

//Remove notification bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        checkifConeecredToInternet();

    }

    private void checkifConeecredToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if ((activeNetwork != null && activeNetwork.isConnectedOrConnecting())) {
            downloData();
        } else {
          /*  Toast.makeText(this,"This App needs internet connection to work",Toast.LENGTH_LONG).show();
            finishAffinity();
*/
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
            alertDialogBuilder.setMessage("No internet connection!");
            alertDialogBuilder.setPositiveButton("Retry?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            checkifConeecredToInternet();
                        }
                    });

            alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }


    private void downloData() {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                int d=progressBar.getProgress();
               // for (int i=progressBar.getProgress();i<i+17;++i){
                    progressBar.setProgress( d+16);
                //}

            }

            @Override
            protected void onPreExecute() {
                // smoothProgressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
                progressBar.setProgress(0);
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if(aVoid==1) {
                    startService(new Intent(StartActivity.this, updateService.class));
                    if (mSharedPreferences.getBoolean(getString(R.string.is_login), false)) {
                        Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainIntent);
                    } else {
                        Intent mainIntent = new Intent(StartActivity.this, LoginActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainIntent);
                    }
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.this);
                    alertDialogBuilder.setMessage("problam connecting to the data base, check your internet connection");
                            alertDialogBuilder.setPositiveButton("Retry?",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            downloData();
                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("Exit",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            }

            @Override
            protected Integer doInBackground(Void... voids) {
                try {
                    String result= PHPtools.GET(AppContract.WEB_URL+"/checkConnectivity.php");
                    Integer.valueOf(result);
                    DBManagerFactory.getManager().updateCarModellist();
                    publishProgress();
                    DBManagerFactory.getManager().updateCarlist();
                    publishProgress();
                    DBManagerFactory.getManager().updateOrderList();
                    publishProgress();
                    DBManagerFactory.getManager().updateAvailablecarList();
                    publishProgress();
                    DBManagerFactory.getManager().updateBranchesList();
                    publishProgress();
                    DBManagerFactory.getManager().updateClientList();
                    publishProgress();

                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }

                return 1;
            }
        }.execute();
    }

}

