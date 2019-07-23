package com.warrous.ready2ride.garage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.createbike.CreateBikeActivity;
import com.warrous.ready2ride.createbike.CreateBikeActivityGarage;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.garage.adapter.GarageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GarageActivity  extends BaseActivity implements GarageContract.View,GarageAdapter.OnItemClickListener {

    @BindView(R.id.rv_bike_items)
    RecyclerView rvBikeItems;
    GarageAdapter garageAdapter;
    @BindView(R.id.tv_no_bikes)
    TextView tvNoBikes;

    GarageContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        injectButterKnife(this);
        mPresenter=new GaragePresenter(this);

        rvBikeItems.setLayoutManager(
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        garageAdapter=new GarageAdapter(this,this,null);
       rvBikeItems.setAdapter(garageAdapter);

        mPresenter.getCycleDetails(0,PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
    }

    @OnClick(R.id.btn_create_bike_profile)
    public void onClickCreateBikeProfile(){
        ActivityUtils.startActivity(this,CreateBikeActivityGarage.class,null);
        finish();
    }
    @OnClick(R.id.iv_back)
    public void onClickBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses) {
        ArrayList<DefaultBikeDetailsResponse> limitedBikes=new ArrayList<>();


        if(segmentResponses.size()!=0){
            tvNoBikes.setVisibility(View.GONE);
            rvBikeItems.setVisibility(View.VISIBLE);
            for(int i=0;i<segmentResponses.size();i++){
                if(i==5)
                    break;
                limitedBikes.add(segmentResponses.get(i));
            }
            rvBikeItems.setLayoutManager(
                    new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
            garageAdapter=new GarageAdapter(this,this,limitedBikes);
            rvBikeItems.setAdapter(garageAdapter);

        }else{
            tvNoBikes.setVisibility(View.VISIBLE);
            rvBikeItems.setVisibility(View.GONE);
        }

    }



    @Override
    public void onItemClick(View view, int position, DefaultBikeDetailsResponse defaultBikeDetailsResponse) {

        Intent intent=new Intent(this,CreateBikeActivityEdit.class);
        intent.putExtra("bikeDetails",defaultBikeDetailsResponse);
        startActivity(intent);
        finish();
    }
}
