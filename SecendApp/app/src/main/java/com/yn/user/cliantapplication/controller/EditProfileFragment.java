package com.yn.user.cliantapplication.controller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.backend.SHA_256_Helper;

/**
 * Created by USER on 28/12/2017.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    SharedPreferences mSharedPreferences;
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
    private Button updateClient;
    private String password;
    private long salt;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.activity_register_client,container,false);
    findView(view);
    populatView();
    return view;
    }

    private void populatView() {
        userEmail.setText(mSharedPreferences.getString(getString(R.string.login_user_email),"Can't find your email"));
        userLastname.setText(mSharedPreferences.getString(getString(R.string.login_user_last_name),"Can't find your name"));
        userFirstname.setText(mSharedPreferences.getString(getString(R.string.login_user_first_name),"Can't find your name"));
        userId.setText(String.valueOf(mSharedPreferences.getLong(getString(R.string.login_user_id),0)));
        userPhone.setText(mSharedPreferences.getString(getString(R.string.login_user_phone_number),""));
        textInputLayoutuserCrediCard.getEditText().setText(String.valueOf(mSharedPreferences.getLong(getString(R.string.login_user_credit_card),0)));
        password=mSharedPreferences.getString(getString(R.string.login_user_password),"");
        salt=mSharedPreferences.getLong(getString(R.string.login_user_salt),0);


    }

    private void findView(View view){

        updateClient=view.findViewById(R.id.add_manager);
        updateClient.setOnClickListener(this);
        progressDialog=new ProgressDialog(getActivity());
        textInputLayoutuserPassword = (TextInputLayout) view.findViewById(R.id.user_password);
        textInputLayoutuserConfrimPassword= (TextInputLayout) view.findViewById(R.id.user_password_confrim);
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
        textInputLayoutuserCrediCard=view.findViewById(R.id.textInputLayout_user_creditCard);
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
                                                                                  {
                                                                                      textInputLayoutuserCrediCard.setErrorEnabled(false);


                                                                                  }
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

        textInputLayout = (TextInputLayout) view.findViewById(R.id.textInputLayout);
        userFirstname = (EditText) view.findViewById(R.id.user_firstname);
        textInputLayout3 = (TextInputLayout) view.findViewById(R.id.textInputLayout3);
        userLastname = (EditText) view.findViewById(R.id.user_lastname);

        textInputLayoutuserEmail = (TextInputLayout) view.findViewById(R.id.textInputLayout_user_email);

        userEmail = (EditText) view.findViewById(R.id.user_email);


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

        textInputLayoutuserPhone = (TextInputLayout) view.findViewById(R.id.textInputLayout_user_phone);
        userPhone = (EditText) view.findViewById(R.id.user_phone);
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

        textInputLayoutuserId = (TextInputLayout) view.findViewById(R.id.textInputLayout_user_id);
        userId = (EditText) view.findViewById(R.id.user_id);
        textInputLayoutuserId.setEnabled(false);

        updateClient = (Button) view.findViewById(R.id.add_manager);
        updateClient.setOnClickListener(this);
        updateClient.setText("update");
    }

    @SuppressLint("StaticFieldLeak")
    public void onClick(final View view)  {
        if (view ==updateClient) {
            final ContentValues managerValues = new ContentValues();

            managerValues.put(AppContract.Client.ID, userId.getText().toString());
            managerValues.put(AppContract.Client.CRADIT_NUMBER, textInputLayoutuserCrediCard.getEditText().getText().toString());
            managerValues.put(AppContract.Client.PHONE_NUMBER, userPhone.getText().toString());
            managerValues.put(AppContract.Client.EMAIL_ADDR, userEmail.getText().toString());
            managerValues.put(AppContract.Client.FIRST_NAME, userFirstname.getText().toString());
            managerValues.put(AppContract.Client.LAST_NAME, userLastname.getText().toString());
            if(textInputLayoutuserPassword.getEditText().getText().toString().trim().length()==0) {
                managerValues.put(AppContract.Client.PASSWORD, password);
                managerValues.put(AppContract.Client.SALT, salt);
            }
            else
            {
                long newSalt = SHA_256_Helper.generateSalt();

                try {
                    managerValues.put(AppContract.Client.PASSWORD, SHA_256_Helper.getHash256String(textInputLayoutuserPassword.getEditText().getText().toString().trim(),newSalt));
                } catch (Exception e) {
                    e.printStackTrace();

                }

                managerValues.put(AppContract.Client.SALT, newSalt);
            }




            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showprogress();
                }

                @Override
                protected Boolean doInBackground(Void... params) {
                    return DBManagerFactory.getManager().updateClient(Long.valueOf(userId.getText().toString()),managerValues);
                }

                @Override
                protected void onPostExecute(Boolean b) {
                    super.onPostExecute(b);


                    if (b) {
                        // Toast.makeText(getBaseContext(), "insert id: " + id, Toast.LENGTH_LONG).show();

                        Snackbar.make(view, "Updated  successfully", Snackbar.LENGTH_LONG).show();
                        SharedPreferences.Editor editor=mSharedPreferences.edit();

                        editor.putString(getString(R.string.login_user_email),managerValues.getAsString(AppContract.Client.EMAIL_ADDR));
                        editor.putString(getString(R.string.login_user_name),managerValues.getAsString(AppContract.Client.FIRST_NAME)+" "+ managerValues.getAsString(AppContract.Client.LAST_NAME));
                        editor.putString(getString(R.string.login_user_first_name),managerValues.getAsString(AppContract.Client.EMAIL_ADDR));
                        editor.putString(getString(R.string.login_user_last_name), managerValues.getAsString(AppContract.Client.EMAIL_ADDR));
                        editor.putString(getString(R.string.login_user_password), managerValues.getAsString(AppContract.Client.PASSWORD));
                        editor.putString(getString(R.string.login_user_phone_number), managerValues.getAsString(AppContract.Client.PHONE_NUMBER));
                        editor.putLong(getString(R.string.login_user_salt), managerValues.getAsLong(AppContract.Client.SALT));
                        editor.putLong(getString(R.string.login_user_credit_card), managerValues.getAsLong(AppContract.Client.CRADIT_NUMBER));
                        editor.commit();

                        TextView headerName= getActivity().findViewById(R.id.header_name);
                        TextView headerEmail= getActivity().findViewById(R.id.header_email);

                        if (headerName != null)
                            headerName.setText(mSharedPreferences.getString(getString(R.string.login_user_name), "unknown"));
                        if (headerEmail != null)
                            headerEmail.setText(mSharedPreferences.getString(getString(R.string.login_user_email), "unknown"));



                    } else {

                        //Toast.makeText(getBaseContext(), "error insert id: " + id, Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "ERROR while updating  ", Snackbar.LENGTH_LONG).show();

                    }

                    closeProgress();
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
