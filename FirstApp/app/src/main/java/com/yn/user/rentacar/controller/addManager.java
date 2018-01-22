package com.yn.user.rentacar.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.yn.user.rentacar.R;
import com.yn.user.rentacar.controller.Adapters.BranchCursorAdapter;
import com.yn.user.rentacar.model.backend.AppContract;
import com.yn.user.rentacar.model.backend.SHA_256_Helper;


public class addManager extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private EditText userFirstname;
    private TextInputLayout textInputLayout3;
    private EditText userLastname;
    private TextInputLayout textInputLayoutuserEmail;
    private EditText userEmail;
    private TextInputLayout textInputLayoutuserId;
    private EditText userId;
    private TextInputLayout textInputLayoutuserPhone;
    private EditText userPhone;
    private TextInputLayout textInputLayoutuserPassword;
    private TextInputLayout textInputLayoutuserConfrimPassword;
    private Button addManager;
    private ListView branchListView;
    private Long branch_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manager);
        findViews();
        showBranches();
        branchListView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        branchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                branch_id = l;

            }
        });
    }

    /**
     * find views and check all input text
     */
    private void findViews() {
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
        branchListView = (ListView) findViewById(R.id.branch_listview);
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
    private void showBranches() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                Cursor cursor = getContentResolver().query(AppContract.Branch.BRANCH_URI, null, null, null, null, null);
                return cursor;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

                CursorAdapter adapter = new BranchCursorAdapter(addManager.this, cursor, 0);

                adapter.changeCursor(cursor);

                branchListView.setAdapter(adapter);

            }
        }.execute();




    }

    @SuppressLint("StaticFieldLeak")
    public void onClick(View view) throws Exception {
        if (view == addManager) {
            final ContentValues managerValues = new ContentValues();
            long newSalt = SHA_256_Helper.generateSalt();
            managerValues.put(AppContract.Manager.ID, ((EditText) findViewById(R.id.user_id)).getText().toString());
            managerValues.put(AppContract.Manager.BRANCH_ID, branch_id);
            managerValues.put(AppContract.Manager.PHONE_NUMBER, ((EditText) findViewById(R.id.user_phone)).getText().toString());
            managerValues.put(AppContract.Manager.EMAIL_ADDR, ((EditText) findViewById(R.id.user_email)).getText().toString());
            managerValues.put(AppContract.Manager.FIRST_NAME, ((EditText) findViewById(R.id.user_firstname)).getText().toString());
            managerValues.put(AppContract.Manager.LAST_NAME, ((EditText) findViewById(R.id.user_lastname)).getText().toString());
            managerValues.put(AppContract.Manager.PASSWORD, SHA_256_Helper.getHash256String(((EditText) findViewById(R.id.user_pass)).getText().toString(),newSalt));
            managerValues.put(AppContract.Manager.SALT, newSalt);




            new AsyncTask<Void, Void, Uri>() {
                @Override
                protected Uri doInBackground(Void... params) {
                    return getContentResolver().insert(AppContract.Manager.MANAGER_URI, managerValues);
                }

                @Override
                protected void onPostExecute(Uri uriResult) {
                    super.onPostExecute(uriResult);

                    long id = ContentUris.parseId(uriResult);
                    if (id > 0) {
                        // Toast.makeText(getBaseContext(), "insert id: " + id, Toast.LENGTH_LONG).show();
                        Intent data=new Intent();
                        data.putExtra(AppContract.Manager.ID,id);
                        setResult(1,data);
                        finish();
                    } else {

                        //Toast.makeText(getBaseContext(), "error insert id: " + id, Toast.LENGTH_LONG).show();
                        Snackbar.make(findViewById(android.R.id.content), "ERROR inserting user " + id, Snackbar.LENGTH_LONG).show();

                    }
                }
            }.execute();
        }

    }


}

