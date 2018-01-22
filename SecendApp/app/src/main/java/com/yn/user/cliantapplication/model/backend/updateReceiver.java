package com.yn.user.cliantapplication.model.backend;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nissy34 on 02/01/2018.
 */

public class updateReceiver extends BroadcastReceiver {
    public updateReceiver() {
        //Log.d("my service" , "updateReceiver");

    }


    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        Log.d("my service" , "onReceive");

        Toast.makeText(context,"update database",Toast.LENGTH_LONG).show();

        final PendingResult pendingResult = goAsync();
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DBManagerFactory.getManager().updateAvailablecarList();
                DBManagerFactory.getManager().updateCarlist();

                pendingResult.finish();
                return null;
            }
        };
        asyncTask.execute();



    }
}
