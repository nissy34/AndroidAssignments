package com.yn.user.cliantapplication.controller;

import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import android.widget.Button;

import android.widget.EditText;


import com.yn.user.cliantapplication.R;

import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.backend.SHA_256_Helper;


public class RegisterClient extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private EditText userFirstname;
    private TextInputLayout textInputLayout3;
    private EditText userLastname;
    private TextInputLayout textInputLayoutuserEmail;
    private EditText userEmail;
    private TextInputLayout textInputLayoutuserCrediCard;
    private EditText CrediCard;
    private TextInputLayout textInputLayoutuserId;
    private EditText userId;
    private TextInputLayout textInputLayoutuserPhone;
    private EditText userPhone;
    private TextInputLayout textInputLayoutuserPassword;
    private TextInputLayout textInputLayoutuserConfrimPassword;
    private Button addManager;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        findViews();


    }

    /**
     * find views and check all input text
     */
    private void findViews() {


        progressDialog=new ProgressDialog(this);
        textInputLayoutuserPassword = (TextInputLayout) findViewById(R.id.user_password);
        textInputLayoutuserConfrimPassword= (TextInputLayout) findViewById(R.id.user_password_confrim);
        textInputLayoutuserConfrimPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().contentEquals(textInputLayoutuserPassword.getEditText().getText().toString())) {
                    textInputLayoutuserConfrimPassword.setErrorEnabled(false);
                    textInputLayoutuserPassword.setErrorEnabled(false);
                } else {
                    textInputLayoutuserConfrimPassword.setErrorEnabled(true);
                    textInputLayoutuserPassword.setErrorEnabled(true);
                    textInputLayoutuserConfrimPassword.setError("Password are not matching");
                    textInputLayoutuserPassword.setError("Password are not matching");

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        textInputLayoutuserCrediCard=findViewById(R.id.textInputLayout_user_creditCard);
        textInputLayoutuserCrediCard.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()<9)
                {
                    textInputLayoutuserCrediCard.setErrorEnabled(true);
                    textInputLayoutuserCrediCard.setError("min length is 8 numbers");

                }
                else
                    textInputLayoutuserCrediCard.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
                                                                          }
        );
        textInputLayoutuserPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().contentEquals(textInputLayoutuserConfrimPassword.getEditText().getText().toString())) {
                    textInputLayoutuserConfrimPassword.setErrorEnabled(false);
                    textInputLayoutuserPassword.setErrorEnabled(false);
                } else {
                    textInputLayoutuserConfrimPassword.setErrorEnabled(true);
                    textInputLayoutuserPassword.setErrorEnabled(true);
                    textInputLayoutuserConfrimPassword.setError("Password are not matching");
                    textInputLayoutuserPassword.setError("Password are not matching");

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        userFirstname = (EditText) findViewById(R.id.user_firstname);
        textInputLayout3 = (TextInputLayout) findViewById(R.id.textInputLayout3);
        userLastname = (EditText) findViewById(R.id.user_lastname);

        textInputLayoutuserEmail = (TextInputLayout) findViewById(R.id.textInputLayout_user_email);

        userEmail = (EditText) findViewById(R.id.user_email);


        userEmail.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                    textInputLayoutuserEmail.setErrorEnabled(true);
                    textInputLayoutuserEmail.setError("Enter A Valid Email");

                } else
                    textInputLayoutuserEmail.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInputLayoutuserPhone = (TextInputLayout) findViewById(R.id.textInputLayout_user_phone);
        userPhone = (EditText) findViewById(R.id.user_phone);
        userPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Patterns.PHONE.matcher(charSequence).matches()) {
                    textInputLayoutuserPhone.setErrorEnabled(true);
                    textInputLayoutuserPhone.setError("Enter A Valid Phone Num");

                } else
                    textInputLayoutuserPhone.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textInputLayoutuserId = (TextInputLayout) findViewById(R.id.textInputLayout_user_id);
        userId = (EditText) findViewById(R.id.user_id);
        userId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()||charSequence.toString().trim().contentEquals("0")) {
                    textInputLayoutuserId.setErrorEnabled(true);
                    textInputLayoutuserId.setError("This Field Is Mandatory");

                } else
                    textInputLayoutuserId.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        addManager = (Button) findViewById(R.id.add_manager);



    }







    @SuppressLint("StaticFieldLeak")
    public void onClick(View view) throws Exception {
        if (view == addManager) {
            final ContentValues managerValues = new ContentValues();
            long newSalt = SHA_256_Helper.generateSalt();
            managerValues.put(AppContract.Client.ID, ((EditText) findViewById(R.id.user_id)).getText().toString());
            managerValues.put(AppContract.Client.CRADIT_NUMBER, textInputLayoutuserCrediCard.getEditText().getText().toString());
            managerValues.put(AppContract.Client.PHONE_NUMBER, ((EditText) findViewById(R.id.user_phone)).getText().toString());
            managerValues.put(AppContract.Client.EMAIL_ADDR, ((EditText) findViewById(R.id.user_email)).getText().toString());
            managerValues.put(AppContract.Client.FIRST_NAME, ((EditText) findViewById(R.id.user_firstname)).getText().toString());
            managerValues.put(AppContract.Client.LAST_NAME, ((EditText) findViewById(R.id.user_lastname)).getText().toString());
            managerValues.put(AppContract.Client.PASSWORD, SHA_256_Helper.getHash256String(((EditText) findViewById(R.id.user_pass)).getText().toString(),newSalt));
            managerValues.put(AppContract.Client.SALT, newSalt);




            new AsyncTask<Void, Void, Long>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    progressDialog.setMessage("contacting the server...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();


                }

                @Override
                protected Long doInBackground(Void... params) {
                    return DBManagerFactory.getManager().addClient(managerValues);
                }

                @Override
                protected void onPostExecute(Long id) {
                    super.onPostExecute(id);


                    if (id > 0) {
                        // Toast.makeText(getBaseContext(), "insert id: " + id, Toast.LENGTH_LONG).show();
                        Intent data=new Intent(RegisterClient.this,LoginActivity.class);
                        startActivity(data);


                    } else {

                        //Toast.makeText(getBaseContext(), "error insert id: " + id, Toast.LENGTH_LONG).show();
                        Snackbar.make(findViewById(android.R.id.content), "ERROR registering user " + id, Snackbar.LENGTH_LONG).show();

                    }
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }.execute();
        }

    }


}

