package com.warrous.ready2ride.dealership.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.framework.GlideManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PromotionsEventsAdapter extends RecyclerView.Adapter<
        PromotionsEventsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;
        ArrayList<EventsPromotionsResponse> eventsPromotionsResponseArrayList;



        public PromotionsEventsAdapter(Context activity, OnItemClickListener mItemClickListener, ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.eventsPromotionsResponseArrayList=eventsPromotionsResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_promotion_events, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            EventsPromotionsResponse eventsPromotionsResponse=eventsPromotionsResponseArrayList.get(position);
            holder.tvEventName.setText(eventsPromotionsResponse.getName());
            holder.tvDescription.setText(eventsPromotionsResponse.getDescription());

            if(!TextUtils.isEmpty(eventsPromotionsResponse.getImage())){
                GlideManager.loadImageUrl(activity,eventsPromotionsResponse.getImage(),holder.ivImage);
            }else{
                holder.ivImage.setBackgroundResource(R.drawable.ic_featured_groups_bg);
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
          //  return 4;
             return eventsPromotionsResponseArrayList == null ? 0 : eventsPromotionsResponseArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            TextView tvEventName,tvDescription;
            ImageView ivImage;

            public ViewHolder(View itemView) {
                super(itemView);
                tvEventName=itemView.findViewById(R.id.tv_name);
                tvDescription=itemView.findViewById(R.id.tv_name_desc);
                ivImage=itemView.findViewById(R.id.iv_home);
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


