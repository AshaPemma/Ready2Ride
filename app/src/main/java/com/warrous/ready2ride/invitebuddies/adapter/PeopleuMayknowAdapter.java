package com.warrous.ready2ride.invitebuddies.adapter;


import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.invitebuddies.DefaultInviteBuddiesActivity;
import com.warrous.ready2ride.invitebuddies.DefaultInviteBuddiesContract;
import com.warrous.ready2ride.invitebuddies.model.BuddiesList;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequest;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequests;
import com.warrous.ready2ride.invitebuddies.model.ContactModel;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;

import java.util.ArrayList;

public class PeopleuMayknowAdapter extends RecyclerView.Adapter<
            PeopleuMayknowAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        DefaultInviteBuddiesActivity activity;
        ArrayList<ContactModel> contactModels;




        public PeopleuMayknowAdapter(DefaultInviteBuddiesActivity activity, OnItemClickListener mItemClickListener, ArrayList<ContactModel> contactModels) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.contactModels=contactModels;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }

    public void filterList(ArrayList<ContactModel> filterdNames) {
        this.contactModels = filterdNames;
        notifyDataSetChanged();
    }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_people_u_may_know, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final ContactModel contactModel=contactModels.get(position);
            holder.tvName.setText(contactModel.getName());
            if(contactModel.isBuddy){
                holder.ivAdd.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_send_blue));
            }else{
                holder.ivAdd.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_add));
            }

            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(contactModel.isBuddy()){

                    }else{

                        String message = "Do you want to send Friend Request to "+contactModel.getName()+"";
                        AlertUtil.showSaveAlert(LayoutInflater.from(activity.getContext()).inflate(R.layout.dialog_save, null),
                                message, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        BuddyRequests buddyRequestsL=new BuddyRequests();

                                        ArrayList<BuddyRequest> buddyRequests=new ArrayList<>();
                                        BuddyRequest buddyRequest=new BuddyRequest();
                                        buddyRequest.setAccepted(false);
                                        buddyRequest.setReceiverId(contactModel.getOwnerId());
                                        buddyRequest.setSenderId(PreferenceManager.getIntegerValue(activity,PreferenceManager.KEY_OWNER_ID));
                                        buddyRequest.setRequestSent(true);
                                        buddyRequests.add(buddyRequest);
                                        buddyRequestsL.setBuddyRequestList(buddyRequests);
                                        activity.onBuddyRequest(buddyRequestsL);

                                    }
                                });

                    }
                }
            });


        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position, InviteBuddiesRequest inviteBuddiesRequest);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
            //return 4;
            return contactModels == null ? 0 : contactModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            TextView tvName;
            ImageView ivAdd;
            public ViewHolder(View itemView) {
                super(itemView);


       tvName=itemView.findViewById(R.id.tv_name);
       ivAdd=itemView.findViewById(R.id.iv_add);

                itemView.setOnClickListener(this);


            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    InviteBuddiesRequest inviteBuddiesRequest=new InviteBuddiesRequest();
                    ContactModel contactModel=contactModels.get(getAdapterPosition());

                    BuddiesList buddiesList=new BuddiesList();
                    ArrayList<BuddiesList> inviteBuddiesList=new ArrayList<>();

                    buddiesList.setSenderId(PreferenceManager.getIntegerValue(activity.getApplicationContext(),PreferenceManager.KEY_OWNER_ID));
                    buddiesList.setFName(contactModel.getName());
                    buddiesList.setOwnerId(PreferenceManager.getIntegerValue(activity.getApplicationContext(),PreferenceManager.KEY_OWNER_ID));
                    buddiesList.setLName(contactModel.getName());
                    buddiesList.setMobile(contactModel.getMobileNumber().trim().replaceAll("\\s+", ""));
                    buddiesList.setEmail(contactModel.getName());
                    inviteBuddiesList.add(buddiesList);
                    inviteBuddiesRequest.setBuddyList(inviteBuddiesList);
                    mItemClickListener.onItemClick(v, getAdapterPosition(),inviteBuddiesRequest);
                }


            }
        }
    }


