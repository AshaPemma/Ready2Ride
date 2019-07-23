package com.warrous.ready2ride.auth.profile.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.profile.model.ProfileResponse;

public class ProfileItemsAdapter extends RecyclerView.Adapter<
            ProfileItemsAdapter.ViewHolder> {

        OnItemClickListener mItemClickListener;
        Context activity;
        ProfileResponse profileResponse;



        public ProfileItemsAdapter(Context activity, OnItemClickListener mItemClickListener, ProfileResponse profileResponse) {
            this.mItemClickListener = mItemClickListener;
            this.activity = activity;
            this.profileResponse=profileResponse;

        }

        public void refreshData() {
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_items, parent, false);

            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if(profileResponse!=null) {
                if (position == 0) {
                    holder.ivImage.setImageDrawable(activity.getDrawable(R.drawable.ic_ride));
                    holder.tvName.setText("Rides");
                    holder.tvNumber.setText(Integer.toString(profileResponse.getRidesCount()));

                } else if (position == 1) {
                    holder.tvNumber.setText(Integer.toString(profileResponse.getGarrage()));
                    holder.ivImage.setImageDrawable(activity.getDrawable(R.drawable.ic_garage_blue));
                    holder.tvName.setText("Garage");
                } else if (position == 2) {
                    holder.tvNumber.setText(Integer.toString(profileResponse.getBuddies()));
                    holder.tvName.setText("Buddies");
                    holder.ivImage.setImageDrawable(activity.getDrawable(R.drawable.ic_buddy));
                } else if (position == 3) {
                    holder.tvNumber.setText(Integer.toString(profileResponse.getGroupCount()));
                    holder.tvName.setText("Groups");
                    holder.ivImage.setImageDrawable(activity.getDrawable(R.drawable.ic_group_blue));
                } else if (position == 4) {
                    holder.tvNumber.setText(Integer.toString(profileResponse.getDealerShipCount()));
                    holder.tvName.setText("Dealership");
                    holder.ivImage.setImageDrawable(activity.getDrawable(R.drawable.ic_home));
                } else if (position == 5) {
                    holder.tvNumber.setText(Integer.toString(profileResponse.getWallet()));
                    holder.tvName.setText("Wallet");
                    holder.ivImage.setImageDrawable(activity.getDrawable(R.drawable.ic_wallet_blue));
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
            return 6;
            //  return serviceCentersList == null ? 0 : serviceCentersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvName;
            ImageView ivImage;
            TextView tvNumber;


            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                tvName=itemView.findViewById(R.id.tv_name);
                tvNumber=itemView.findViewById(R.id.tv_min);
                ivImage=itemView.findViewById(R.id.iv_home);
            }


            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }


            }
        }
    }

