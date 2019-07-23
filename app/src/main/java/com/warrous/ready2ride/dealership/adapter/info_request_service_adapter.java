package com.warrous.ready2ride.dealership.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;

import java.util.ArrayList;


    public class info_request_service_adapter extends ArrayAdapter<DefaultBikeDetailsResponse> {

        int textViewResourceId;

        public info_request_service_adapter(Context context2, int textViewResourceId2, ArrayList<DefaultBikeDetailsResponse> objects) {
            super(context2, textViewResourceId2, objects);
            textViewResourceId = textViewResourceId2;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(textViewResourceId, parent, false);
            DefaultBikeDetailsResponse model2 = getItem(position);
            TextView label= row.findViewById(R.id.txt_bike_model_name);
            label.setText(model2.getCycleName());
            label.setEllipsize(TextUtils.TruncateAt.END);
            label.setSingleLine(false);
            return row;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(textViewResourceId, parent, false);
            try
            {
                DefaultBikeDetailsResponse model2 = getItem(position);
                TextView label= row.findViewById(R.id.txt_bike_model_name);
                label.setText(model2.getCycleName());
                label.setSingleLine(true);
            }catch (Exception e){

            }

            return row;
        }
    }


