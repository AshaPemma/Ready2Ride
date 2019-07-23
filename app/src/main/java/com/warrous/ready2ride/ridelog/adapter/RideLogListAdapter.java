package com.warrous.ready2ride.ridelog.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.warrous.ready2ride.R;
import com.warrous.ready2ride.bike.model.RideList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RideLogListAdapter extends RecyclerView.Adapter<
            RideLogListAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<RideList> rideLists;



        public RideLogListAdapter(Activity activity, OnItemClickListener mItemClickListener,ArrayList<RideList> rideLists) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
           this.rideLists=rideLists;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ride_log_list_item, parent, false);

            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            RideList rideList=rideLists.get(position);
            holder.tvRideName.setText(rideList.getRideName());
            holder.tvRideDate.setText(rideList.getRideHappened());





        }




        public interface OnItemClickListener {
            void onItemClick(View view, int position,RideList rideList);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 6;
            return rideLists == null ? 0 : rideLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


            ImageView mapFragment;
            TextView tvRideName;
            TextView tvRideDate;


            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                mapFragment = itemView.findViewById(R.id.map_view);
                tvRideDate=itemView.findViewById(R.id.tv_rides);
                tvRideName=itemView.findViewById(R.id.tv_bike_name);

            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),rideLists.get(getAdapterPosition()));
                }


            }


        }
    }


