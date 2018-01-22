package com.yn.user.cliantapplication.model.backend;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by nissy34 on 02/01/2018.
 */

public class updateService extends Service {
    final String TAG = "myservice";
    static boolean ServiceRun;// = false;

    static {
        ServiceRun = false;
    }

    public updateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        Toast.makeText(this, "start service", Toast.LENGTH_SHORT).show();
        Thread t = new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(AppContract.TIMEINTERVAL*1000);
                        Log.d(TAG, "thread run ..");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   if (DBManagerFactory.getManager().orderClosedIn10sec()) {
                        Log.d(TAG, "isUpdatet run ..");
                        Intent intent1 = new Intent("com.model.backend.UPDATE");
                        updateService.this.sendBroadcast(intent1);

                    //sendBroadcast(intent);
                   }

                }
            }
        };

        t.start();


    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        // TODO: Return the communication channel to the service.
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        if (!ServiceRun) {
            ServiceRun = true;
            Toast.makeText(this, "run service", Toast.LENGTH_LONG).show();
            return START_STICKY;
        }

        Toast.makeText(this, "The service is already running", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
}
