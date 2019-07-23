package com.warrous.ready2ride.invitebuddies.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.invitebuddies.model.BuddiesList;
import com.warrous.ready2ride.invitebuddies.model.ContactModel;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;

import java.util.ArrayList;

public class InviteBuddiesAdapter extends RecyclerView.Adapter<
            InviteBuddiesAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Activity activity;
    ArrayList<ContactModel> contactModels;


        public InviteBuddiesAdapter(Activity activity, OnItemClickListener mItemClickListener, ArrayList<ContactModel> contactModels) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buddy_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            ContactModel contactModel= contactModels.get(position);
            holder.tvName.setText(contactModel.getName());



        }



        public interface OnItemClickListener {
            void onItemClick(View view, int position,InviteBuddiesRequest inviteBuddiesRequest);
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;

        }

        @Override
        public int getItemCount() {
           // return 4;
            return contactModels == null ? 0 : contactModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvName;

            public ViewHolder(View itemView) {
                super(itemView);

                tvName=itemView.findViewById(R.id.tv_name);
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
                    if(!TextUtils.isEmpty(contactModel.getMobileNumber())){
                        buddiesList.setMobile(contactModel.getMobileNumber().trim().replaceAll("\\s+", ""));
                        }
                    buddiesList.setEmail(contactModel.getName());
                    inviteBuddiesList.add(buddiesList);
                    inviteBuddiesRequest.setBuddyList(inviteBuddiesList);
                    mItemClickListener.onItemClick(v, getAdapterPosition(),inviteBuddiesRequest);
                }


            }
        }
}
