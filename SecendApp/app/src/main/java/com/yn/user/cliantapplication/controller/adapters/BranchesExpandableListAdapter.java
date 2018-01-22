package com.yn.user.cliantapplication.controller.adapters;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.controller.GlideApp;
import com.yn.user.cliantapplication.controller.MapDialogFragment;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.entities.Branch;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by USER on 30/12/2017.
 */


public class BranchesExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {

    private Filter filter;
    private Context context;
    private List<Branch> branches;
    private List<Branch> originalBranches;

    private Map<Long,List<Car>> carMap;

    public BranchesExpandableListAdapter(Context context,List<Branch> branches,Map<Long,List<Car>> carMap) {

        this.context = context;
        this.branches = branches;
        this.carMap = carMap;
        this.originalBranches=branches;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (this.carMap.get(branches.get(groupPosition).getBranchID()) != null)
            return this.carMap.get(branches.get(groupPosition).getBranchID()).get(childPosition);
        else return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.carMap.get(this.branches.get(groupPosition).getBranchID()).get(childPosition).getIdCarNumber();
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.car_item_card, parent, false);
        }
            Car car = this.carMap.get(this.branches.get(groupPosition).getBranchID()).get(childPosition);

        long modelid = car.getCarModelID();


        //((CardView)convertView.findViewById(R.id.card_view)).setCardBackgroundColor(context.getResources().getColor(R.color.child_item_color));
        CarModel carModel = DBManagerFactory.getManager().getCarModel(modelid);

        if (carModel != null) {

            TextView trans = (TextView) convertView.findViewById(R.id.cars_transmition);
            TextView description = (TextView) convertView.findViewById(R.id.cars_name_description);
            TextView classa = (TextView) convertView.findViewById(R.id.cars_class);
            TextView engine = (TextView) convertView.findViewById(R.id.cars_engineCapacity);
            TextView numseats = (TextView) convertView.findViewById(R.id.cars_numofseats);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.cars_carImage);


            numseats.setText(String.valueOf(carModel.getNumOfSeats()));
            trans.setText(carModel.getTransmissionType().toString());
            description.setText(carModel.getCompenyName() + " " + carModel.getModelName());
            classa.setText(carModel.getCarClass().toString());
            engine.setText(String.valueOf(carModel.getEngineCapacity()));

            GlideApp.with(context)
                    .load(carModel.getCarPic())
                    .placeholder(R.drawable.progress_animation)
                    .centerCrop()
                    .into(imageView);
        }

        TextView carid = (TextView) convertView.findViewById(R.id.car_id);
        TextView carkilo = (TextView) convertView.findViewById(R.id.car_kilo);
        TextView branch = (TextView) convertView.findViewById(R.id.car_branch);

        carid.setText(String.valueOf(car.getIdCarNumber()));
        carkilo.setText(String.valueOf(car.getKilometers()));
        branch.setText(String.valueOf(car.getBranchNum()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.carMap.get(branches.get(groupPosition).getBranchID()) != null)
            return this.carMap.get(branches.get(groupPosition).getBranchID()).size();
        else return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.branches.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.branches.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.branches.get(groupPosition).getBranchID();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.branch_item, parent, false);
        }

        TextView address = (TextView) convertView.findViewById(R.id.branch_address);
        TextView parking_spaces = (TextView) convertView.findViewById(R.id.branch_parking_spaces);
        final ImageButton map_button = (ImageButton) convertView.findViewById(R.id.branch_button);
        final ImageView branch_imageView = (ImageView) convertView.findViewById(R.id.branch_image);

        map_button.setTag(R.id.branch_button, branches.get(groupPosition).getBranchAddress().toString());
        System.out.println(map_button.getTag(R.id.branch_button));

        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (view == map_button) {
                    MapDialogFragment dialog = new MapDialogFragment();
                    dialog.onAttach(context);
                    dialog.setAddress(((ImageButton) view).getTag(R.id.branch_button).toString());
                    dialog.onCreateDialog(Bundle.EMPTY);
                }

            }
        });
        address.setText(branches.get(groupPosition).getBranchAddress().toString());
        parking_spaces.setText(String.valueOf(branches.get(groupPosition).getNumberOfParkingSpaces()));

        GlideApp.with(context)
                .load(Uri.parse(branches.get(groupPosition).getBranchImgUrl()))
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(branch_imageView);

    return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    @Override
    public Filter getFilter() {

        if (filter == null)
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();

                    //If there's nothing to filter on, return the original data for your list
                    if (charSequence == null || charSequence.length() == 0) {
                        results.values = originalBranches;
                        results.count = originalBranches.size();
                    } else {
                        List filterResultsData = new ArrayList<Branch>();

                        for (Branch data : originalBranches
                                ) {
                            //In this loop, you'll filter through originalData and compare each item to charSequence.
                            //If you find a match, add it to your new ArrayList
                            //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
                            if (data.getBranchAddress().getCity().trim().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                                filterResultsData.add(data);
                            }
                        }

                        results.values = filterResultsData;
                        results.count = filterResultsData.size();
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    branches = (ArrayList<Branch>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

        return filter;


    }



}

