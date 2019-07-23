package com.warrous.ready2ride.garage.adapter;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.framework.GlideManager;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GarageAdapter extends RecyclerView.Adapter<
            GarageAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<DefaultBikeDetailsResponse> defaultBikeDetailsResponses;



        public GarageAdapter(Activity activity, OnItemClickListener mItemClickListener,ArrayList<DefaultBikeDetailsResponse> defaultBikeDetailsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.defaultBikeDetailsResponses=defaultBikeDetailsResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_garage_item, parent, false);

            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            DefaultBikeDetailsResponse defaultBikeDetailsResponse=defaultBikeDetailsResponses.get(position);

            holder.tvBikeName.setText(defaultBikeDetailsResponse.getCycleName());

            GlideManager.loadImageUrl(activity,defaultBikeDetailsResponse.getPhotoPath(),holder.ivBikeImage);
            if(!defaultBikeDetailsResponse.getRideList().isEmpty()){
                holder.tvRides.setText(Integer.toString(defaultBikeDetailsResponse.getRideList().get(0).getTotalRides())+" Rides");
            }else{
                holder.tvRides.setText("0 Rides");
            }



        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position,DefaultBikeDetailsResponse defaultBikeDetailsResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 6;
             return defaultBikeDetailsResponses == null ? 0 : defaultBikeDetailsResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvBikeName,tvRides;
            ImageView ivBikeImage;


            public ViewHolder(View itemView) {
                super(itemView);
               tvBikeName= itemView.findViewById(R.id.tv_bike_name);
               tvRides= itemView.findViewById(R.id.tv_rides);
               ivBikeImage=itemView.findViewById(R.id.iv_bike);

                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),defaultBikeDetailsResponses.get(getAdapterPosition()));
                }


            }
        }
}
