package com.yn.user.rentacar.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class addCarModel extends AppCompatActivity implements View.OnClickListener {
    final int REQUEST_CODE_GALLERY = 999;
    Spinner classSpinner;
    Spinner transSpinner;
    ImageView modelImageView;
    MKLoader progress;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmodel);

        findViews();

        //populate the spinners
        classSpinner.setAdapter(new ArrayAdapter<CarClass>(this, android.R.layout.simple_list_item_1, CarClass.values()));
        transSpinner.setAdapter(new ArrayAdapter<TransmissionType>(this, android.R.layout.simple_list_item_1, TransmissionType.values()));

        //check permission
        modelImageView.setOnClickListener(new View.OnClickListener() {

           @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        addCarModel.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });



    }

    private void findViews()
    {
         classSpinner=((Spinner)findViewById(R.id.model_spin_class));
         transSpinner=((Spinner)findViewById(R.id.model_spin_trans));
         modelImageView=((ImageView)findViewById(R.id.model_image));
         progress=(MKLoader)findViewById(R.id.MKLoader);
         addButton=((Button)findViewById(R.id.model_add));
        addButton.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * gets the image from the  intent
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

                ((ImageView) findViewById(R.id.model_image)).setImageBitmap(Tools.scaleDown(bitmap,500,true));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void onClick(View view) {

       final ContentValues modelcontentValues= new ContentValues();
        modelcontentValues.put(AppContract.CarModel.CLASS_OF_CAR,((Spinner)findViewById(R.id.model_spin_class)).getSelectedItem().toString());
        Bitmap bitmap= ((BitmapDrawable) ((ImageView) findViewById(R.id.model_image)).getDrawable()).getBitmap();
        modelcontentValues.put(AppContract.CarModel.ENGINE_COPACITY,((EditText)findViewById(R.id.model_engine)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.MODEL_NAME,((EditText)findViewById(R.id.model_name)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.COMPENY_NAME,((EditText)findViewById(R.id.model_comname)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.ID_CAR_MODEL,((EditText)findViewById(R.id.model_id)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.NUM_OF_SEATS,((EditText)findViewById(R.id.model_numofseats)).getText().toString());
        modelcontentValues.put(AppContract.CarModel.TRANSMISSION_TYPE,((Spinner)findViewById(R.id.model_spin_trans)).getSelectedItem().toString());



        new AsyncTask<Bitmap, Void, Uri>() {

            @Override
            protected void onPreExecute() {
                progress.setVisibility(View.VISIBLE);
                addButton.setEnabled(false);
            }

            @Override
            protected Uri doInBackground(Bitmap... bitmaps) {
                modelcontentValues.put(AppContract.CarModel.IMG, Tools.encodeToBase64(bitmaps[0], Bitmap.CompressFormat.PNG,50));
                return getContentResolver().insert(AppContract.CarModel.CAR_MODEL_URI, modelcontentValues);
            }

            @Override
            protected void onPostExecute(Uri uriResult) {
                super.onPostExecute(uriResult);

                long id = ContentUris.parseId(uriResult);

                if (id > 0) {

                    Intent data=new Intent();
                    data.putExtra(AppContract.CarModel.ID_CAR_MODEL, id);
                    setResult(1,data);
                    finish();

                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "ERROR inserting car model  " , Snackbar.LENGTH_LONG).show();
                    progress.setVisibility(View.GONE);
                    addButton.setEnabled(true);
                }
            }
        }.execute(bitmap);
    }
}
