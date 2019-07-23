package com.warrous.ready2ride.navigation.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.navigation.fragments.NotificationsContract;
import com.warrous.ready2ride.navigation.model.NotificationsResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<
            NotificationsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;
        ArrayList<NotificationsResponse> notificationsResponses;



        public NotificationsAdapter(Context activity, OnItemClickListener mItemClickListener,ArrayList<NotificationsResponse> notificationsResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.notificationsResponses=notificationsResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notifications, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            NotificationsResponse notificationsResponse=notificationsResponses.get(position);
            holder.tvDes.setText(notificationsResponse.getDescription());
            if(notificationsResponse.getNotificationType().equalsIgnoreCase("Event")||notificationsResponse.getNotificationType().equalsIgnoreCase("Promotion")){
                holder.btnAceept.setVisibility(View.VISIBLE);
                holder.btnReject.setVisibility(View.VISIBLE);
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvName.setText(notificationsResponse.getName());
            }else{
                holder.btnAceept.setVisibility(View.GONE);
                holder.btnReject.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(notificationsResponse.getImage())){
                GlideManager.loadImageUrl(activity,notificationsResponse.getImage(),holder.ivImage);
            }else{
                if(notificationsResponse.getNotificationType().equalsIgnoreCase("Event")){
                    holder.ivImage.setBackgroundResource(R.drawable.ic_ride_bg);
                }else{
                    holder.ivImage.setBackgroundResource(R.drawable.ic_oval_photo);
                }

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
           // return 6;
              return notificationsResponses == null ? 0 : notificationsResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvDes;
            Button btnReject,btnAceept;
            CircleImageView ivImage;
            TextView tvName;



            public ViewHolder(View itemView) {
                super(itemView);
                tvDes=itemView.findViewById(R.id.tv_desc);
                btnReject=itemView.findViewById(R.id.btn_reject);
                btnAceept=itemView.findViewById(R.id.btn_accept);
                ivImage=itemView.findViewById(R.id.iv_home);
                tvName=itemView.findViewById(R.id.tv_name);



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

