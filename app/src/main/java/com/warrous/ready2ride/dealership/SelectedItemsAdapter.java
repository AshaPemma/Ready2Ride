package com.warrous.ready2ride.dealership;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.mydealerships.MyDealershipsActivity;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.GlideManager;

import java.util.ArrayList;

public class SelectedItemsAdapter extends RecyclerView.Adapter<
            SelectedItemsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        MyDealershipsActivity activity;
        ArrayList<DealershipResponse> dealershipResponses;



        public SelectedItemsAdapter(MyDealershipsActivity activity, OnItemClickListener mItemClickListener, ArrayList<DealershipResponse> dealershipResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.dealershipResponses=dealershipResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selected_dealers, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final DealershipResponse dealershipResponse=dealershipResponses.get(position);

            if(position==0){
                holder.tvChange.setImageDrawable(activity.getContext().getResources().getDrawable(R.drawable.ic_check_circle_green));
            }else{
                holder.tvChange.setImageDrawable(activity.getContext().getResources().getDrawable(R.drawable.ic_check_grey));
            }


            if(!TextUtils.isEmpty(dealershipResponse.getBackgroundImage())){
                GlideManager.loadImageUrl(activity,dealershipResponse.getBackgroundImage(),holder.imageView);
            }else{
                holder.imageView.setBackgroundResource(R.drawable.ic_dealership_background);
            }
            holder.tvName.setText(dealershipResponse.getName());
            holder.tvDesc.setText(dealershipResponse.getCity()+", "+dealershipResponse.getState()+" "+dealershipResponse.getAddress());

            holder.tvChange.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
//                    if(position==0){


                    String message = "Do you want to select "+dealershipResponse.getName()+" as Default Dealer";
                    AlertUtil.showSaveAlert(LayoutInflater.from(activity.getContext()).inflate(R.layout.dialog_save, null),
                            message, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activity.showDealersList(dealershipResponse);

                                }
                            });
//                    }else{
////                        String message = "Do you want to remove dealer from selected Dealers";
////                        AlertUtil.showSaveAlert(LayoutInflater.from(holder.tvChange.getContext()).inflate(R.layout.dialog_save, null),
////                                message, new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        activity.removeDealer(dealershipResponses.get(position));
////                                    }
////                                });
//
//                    }

                }
            });


        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position);
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
            ImageView tvChange;
            ImageView imageView;



            public ViewHolder(View itemView) {
                super(itemView);
                tvName=itemView.findViewById(R.id.tv_name);
                tvDesc=itemView.findViewById(R.id.tv_name_desc);
                tvChange=itemView.findViewById(R.id.tv_change);
                imageView=itemView.findViewById(R.id.iv_home);
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


