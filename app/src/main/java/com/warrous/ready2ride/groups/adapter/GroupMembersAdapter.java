package com.warrous.ready2ride.groups.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.groups.model.GroupMembers;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;

import java.util.ArrayList;

public class GroupMembersAdapter extends RecyclerView.Adapter<
            GroupMembersAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<GroupMembers> featuredRidersResponses;



        public GroupMembersAdapter(Activity activity, OnItemClickListener mItemClickListener, ArrayList<GroupMembers> featuredRidersResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.featuredRidersResponses=featuredRidersResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_members, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            GroupMembers featuredRidersResponse= featuredRidersResponses.get(position);
            holder.tvFName.setText(featuredRidersResponse.getFirstName());
            holder.tvLName.setText(featuredRidersResponse.getLastName());



        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 6;
              return featuredRidersResponses == null ? 0 : featuredRidersResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvFName,tvLName;



            public ViewHolder(View itemView) {
                super(itemView);
               tvFName= itemView.findViewById(R.id.tv_fname);
               tvLName= itemView.findViewById(R.id.tv_lname);
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
