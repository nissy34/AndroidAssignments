package com.yn.user.cliantapplication.controller;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.controller.adapters.BranchesExpandableListAdapter;
import com.yn.user.cliantapplication.model.backend.AppContract;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.entities.Car;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by USER on 30/12/2017.
 */

public class  BranchesFragment extends Fragment {

    public static final String BRANCH_ID ="1" ;
    BranchesExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.branches_fragment, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        findView(view);
        return view;
    }

    private void findView(View view) {
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.branch_expandable_list_view);
        /*fab = (FloatingActionButton) view.findViewById(R.id.open_order_floatingActionButton);
        fab.setVisibility(View.INVISIBLE);*/
        progressDialog=new ProgressDialog(getActivity());


        buildAdapter();

    }

    private void buildAdapter() {
        new AsyncTask<Void, Void, BranchesExpandableListAdapter>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showprogress();
            }

            @Override
            protected BranchesExpandableListAdapter doInBackground(Void... voids) {

                return new BranchesExpandableListAdapter(getActivity(), DBManagerFactory.getManager().getBranches(), DBManagerFactory.getManager().mapCarsByBranch());
            }

            @Override
            protected void onPostExecute(BranchesExpandableListAdapter branchesExpandableListAdapter) {
                // setting list adapter
                super.onPostExecute(branchesExpandableListAdapter);
                listAdapter=branchesExpandableListAdapter;
                expListView.setAdapter(branchesExpandableListAdapter);
                setHasOptionsMenu(true);
                closeProgress();

            }
        }.execute();

    }


    @Override
    public void onStart() {
        super.onStart();

        // Listview - on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                openOrder(v,groupPosition,childPosition);

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getBaseContext(),
                        "group id: "+ listAdapter.getGroupId(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void openOrder(final View viewChild, int groupPosition, int childPosition) {

        final Car car = (Car) listAdapter.getChild(groupPosition,childPosition);
/* fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
                Snackbar.make(viewChild,"order car number "+ String.valueOf(car.getIdCarNumber())+" ?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {

                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void onClick(final View view) {
                                //Snackbar.make(view, String.valueOf(sharedPreferences.getLong(getString(R.string.login_user_id),0)), Snackbar.LENGTH_LONG).show();

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                String datetime = dateformat.format(c.getTime());

                                final ContentValues orderContentValues= new ContentValues();
                                orderContentValues.put(AppContract.Order.CAR_NUM,String.valueOf(car.getIdCarNumber()));
                                orderContentValues.put(AppContract.Order.KILOMETERS_AT_RENT,String.valueOf(car.getKilometers()));
                                orderContentValues.put(AppContract.Order.CLIENT_ID,String.valueOf(sharedPreferences.getLong(getString(R.string.login_user_id),1)));
                                orderContentValues.put(AppContract.Order.RENT_DATE,datetime);
                                orderContentValues.put(AppContract.Order.ORDER_STATUS,String.valueOf(0));




                                new AsyncTask<Void, Void, Long>() {
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        showprogress();
                                    }

                                    @Override
                                    protected Long doInBackground(Void... params) {
                                        return DBManagerFactory.getManager().addOrder(orderContentValues);
                                    }

                                    @Override
                                    protected void onPostExecute(Long result) {
                                        super.onPostExecute(result);


                                        if (result > 0) {
                                            Snackbar.make(viewChild, "Car ordered: " + car.getIdCarNumber(), Snackbar.LENGTH_LONG).show();
                                           // fab.setVisibility(View.INVISIBLE);
                                            buildAdapter();
                                        } else {
                                            Snackbar.make(viewChild, "ERROR ordering car.", Snackbar.LENGTH_LONG).show();
                                            closeProgress();
                                        }

                                    }
                                }.execute();
                            }
                        }).show();
        /*    }
        });*/

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragbar,menu);
        Toast.makeText(getActivity(), "onCreateOptionsMenu", Toast.LENGTH_SHORT).show();
        final BranchesExpandableListAdapter arrayAdapter= listAdapter;
        MenuItem searchViewItem = menu.findItem(R.id.search2);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(), "onQueryTextChange", Toast.LENGTH_SHORT).show();

                arrayAdapter.getFilter().filter(newText);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
