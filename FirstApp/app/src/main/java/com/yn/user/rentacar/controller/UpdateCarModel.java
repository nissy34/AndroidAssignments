package com.yn.user.rentacar.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;
import com.yn.user.rentacar.R;
import com.yn.user.rentacar.model.backend.AppContract;
import com.yn.user.rentacar.model.datasource.Tools;
import com.yn.user.rentacar.model.entities.CarClass;
import com.yn.user.rentacar.model.entities.TransmissionType;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateCarModel extends AppCompatActivity {
    final int REQUEST_CODE_GALLERY = 999;
    private ImageView modelImage;
    private EditText modelId;
    private EditText modelComname;
    private EditText modelName;
    private EditText modelEngine;
    private EditText modelNumofseats;
    private Spinner modelSpinTrans;
    private Spinner modelSpinClass;
    private Button modelAdd;
    private long carModel_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_model);

        findViews();

        populateViews();

        //populate the spinners
        ((Spinner) findViewById(R.id.model_spin_class)).setAdapter(new ArrayAdapter<CarClass>(this, android.R.layout.simple_list_item_1, CarClass.values()));
        ((Spinner) findViewById(R.id.model_spin_trans)).setAdapter(new ArrayAdapter<TransmissionType>(this, android.R.layout.simple_list_item_1, TransmissionType.values()));

        //check permission
        ((ImageView) findViewById(R.id.model_image)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        UpdateCarModel.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void populateViews() {
        //populate the spinners

        final Intent intent=getIntent();
         carModel_id =intent.getLongExtra(AppContract.CarModel.ID_CAR_MODEL,-1);
         //TODO check if -1
        new AsyncTask<Long, Void, Cursor>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ((ProgressBar)findViewById(R.id.updteModelcar_pb)).setVisibility(View.VISIBLE);
            }

            @Override
            protected Cursor doInBackground(Long... longs) {

                return getContentResolver().query(ContentUris.withAppendedId(AppContract.CarModel.CAR_MODEL_URI,longs[0]), null,null,null,null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

              if(cursor!=null) {
                  cursor.moveToFirst();

                  modelSpinClass.setAdapter(new ArrayAdapter<CarClass>(UpdateCarModel.this, android.R.layout.simple_list_item_1, CarClass.values()));
                  modelSpinClass.setSelection(((ArrayAdapter)modelSpinClass.getAdapter()).getPosition(CarClass.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.CLASS_OF_CAR)))));

                  modelSpinTrans.setAdapter(new ArrayAdapter<TransmissionType>(UpdateCarModel.this, android.R.layout.simple_list_item_1, TransmissionType.values()));
                  modelSpinTrans.setSelection(((ArrayAdapter)modelSpinTrans.getAdapter()).getPosition(TransmissionType.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.TRANSMISSION_TYPE)))));

                  modelNumofseats.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.NUM_OF_SEATS)));

                  modelName.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.MODEL_NAME)));

                  modelComname.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.COMPENY_NAME)));

                  modelEngine.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.ENGINE_COPACITY)));

                  GlideApp.with(UpdateCarModel.this)
                          .load(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.CarModel.IMG)))
                          .placeholder(R.drawable.progress_animation)
                          .into(modelImage);
                  modelId.setText(String.valueOf(carModel_id));

                  ((ProgressBar)findViewById(R.id.updteModelcar_pb)).setVisibility(View.GONE);
              }
            }
        }.execute(carModel_id);


    }

    /**
     * Find the Views in the layout
     */
    private void findViews() {
        modelImage = (ImageView)findViewById( R.id.model_image );
        modelId = (EditText)findViewById( R.id.model_id );
        modelComname = (EditText)findViewById( R.id.model_comname );
        modelName = (EditText)findViewById( R.id.model_name );
        modelEngine = (EditText)findViewById( R.id.model_engine );
        modelNumofseats = (EditText)findViewById( R.id.model_numofseats );
        modelSpinTrans = (Spinner)findViewById( R.id.model_spin_trans );
        modelSpinClass = (Spinner)findViewById( R.id.model_spin_class );
        modelAdd = (Button)findViewById( R.id.model_add );



    }
    //check permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * gets the image from the  intent
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ((ImageView) findViewById(R.id.model_image)).setImageBitmap(Tools.scaleDown(bitmap, 500, true));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    @SuppressLint("StaticFieldLeak")
    public void onClick(View view) {

        final ContentValues modelcontentValues = new ContentValues();
        modelcontentValues.put(AppContract.CarModel.CLASS_OF_CAR, ((Spinner) findViewById(R.id.model_spin_class)).getSelectedItem().toString());
        Bitmap bitmap= ((BitmapDrawable) ((ImageView) findViewById(R.id.model_image)).getDrawable()).getBitmap();
        modelcontentValues.put(AppContract.CarModel.ENGINE_COPACITY, ((EditText) findViewById(R.id.model_engine)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.MODEL_NAME, ((EditText) findViewById(R.id.model_name)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.COMPENY_NAME, ((EditText) findViewById(R.id.model_comname)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.ID_CAR_MODEL, ((EditText) findViewById(R.id.model_id)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.NUM_OF_SEATS, ((EditText) findViewById(R.id.model_numofseats)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.TRANSMISSION_TYPE, ((Spinner) findViewById(R.id.model_spin_trans)).getSelectedItem().toString());

        new AsyncTask<Bitmap,Void , Integer>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                ((ProgressBar)findViewById(R.id.updteModelcar_pb)).setVisibility(View.VISIBLE);
            }

            @Override
            protected Integer doInBackground(Bitmap... bitmaps) {

                modelcontentValues.put(AppContract.CarModel.IMG, Tools.encodeToBase64(bitmaps[0], Bitmap.CompressFormat.PNG,50));

                return getContentResolver().update(ContentUris.withAppendedId(AppContract.CarModel.CAR_MODEL_URI,Long.valueOf(modelId.getText().toString())),modelcontentValues,null,null );
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);



                if (result == 1) {
                    //Toast.makeText(getApplicationContext(), "insert car model  id: " + id, Toast.LENGTH_LONG).show();
                   setResult(1);
                   finish();

                } else {
                    ((ProgressBar)findViewById(R.id.updteModelcar_pb)).setVisibility(View.GONE);
                    Snackbar.make(findViewById(android.R.id.content), "ERROR updating car model  ", Snackbar.LENGTH_LONG).show();
                    //toast.show();
                }
            }
        }.execute(bitmap);
    }
}