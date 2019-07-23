package com.warrous.ready2ride.bike.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.bike.model.RideList;

import java.util.ArrayList;


public class RideLogAdapter extends RecyclerView.Adapter<
            RideLogAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;

        ArrayList<RideList> rideLists;



        public RideLogAdapter(Context activity, OnItemClickListener mItemClickListener, ArrayList<RideList> rideLists) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.rideLists=rideLists;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ride_log, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            RideList rideList=rideLists.get(position);
       holder.tvRideName.setText(rideList.getRideName());
          holder.tvDuration.setText(rideList.getDuration());
            holder.tvRideHappend.setText(rideList.getRideHappened());
         holder.DistanceRidden.setText(rideList.getRideDistance()+" "+"miles");

        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 4;
            return rideLists == null ? 0 : rideLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvRideName;
            TextView tvRideHappend;
            TextView tvDuration;
            TextView DistanceRidden;



            public ViewHolder(View itemView) {
                super(itemView);
                tvRideName=itemView.findViewById(R.id.tv_name);
                tvRideHappend=itemView.findViewById(R.id.tv_ride_happend);
                tvDuration=itemView.findViewById(R.id.tv_name_desc);
                DistanceRidden=itemView.findViewById(R.id.tv_miles);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }


            }
        }
    }

