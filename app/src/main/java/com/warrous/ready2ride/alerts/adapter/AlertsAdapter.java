package com.warrous.ready2ride.alerts.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.alerts.AlertsActivity;
import com.warrous.ready2ride.alerts.model.AlertListResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AlertsAdapter extends RecyclerView.Adapter<
            AlertsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
       AlertsActivity activity;
        ArrayList<AlertListResponse> alertsList;



        public AlertsAdapter(AlertsActivity activity, OnItemClickListener mItemClickListener, ArrayList<AlertListResponse> alertLists) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.alertsList=alertLists;

        }

        public void refreshData(ArrayList<AlertListResponse> listResponses) {
            alertsList=listResponses;
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_alert_items, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            if(alertsList!=null){
                AlertListResponse alertListResponse=alertsList.get(position);
                holder.tvReminder.setText(alertListResponse.getRemainder());
                holder.tvLastService.setText(alertListResponse.getLastServiced());
                if(alertListResponse.getAlertBy().equalsIgnoreCase("Time")){
                    holder.tvEvery.setText("every "+alertListResponse.getEvery()+" months");
                }else{
                    holder.tvEvery.setText("every "+alertListResponse.getEvery()+" miles");
                }
                if(alertListResponse.getServiceCategoryId()==1){
                    holder.tvAlertName.setText("Oil & Filter Change");

                }else if(alertListResponse.getServiceCategoryId()==2){
                    holder.tvAlertName.setText("Break-in Service");
                }else if(alertListResponse.getServiceCategoryId()==3){
                    holder.tvAlertName.setText("Brake Service");
                }else if(alertListResponse.getServiceCategoryId()==4){
                    holder.tvAlertName.setText("Accessory Installation");
                }else if(alertListResponse.getServiceCategoryId()==5){
                    holder.tvAlertName.setText("Tune-Up");
                }else {
                    holder.tvAlertName.setText("Winterization");
                }
                holder.btnSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onClickSchedule();
                    }
                });

            }


        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position,AlertListResponse alertResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 4;
             return alertsList == null ? 0 : alertsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvLastService,tvReminder;
            TextView tvEvery;
            TextView tvAlertName;
            Button btnSchedule;


            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                tvLastService=itemView.findViewById(R.id.tv_last_desc);
                tvReminder=itemView.findViewById(R.id.tv_reminder_des);
                tvEvery=itemView.findViewById(R.id.tv_months);
                tvAlertName=itemView.findViewById(R.id.tv_header);
                btnSchedule=itemView.findViewById(R.id.btn_schedule);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),alertsList.get(getAdapterPosition()));
                }


            }
        }

    }
