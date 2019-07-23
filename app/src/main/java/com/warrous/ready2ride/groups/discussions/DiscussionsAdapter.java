package com.warrous.ready2ride.groups.discussions;


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
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiscussionsAdapter extends RecyclerView.Adapter<
            DiscussionsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        DiscussionsActivity activity;
        ArrayList<DiscussionsResponse> discussionsResponses;




        public DiscussionsAdapter(DiscussionsActivity activity, OnItemClickListener mItemClickListener, ArrayList<DiscussionsResponse> discussionsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.discussionsResponses=discussionsResponses;


        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_discussions, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final DiscussionsResponse discussionsResponse=discussionsResponses.get(position);
            holder.tvDescTitle.setText(discussionsResponse.getDiscussionTittle());
            holder.tvDescMessage.setText(discussionsResponse.getDiscussionDescription());

            if(!TextUtils.isEmpty(discussionsResponse.getImage())){
                holder.ivImage.setVisibility(View.VISIBLE);
                GlideManager.loadImageUrl(activity,discussionsResponse.getImage(),holder.ivImage);
            }else{
                holder.ivImage.setVisibility(View.GONE);
            }
//            holder.tvdiscussion.setOnClickListener(new View.OnClickListener() {
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

            ImageView ivImage;
            TextView tvName,tvDescTitle,tvDescMessage;
            TextView tvdiscussion;




            public ViewHolder(View itemView) {
                super(itemView);
                ivImage=itemView.findViewById(R.id.iv_discsn_bg);
                tvDescTitle=itemView.findViewById(R.id.tv_group_ride);
                tvDescMessage=itemView.findViewById(R.id.tv_ride_desc);
                tvdiscussion=itemView.findViewById(R.id.tv_discussion);

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
