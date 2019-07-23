package com.warrous.ready2ride.invitebuddies.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.invitebuddies.model.BuddiesList;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;

import java.util.ArrayList;

public class MyBuddiesAdapter extends RecyclerView.Adapter<
            MyBuddiesAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<BuddyListResponse> buddyListResponses;



        public MyBuddiesAdapter(Activity activity, OnItemClickListener mItemClickListener, ArrayList<BuddyListResponse> buddyListResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.buddyListResponses=buddyListResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_buddies, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            BuddyListResponse buddyListResponse=buddyListResponses.get(position);
            holder.tvName.setText(buddyListResponse.getFName()+" "+buddyListResponse.getLName());

        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 14;
             return buddyListResponses == null ? 0 : buddyListResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            TextView tvName;

            public ViewHolder(View itemView) {
                super(itemView);

                tvName=itemView.findViewById(R.id.tv_name);




            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }


            }
        }
}
