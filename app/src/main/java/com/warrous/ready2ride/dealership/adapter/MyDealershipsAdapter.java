package com.warrous.ready2ride.dealership.adapter;


import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.dealership.DealerShipContract;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.mydealerships.MyDealershipsActivity;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.GlideManager;

import java.util.ArrayList;

public class MyDealershipsAdapter extends RecyclerView.Adapter<
            MyDealershipsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        MyDealershipsActivity activity;
        ArrayList<DealershipResponse> dealershipResponses;
        DealershipResponse dealershipResponse;



        public MyDealershipsAdapter(MyDealershipsActivity activity, OnItemClickListener mItemClickListener, ArrayList<DealershipResponse> dealershipResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.dealershipResponses=dealershipResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_dealerships, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            DealershipResponse dealershipResponse=dealershipResponses.get(position);

            holder.tvName.setText(dealershipResponse.getName());

            if(!TextUtils.isEmpty(dealershipResponse.getBackgroundImage())){
                GlideManager.loadImageUrl(activity,dealershipResponse.getBackgroundImage(),holder.imageView);
            }else{
                holder.imageView.setBackgroundResource(R.drawable.ic_dealership_background);
            }
            holder.tvDesc.setText(dealershipResponse.getCity()+", "+dealershipResponse.getState()+" "+dealershipResponse.getAddress());

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String message = "Do you want to select dealer as Default Dealer";
                    AlertUtil.showSaveAlert(LayoutInflater.from(holder.add.getContext()).inflate(R.layout.dialog_save, null),
                            message, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int dealerPos=position;
                                  DealershipResponse   dealershipResponse=new DealershipResponse();
                                    dealershipResponse=dealershipResponses.get(dealerPos);
                                    activity.addasFavouriteDealer(dealershipResponse);

                                }
                            });

                }
            });


        }




        public interface OnItemClickListener {
            void onItemClick(View view, int position,DealershipResponse dealershipResponse);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 4;
            return dealershipResponses == null ? 0 : dealershipResponses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            TextView tvName,tvDesc;
            TextView add;
            ImageView imageView;


            public ViewHolder(View itemView) {
                super(itemView);
                tvName=itemView.findViewById(R.id.tv_name);
                tvDesc=itemView.findViewById(R.id.tv_name_desc);
                add=itemView.findViewById(R.id.cb_category);

                imageView=itemView.findViewById(R.id.iv_home);
                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition(),dealershipResponses.get(getAdapterPosition()));
                }


            }
        }

    }
