package com.example.nissy304929995_yuval305302937.activitylifecycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    final String ACTIVITY_LIFE_TAG = "activity lifecycle";
    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate()",Toast.LENGTH_SHORT).show();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onCreate()");
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onRestart()");
        Toast.makeText(this, "onRestart()",Toast.LENGTH_SHORT).show();



    }


    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onResume()");
        Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onPause()");
        Toast.makeText(this, "onPause()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onStop()");
        Toast.makeText(this, "onStop()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onDestroy()");
        Toast.makeText(this, "onDestroy()",Toast.LENGTH_SHORT).show();

    }


}
