package com.warrous.ready2ride.rides.adapter;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;
import com.warrous.ready2ride.rides.SaveRideresponse;

import java.util.ArrayList;

public class RideSummaryAdapter extends RecyclerView.Adapter<
            RideSummaryAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<SaveRideresponse> saveRideresponses;
     //   ArrayList<FeaturedRidersResponse> featuredRides;



        public RideSummaryAdapter(Activity activity, OnItemClickListener mItemClickListener,ArrayList<SaveRideresponse> saveRideresponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.saveRideresponses=saveRideresponses;
         //   this.featuredRides=featuredRidersResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ride_summary, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            SaveRideresponse saveRideresponse= saveRideresponses.get(position);
            holder.tvDistance.setText(String.valueOf(saveRideresponse.getDistance()));
            holder.tvAvg.setText(String.valueOf(saveRideresponse.getSpeed()));
            holder.tvTime.setText(String.valueOf(saveRideresponse.getTime()));
            holder.ivLog.setColorFilter(Integer.parseInt(saveRideresponse.getColor()), android.graphics.PorterDuff.Mode.MULTIPLY);

        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 5;
            return saveRideresponses == null ? 0 : saveRideresponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvAvg,tvTop,tvTime,tvDistance;
            ImageView ivLog;



            public ViewHolder(View itemView) {
                super(itemView);
                tvAvg=itemView.findViewById(R.id.tv_avg);
                tvTop=itemView.findViewById(R.id.tv_top);
                tvTime=itemView.findViewById(R.id.tv_time);
                tvDistance=itemView.findViewById(R.id.tv_dist);
                ivLog=itemView.findViewById(R.id.tv_log);





            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }


            }
        }
    }


