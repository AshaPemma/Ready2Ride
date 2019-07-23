package com.warrous.ready2ride.navigation.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.navigation.model.MessagesResponse;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<
            MessagesAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;
        ArrayList<MessagesResponse> messagesResponses;




        public MessagesAdapter(Context activity, OnItemClickListener mItemClickListener,ArrayList<MessagesResponse> messagesResponse) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.messagesResponses=messagesResponse;
        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_messages, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            MessagesResponse messagesResponse=messagesResponses.get(position);
            holder.tvMessage.setText(messagesResponse.getMesage());



        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
          //  return 6;
             return messagesResponses == null ? 0 : messagesResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            TextView tvOwnerName,tvMessage;


            public ViewHolder(View itemView) {
                super(itemView);
                tvOwnerName=itemView.findViewById(R.id.tv_name);
                tvMessage=itemView.findViewById(R.id.tv_desc);
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



