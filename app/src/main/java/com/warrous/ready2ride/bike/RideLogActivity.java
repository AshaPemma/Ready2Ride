package com.warrous.ready2ride.bike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.adapter.RideLogAdapter;
import com.warrous.ready2ride.bike.model.RideList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RideLogActivity  extends BaseActivity {

    @BindView(R.id.rv_ride_log)
    RecyclerView rvRideList;
    RideLogAdapter rideLogAdapter;

    ArrayList<RideList> rideList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_log);
        injectButterKnife(this);
        rideList=new ArrayList<>();
        rideList=(ArrayList<RideList>)getIntent().getSerializableExtra("ridesResponse");

        rvRideList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rideLogAdapter = new RideLogAdapter(getContext(), null, rideList);
        rvRideList.setAdapter(rideLogAdapter);
        }

        @OnClick(R.id.iv_back)
        public void onClickBack(){
        onBackPressed();
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
