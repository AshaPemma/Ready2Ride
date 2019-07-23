package com.warrous.ready2ride.groups.adapter;


import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import java.util.ArrayList;

public class FeaturedGroupsAdapterG extends RecyclerView.Adapter<
            FeaturedGroupsAdapterG.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<FeaturedGroupsResponse> featuredGroupsResponses;



        public FeaturedGroupsAdapterG(Activity activity, OnItemClickListener mItemClickListener, ArrayList<FeaturedGroupsResponse> featuredGroupsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.featuredGroupsResponses=featuredGroupsResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_featured_groups, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            FeaturedGroupsResponse featuredGroupsResponse=featuredGroupsResponses.get(position);

            holder.tvName.setText(featuredGroupsResponse.getName());
            holder.tvDesc.setText(featuredGroupsResponse.getDescription());
            if(!TextUtils.isEmpty(featuredGroupsResponse.getIcon())){
                GlideManager.loadImageUrl(activity,featuredGroupsResponse.getIcon(),holder.ivImage);
            }else{
                holder.ivImage.setBackgroundResource(R.drawable.ic_featured_groups_bg);
            }

        }



        public interface OnItemClickListener {
            void onItemClickF(View view, int position,FeaturedGroupsResponse featuredGroupsResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 6;
             return featuredGroupsResponses == null ? 0 : featuredGroupsResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName;
            TextView tvDesc;
            ImageView ivImage;


            public ViewHolder(View itemView) {
                super(itemView);

                tvName=itemView.findViewById(R.id.tv_name);
                tvDesc=itemView.findViewById(R.id.tv_name_desc);
                ivImage=itemView.findViewById(R.id.iv_home);

                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClickF(v, getAdapterPosition(),featuredGroupsResponses.get(getAdapterPosition()));
                }


            }
        }
}
