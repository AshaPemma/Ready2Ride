package com.warrous.ready2ride.rideslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.DefaultBikeContract;
import com.warrous.ready2ride.bike.DefaultBikePresenter;
import com.warrous.ready2ride.bike.adapter.RideLogAdapter;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.ridelog.adapter.RideLogListAdapter;
import com.warrous.ready2ride.ridelog.ridedetails.RideDetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RideListActivity extends BaseActivity implements DefaultBikeContract.View ,RideLogListAdapter.OnItemClickListener {

    @BindView(R.id.rv_ride_log)
    RecyclerView rvRideList;
   // RideLogAdapter rideLogAdapter;
    int cycleId;

    ArrayList<RideList> rideList;
    DefaultBikeContract.Presenter mPresenter;
    RideLogListAdapter rideLogListAdapter;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_log);
        injectButterKnife(this);
        rideList=new ArrayList<>();
        //rideList=(ArrayList<RideList>)getIntent().getSerializableExtra("ridesResponse");
        mPresenter=new DefaultBikePresenter(this);
        mPresenter.getCycleDetails(0,PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));


        rvRideList.setLayoutManager(
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        rideLogListAdapter = new RideLogListAdapter(this, this, rideList);
        rvRideList.setAdapter(rideLogListAdapter);
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

    @Override
    public void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses) {
        if(segmentResponses!=null){
            if(segmentResponses.size()!=0){
                cycleId=segmentResponses.get(0).getCycleId();
            }
            mPresenter.getRideDetails(cycleId,PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));

        }
    }

    @Override
    public void onGetRideLogResponse(ArrayList<RideList> segmentResponses) {

        rvRideList.setLayoutManager(
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        rideLogListAdapter = new RideLogListAdapter(this, this, segmentResponses);
        rvRideList.setAdapter(rideLogListAdapter);
    }



    @Override
    public void onItemClick(View view, int position, RideList rideList) {
        Intent intent=new Intent(this,RideDetailsActivity.class);
        intent.putExtra("rideList",rideList);
        startActivity(intent);
    }
}
