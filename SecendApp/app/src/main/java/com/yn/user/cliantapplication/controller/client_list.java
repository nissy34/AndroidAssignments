package com.yn.user.cliantapplication.controller;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

//import com.tuyenmonkey.mkloader.MKLoader;
import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.model.backend.AppContract;


public class client_list extends AppCompatActivity {

    private long client_id;

    //private MKLoader progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

       // progress=(MKLoader)findViewById(R.id.MKLoader);

       showclient();
       manageDeleteOrEdit();

    }
@SuppressLint("StaticFieldLeak")
private void showclient()
{
    final SimpleCursorAdapter adapter = new SimpleCursorAdapter
            (
                    this,
                    R.layout.client_card,
                    null,
                    new String[]{AppContract.Client.FIRST_NAME,AppContract.Client.LAST_NAME,
                            AppContract.Client.EMAIL_ADDR,
                            AppContract.Client.PHONE_NUMBER},
                    new int[]{R.id.client_firstname,R.id.client_lastname, R.id.client_email, R.id.client_phone}
            );

    new AsyncTask<Void, Void, Cursor>() {

        @Override
        protected void onPreExecute() {
           // progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            Cursor cursor = getContentResolver().query(AppContract.Client.CLIENT_URI, null, null, null, null, null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            adapter.changeCursor(cursor);
          //  progress.setVisibility(View.GONE);

        }
    }.execute();
    ((GridView)findViewById(R.id.client_listview)).setAdapter(adapter);
}
    private  void manageDeleteOrEdit() {
        final FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab);
        fabDelete.setVisibility(View.INVISIBLE);



        ((GridView)findViewById(R.id.client_listview)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fabDelete.setVisibility(View.VISIBLE);
                client_id =l;
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Are You Sure", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {

                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onClick(View view) {
                                final Uri uri= ContentUris.withAppendedId(AppContract.Client.CLIENT_URI,client_id);
                                new AsyncTask<Void, Void, Integer>() {
                                    @Override
                                    protected Integer doInBackground(Void... params) {
                                        return getContentResolver().delete(uri, null,null);
                                    }

                                    @Override
                                    protected void onPostExecute(Integer result) {
                                        super.onPostExecute(result);


                                        if (result > 0) {
                                            //Toast.makeText(getBaseContext(), "insert car   id: " + id, Toast.LENGTH_LONG).show();
                                            Snackbar.make(findViewById(android.R.id.content), "deleted client   id: " + client_id, Snackbar.LENGTH_LONG).show();
                                            showclient();
                                            fabDelete.setVisibility(View.INVISIBLE);


                                        }
                                        else {
                                            //Toast.makeText(getBaseContext(), "error insert car  id: " + id, Toast.LENGTH_LONG).show();
                                            Snackbar.make(findViewById(android.R.id.content), "ERROR deleting client" , Snackbar.LENGTH_LONG).show();

                                        }
                                    }
                                }.execute();
                            }
                        }).show();
            }
        });







    }


}
