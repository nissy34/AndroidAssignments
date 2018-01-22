package com.example.nissy304929995_yuval305302937.activitylifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    final String ACTIVITY_LIFE_TAG = "activity lifecycle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toast.makeText(this, "onCreate2()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart2()",Toast.LENGTH_SHORT).show();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onRestart()");
        Toast.makeText(this, "onRestart2()",Toast.LENGTH_SHORT).show();



    }


    @Override
    protected void onResume() {
        super.onResume();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onResume()");
        Toast.makeText(this, "onResume2()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onPause()");
        Toast.makeText(this, "onPause2()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onStop()");
        Toast.makeText(this, "onStop2()",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.d(ACTIVITY_LIFE_TAG , "onDestroy()");
        Toast.makeText(this, "onDestroy2()",Toast.LENGTH_SHORT).show();

    }
}
