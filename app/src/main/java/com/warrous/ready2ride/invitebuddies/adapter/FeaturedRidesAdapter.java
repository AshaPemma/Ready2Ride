package com.warrous.ready2ride.invitebuddies.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.invitebuddies.DefaultInviteBuddiesActivity;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequest;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequests;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;

import java.util.ArrayList;

public class FeaturedRidesAdapter extends RecyclerView.Adapter<
            FeaturedRidesAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        DefaultInviteBuddiesActivity activity;
        ArrayList<FeaturedRidersResponse> featuredRides;



        public FeaturedRidesAdapter(DefaultInviteBuddiesActivity activity, OnItemClickListener mItemClickListener, ArrayList<FeaturedRidersResponse> featuredRidersResponses) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.featuredRides=featuredRidersResponses;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feature_riders, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final FeaturedRidersResponse featuredRidersResponse= featuredRides.get(position);
            if(!TextUtils.isEmpty(featuredRidersResponse.getFirstName())&&!TextUtils.isEmpty(featuredRidersResponse.getLastName())){
                holder.tvName.setText(featuredRidersResponse.getFirstName()+" "+featuredRidersResponse.getLastName());
                }
                holder.ivadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = "Do you want to send Friend Request to  "+featuredRidersResponse.getFirstName()+" "+featuredRidersResponse.getLastName()+"";
                        AlertUtil.showSaveAlert(LayoutInflater.from(activity.getContext()).inflate(R.layout.dialog_save, null),
                                message, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        BuddyRequests buddyRequestsL=new BuddyRequests();

                                        ArrayList<BuddyRequest> buddyRequests=new ArrayList<>();
                                        BuddyRequest buddyRequest=new BuddyRequest();
                                        buddyRequest.setAccepted(false);
                                        buddyRequest.setReceiverId(featuredRidersResponse.getOwnerId());
                                        buddyRequest.setSenderId(PreferenceManager.getIntegerValue(activity,PreferenceManager.KEY_OWNER_ID));
                                        buddyRequest.setRequestSent(true);
                                        buddyRequests.add(buddyRequest);
                                        buddyRequestsL.setBuddyRequestList(buddyRequests);
                                        activity.onBuddyRequest(buddyRequestsL);

                                    }
                                });

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
           // return 4;
              return featuredRides == null ? 0 : featuredRides.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvName;
            ImageView ivadd;



            public ViewHolder(View itemView) {
                super(itemView);
                tvName=itemView.findViewById(R.id.tv_name);
                ivadd=itemView.findViewById(R.id.iv_add);
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }


            }
        }
    }


