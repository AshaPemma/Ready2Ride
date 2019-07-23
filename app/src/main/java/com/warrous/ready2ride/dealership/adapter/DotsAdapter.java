package com.warrous.ready2ride.dealership.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.warrous.ready2ride.R;

public class DotsAdapter extends RecyclerView.Adapter<
        DotsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;
        int size,pos;//ArrayList<DealershipResponse> dealershipResponses;
    int selectedPos=0;



        public DotsAdapter(Context activity, OnItemClickListener mItemClickListener,int size,int position) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.size=size;
            this.pos=position;
           // this.dealershipResponses=dealershipResponses;

        }
        public int getSelectedPosition(){
            return pos;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dots, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if(position==pos){
                holder.ivDot.setBackgroundResource(R.drawable.ic_selected_oval);
            }else{
                holder.ivDot.setBackgroundResource(R.drawable.ic_unselected);
            }


            //            DealershipResponse dealershipResponse=dealershipResponses.get(position);
//
//            holder.tvName.setText(dealershipResponse.getName());
//            holder.tvDesc.setText(dealershipResponse.getCity()+", "+dealershipResponse.getState()+" "+dealershipResponse.getAddress());



        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            return size;
           //return dealershipResponses == null ? 0 : dealershipResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


          //  TextView tvName,tvDesc;
            ImageView ivDot;


            public ViewHolder(View itemView) {
                super(itemView);
//                tvName=itemView.findViewById(R.id.tv_name);
//                tvDesc=itemView.findViewById(R.id.tv_name_desc);
                ivDot=itemView.findViewById(R.id.iv_dots);

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


