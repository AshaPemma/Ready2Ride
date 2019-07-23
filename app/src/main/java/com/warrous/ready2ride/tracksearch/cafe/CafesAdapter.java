package com.warrous.ready2ride.tracksearch.cafe;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.tracksearch.restaurants.RestaurantResponse;

import java.util.ArrayList;

public class CafesAdapter extends RecyclerView.Adapter<
            CafesAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<RestaurantResponse> dealersList;



        public CafesAdapter(Activity activity, OnItemClickListener mItemClickListener, ArrayList<RestaurantResponse> dealersList) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.dealersList=dealersList;

        }
        public void filterList(ArrayList<RestaurantResponse> filterdNames) {
            this.dealersList = filterdNames;
            notifyDataSetChanged();
        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cafes, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            RestaurantResponse dealershipResponse=dealersList.get(position);
            holder.tvDealerName.setText(dealershipResponse.getRestaurantName());
            holder.tvDealerDist.setText("0.0");

        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position, RestaurantResponse dealershipResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 4;
            return dealersList == null ? 0 : dealersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvDealerName;
            TextView tvDealerDist;


            public ViewHolder(View itemView) {
                super(itemView);
                tvDealerName=itemView.findViewById(R.id.tv_dealer_name);
                tvDealerDist=itemView.findViewById(R.id.tv_min);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),dealersList.get(getAdapterPosition()));
                }


            }
        }
    }


