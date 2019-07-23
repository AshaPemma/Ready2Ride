package com.warrous.ready2ride.wallet.adapter;


import android.content.Context;
import android.media.Image;
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

import java.util.ArrayList;

public class SavedPromotionsAdapter extends RecyclerView.Adapter<
            SavedPromotionsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;
        ArrayList<EventsPromotionsResponse> eventsPromotionsResponses;



        public SavedPromotionsAdapter(Context activity, OnItemClickListener mItemClickListener, ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.eventsPromotionsResponses=eventsPromotionsResponses;


        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_saved_promotions, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            EventsPromotionsResponse eventsPromotionsResponse=eventsPromotionsResponses.get(position);
            holder.tvName.setText(eventsPromotionsResponse.getName());
            holder.tvDescription.setText(eventsPromotionsResponse.getDescription());

            if(!TextUtils.isEmpty(eventsPromotionsResponse.getImage())){
                GlideManager.loadImageUrl(activity,eventsPromotionsResponse.getImage(),holder.img);
            }else{
                holder.img.setBackgroundResource(R.drawable.ic_featured_groups_bg);
            }



        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position,EventsPromotionsResponse eventsPromotionsResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 4;
             return eventsPromotionsResponses == null ? 0 : eventsPromotionsResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName,tvDescription;
            ImageView img;



            public ViewHolder(View itemView) {
                super(itemView);
                tvName=itemView.findViewById(R.id.tv_name);
                tvDescription=itemView.findViewById(R.id.tv_name_desc);
                img=itemView.findViewById(R.id.iv_home);



                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),eventsPromotionsResponses.get(getAdapterPosition()));
                }


            }
        }
}
