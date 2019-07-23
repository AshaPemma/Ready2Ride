package com.warrous.ready2ride.groups.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.groups.discussions.model.DiscussionCommentsResponse;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;

import java.util.ArrayList;


public class DiscussionCommntsAdapter extends RecyclerView.Adapter<
            DiscussionCommntsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
        ArrayList<DiscussionCommentsResponse> discussionsResponses;



        public DiscussionCommntsAdapter(Activity activity, OnItemClickListener mItemClickListener, ArrayList<DiscussionCommentsResponse> discussionsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.discussionsResponses=discussionsResponses;


        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapteer_discussion_comments, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            DiscussionCommentsResponse discussionsResponse=discussionsResponses.get(position);
           // holder.tvDescTitle.setText(discussionsResponse.getDiscussionTittle());
            holder.tvDescMessage.setText(discussionsResponse.getDiscussionComment());
            holder.tvName.setText(discussionsResponse.getFirstName()+""+discussionsResponse.getLastName());

            if(!TextUtils.isEmpty(discussionsResponse.getImageUrl())){
                holder.ivImage.setVisibility(View.VISIBLE);
                GlideManager.loadImageUrl(activity,discussionsResponse.getImageUrl(),holder.ivImage);
            }else{
                holder.ivImage.setVisibility(View.GONE);
            }


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
            return discussionsResponses == null ? 0 : discussionsResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            TextView tvName,tvDescMessage;
            ImageView ivImage;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName=itemView.findViewById(R.id.tv_name);
               // tvDescTitle=itemView.findViewById(R.id.tv_group_ride);
                tvDescMessage=itemView.findViewById(R.id.tv_ride_desc);
                ivImage=itemView.findViewById(R.id.iv_discsn_bg);
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



