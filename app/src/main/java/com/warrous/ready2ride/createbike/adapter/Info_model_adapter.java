package com.warrous.ready2ride.createbike.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.warrous.ready2ride.R;
import com.warrous.ready2ride.createbike.model.Model;

import java.util.ArrayList;

/**
* Created by IPS on 26-Mar-16.
*/
public class Info_model_adapter extends ArrayAdapter<Model> {

int textViewResourceId;

    public Info_model_adapter(Context context2, int textViewResourceId2, ArrayList<Model> objects) {
        super(context2, textViewResourceId2, objects);
        textViewResourceId = textViewResourceId2;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(textViewResourceId, parent, false);
        Model model2 = getItem(position);
        TextView label= row.findViewById(R.id.txt_bike_model_name);
        label.setText(model2.getModelName());
        label.setEllipsize(TextUtils.TruncateAt.END);
        label.setSingleLine(false);
         return row;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(textViewResourceId, parent, false);
        try
        {
            Model model2 = getItem(position);
            TextView label= row.findViewById(R.id.txt_bike_model_name);
            label.setText(model2.getModelName());
            label.setSingleLine(true);
        }catch (Exception e){

        }

        return row;
    }
}
