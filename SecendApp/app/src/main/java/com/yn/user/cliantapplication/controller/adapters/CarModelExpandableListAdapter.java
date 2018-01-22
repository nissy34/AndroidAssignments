package com.yn.user.cliantapplication.controller.adapters;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yn.user.cliantapplication.R;
import com.yn.user.cliantapplication.controller.GlideApp;
import com.yn.user.cliantapplication.controller.MapDialogFragment;
import com.yn.user.cliantapplication.model.backend.DBManagerFactory;
import com.yn.user.cliantapplication.model.entities.Branch;
import com.yn.user.cliantapplication.model.entities.Car;
import com.yn.user.cliantapplication.model.entities.CarModel;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 31/12/2017.
 */

public class CarModelExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CarModel> carModels;
    private Map<Long,List<Branch>> branchesMap;

    public CarModelExpandableListAdapter(Context context, List<CarModel> carModels, Map<Long,List<Branch>> branchesMap) {

        this.context = context;
        this.carModels = carModels;
        this.branchesMap = branchesMap;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (this.branchesMap.get(carModels.get(groupPosition).getIdCarModel()) != null)
            return this.branchesMap.get(carModels.get(groupPosition).getIdCarModel()).get(childPosition);
        else return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.branchesMap.get(this.carModels.get(groupPosition).getIdCarModel()).get(childPosition).getBranchID();
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.branch_item, parent, false);
        }
        Branch branch = this.branchesMap.get(this.carModels.get(groupPosition).getIdCarModel()).get(childPosition);

        TextView address = (TextView) convertView.findViewById(R.id.branch_address);
        TextView parking_spaces = (TextView) convertView.findViewById(R.id.branch_parking_spaces);
        final ImageButton map_button = (ImageButton) convertView.findViewById(R.id.branch_button);
        final ImageView branch_imageView = (ImageView) convertView.findViewById(R.id.branch_image);

        map_button.setTag(R.id.branch_button, branch.getBranchAddress().toString());
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
        address.setText(branch.getBranchAddress().toString());
        parking_spaces.setText(String.valueOf(branch.getNumberOfParkingSpaces()));

        GlideApp.with(context)
                .load(Uri.parse(branch.getBranchImgUrl()))
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(branch_imageView);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.branchesMap.get(carModels.get(groupPosition).getIdCarModel()) != null)
            return this.branchesMap.get(carModels.get(groupPosition).getIdCarModel()).size();
        else return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.carModels.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.carModels.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.carModels.get(groupPosition).getIdCarModel();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.carmodel_card, parent, false);
        }
        CarModel carModel = this.carModels.get(groupPosition);
        if (carModel != null) {

            TextView trans = (TextView) convertView.findViewById(R.id.cars_transmition);
            TextView description = (TextView) convertView.findViewById(R.id.cars_name_description);
            TextView classa = (TextView) convertView.findViewById(R.id.cars_class);
            TextView engine = (TextView) convertView.findViewById(R.id.cars_engineCapacity);
            TextView numseats = (TextView) convertView.findViewById(R.id.cars_numofseats);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.cars_carImage);


            numseats.setText(String.valueOf(carModel.getNumOfSeats()));
            trans.setText(String.valueOf(carModel.getNumOfSeats()));
            description.setText(carModel.getCompenyName() + " " + carModel.getModelName());
            classa.setText(String.valueOf(carModel.getCarClass()));
            engine.setText(String.valueOf(carModel.getEngineCapacity()));

            GlideApp.with(context)
                    .load(Uri.parse(carModel.getCarPic()))
                    .placeholder(R.drawable.progress_animation)
                    .centerCrop()
                    .into(imageView);

        }
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
}

