package com.warrous.ready2ride.invitebuddies.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FeaturedGroupsAdapter extends RecyclerView.Adapter<
            FeaturedGroupsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<FeaturedGroupsResponse> featuredGroups;



        public FeaturedGroupsAdapter(Activity activity, OnItemClickListener mItemClickListener,ArrayList<FeaturedGroupsResponse> featuredGroups) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.featuredGroups=featuredGroups;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }

    public void filterList(ArrayList<FeaturedGroupsResponse> filterdNames) {
        this.featuredGroups = filterdNames;
        notifyDataSetChanged();
    }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_featured_groups, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            FeaturedGroupsResponse featuredGroupsResponse=featuredGroups.get(position);

            holder.tvName.setText(featuredGroupsResponse.getName());
            holder.tvDesc.setText(featuredGroupsResponse.getDescription());



        }



        public interface OnItemClickListener {
            void onItemClickG(View view, int position,FeaturedGroupsResponse featuredGroupsResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 4;
             return featuredGroups == null ? 0 : featuredGroups.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName;
            TextView tvDesc;


            public ViewHolder(View itemView) {
                super(itemView);

                tvName=itemView.findViewById(R.id.tv_name);
                tvDesc=itemView.findViewById(R.id.tv_name_desc);

                itemView.setOnClickListener(this);


            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClickG(v, getAdapterPosition(),featuredGroups.get(getAdapterPosition()));
                }


            }
        }
    }

