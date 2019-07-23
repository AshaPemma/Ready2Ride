package com.warrous.ready2ride.dealership.popup;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.dealership.model.DealershipResponse;

import java.util.ArrayList;
import java.util.List;


public class PopupAdapter extends RecyclerView.Adapter<
        PopupAdapter.ViewHolder> {

    OnItemClickListener mItemClickListener;
    ArrayList<DealershipResponse> categories;
    Activity activity;
    DealershipResponse selectedCategories ;
   // List<String> selectedTitles;

    public PopupAdapter(Activity activity, ArrayList<DealershipResponse> productModels, OnItemClickListener mItemClickListener) {
        this.categories = productModels;
        this.mItemClickListener = mItemClickListener;
        this.activity = activity;
       // selectedTitles = tabTitleNameList;
        selectedCategories=new DealershipResponse();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pop_up_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DealershipResponse dealershipResponse=categories.get(position);

        holder.tvName.setText(dealershipResponse.getName());
        holder.tvDesc.setText(dealershipResponse.getCity()+", "+dealershipResponse.getState()+" "+dealershipResponse.getAddress());

        if(dealershipResponse.isDefault()){
            holder.cbSelected.setChecked(true);
        }
        holder.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });




    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;

    }

    public DealershipResponse setSelectedDealerId(){
return selectedCategories;
    }
    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName,tvDesc;
        CheckBox cbSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvDesc=itemView.findViewById(R.id.tv_name_desc);
            cbSelected=itemView.findViewById(R.id.cb_category);

            if (itemView != null) {
                itemView.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }


        }
    }
}
