package com.warrous.ready2ride.createbike.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.warrous.ready2ride.R;
import com.warrous.ready2ride.createbike.model.Year;

import java.util.ArrayList;

public class Info_year_adapter extends ArrayAdapter<Year> {
    int textViewResourceId;

    public Info_year_adapter(Context context2, int textViewResourceId2, ArrayList<Year> objects) {
        super(context2, textViewResourceId2, objects);
        textViewResourceId = textViewResourceId2;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(textViewResourceId, parent, false);
        Year model2 = getItem(position);
        TextView label= row.findViewById(R.id.txt_bike_model_name);
        label.setText(model2.getYearValue());
        label.setEllipsize(TextUtils.TruncateAt.END);
        label.setSingleLine(false);
        return row;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(textViewResourceId, parent, false);
        try{
            Year model2 = getItem(position);
            TextView label= row.findViewById(R.id.txt_bike_model_name);
            label.setText(model2.getYearValue());
            Log.e("Year",model2.getYearValue());
            label.setSingleLine(true);
        }catch (Exception e){

        }

        return row;
    }
}
