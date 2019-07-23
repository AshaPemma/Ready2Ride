package com.warrous.ready2ride.tracksearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.tracksearch.cafe.CafesActivity;
import com.warrous.ready2ride.tracksearch.gasstations.GasStationsActivity;
import com.warrous.ready2ride.tracksearch.neardealerships.NearDealersActivity;
import com.warrous.ready2ride.tracksearch.parking.ParkingActivity;
import com.warrous.ready2ride.tracksearch.restaurants.RestaurantActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TrackSearchActivity extends BaseActivity {

    @BindView(R.id.iv_home)
    ImageView ivParking;
    @BindView(R.id.iv_bike)
    ImageView ivPetrol;
    @BindView(R.id.iv_road_track)
    ImageView ivRestauranta;
    @BindView(R.id.iv_store)
    ImageView ivCoffe;
    @BindView(R.id.iv_profile_image)
    ImageView ivDealerships;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_search);
        injectButterKnife(this);
    }
    @OnClick(R.id.iv_home)
    public void onClickParking(){
        ActivityUtils.startActivity(this,ParkingActivity.class,null);
    }

    @OnClick(R.id.iv_road_track)
    public void onClickRestaurants(){
        ActivityUtils.startActivity(this,RestaurantActivity.class,null);
    }

    @OnClick(R.id.iv_bike)
    public void onClickGasStations(){
        ActivityUtils.startActivity(this,GasStationsActivity.class,null);
    }

    @OnClick(R.id.iv_store)
    public void onClickCafes(){
        ActivityUtils.startActivity(this,CafesActivity.class,null);
    }

    @OnClick(R.id.iv_profile_image)
    public void onClickProfileImage(){
        ActivityUtils.startActivity(this,NearDealersActivity.class,null);
    }
}
