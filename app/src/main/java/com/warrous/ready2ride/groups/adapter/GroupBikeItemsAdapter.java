package com.warrous.ready2ride.groups.adapter;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.groups.model.GroupsListResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class GroupBikeItemsAdapter extends RecyclerView.Adapter<
            GroupBikeItemsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<FeaturedGroupsResponse> groupsListResponses;



        public GroupBikeItemsAdapter(Activity activity, OnItemClickListener mItemClickListener,ArrayList<FeaturedGroupsResponse> groupsListResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.groupsListResponses=groupsListResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_groups, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if(groupsListResponses!=null){
                FeaturedGroupsResponse groupsListResponse=groupsListResponses.get(position);
                if(groupsListResponse!=null){
                    holder.tvGroupName.setText(groupsListResponse.getName());
                    holder.tvCount.setText(groupsListResponse.getOwnerDetails().get(0).getGroupCount()+" members");
                    if(groupsListResponse.getIcon()!=null){
                        GlideManager.loadImageUrl(activity,groupsListResponse.getIcon(),holder.ivGroupImg);
                    }else{

                    }
                }


            }





        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position,FeaturedGroupsResponse featuredGroupsResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 6;
             return groupsListResponses == null ? 0 : groupsListResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvGroupName;
            SimpleDraweeView ivGroupImg;
            TextView tvCount;


            public ViewHolder(View itemView) {
                super(itemView);
                tvGroupName=itemView.findViewById(R.id.tv_bike_name);
                ivGroupImg=itemView.findViewById(R.id.iv_group);
                tvCount=itemView.findViewById(R.id.tv_mem_count);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),groupsListResponses.get(getAdapterPosition()));
                }


            }
        }

    }
