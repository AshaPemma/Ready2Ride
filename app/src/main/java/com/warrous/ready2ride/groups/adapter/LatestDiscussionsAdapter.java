package com.warrous.ready2ride.groups.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.groups.DetailedGroupActivity;
import com.warrous.ready2ride.groups.discussions.DiscussionsActivity;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;
import com.warrous.ready2ride.groups.discussions.model.StartDiscussionRequest;

import java.util.ArrayList;

public class LatestDiscussionsAdapter extends RecyclerView.Adapter<
            LatestDiscussionsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
    DetailedGroupActivity activity;
        ArrayList<DiscussionsResponse> discussionsResponses;



        public LatestDiscussionsAdapter(DetailedGroupActivity activity, OnItemClickListener mItemClickListener, ArrayList<DiscussionsResponse> discussionsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.discussionsResponses=discussionsResponses;


        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_latest_discussion, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final DiscussionsResponse discussionsResponse=discussionsResponses.get(position);
            holder.tvDescTitle.setText(discussionsResponse.getDiscussionTittle());
            holder.tvDescMessage.setText(discussionsResponse.getDiscussionDescription());
//            holder.tvComments.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    activity.onDisplayDiscussionComments(discussionsResponse.getDiscussionId());
//                }
//            });


        }


        public interface OnItemClickListener {
            void onItemClick(View view, int position,int discussionId);
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


            TextView tvName,tvDescTitle,tvDescMessage,tvComments;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName=itemView.findViewById(R.id.tv_name);
                tvDescTitle=itemView.findViewById(R.id.tv_group_ride);
                tvDescMessage=itemView.findViewById(R.id.tv_ride_desc);
                tvComments=itemView.findViewById(R.id.tv_comments);

                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),discussionsResponses.get(getAdapterPosition()).getDiscussionId());
                }


            }
        }


    }

