package com.warrous.ready2ride.ridelog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.adapter.RideLogAdapter;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.ridelog.adapter.RideLogListAdapter;
import com.warrous.ready2ride.ridelog.ridedetails.RideDetailsActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RideLogListActivity extends BaseActivity implements RideLogListAdapter.OnItemClickListener{

    @BindView(R.id.rv_ride_log_list)
    RecyclerView rvRideList;
    RideLogListAdapter rideLogListAdapter;
    @BindView(R.id.tv_no_bikes)
    TextView tvNoBikes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_log_list);
        injectButterKnife(this);

        rvRideList.setLayoutManager(
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        rideLogListAdapter=new RideLogListAdapter(this,this,null);
        rvRideList.setAdapter(rideLogListAdapter);

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
    public void onItemClick(View view, int position, RideList rideList) {
        Intent intent=new Intent(this,RideDetailsActivity.class);
        intent.putExtra("rideList",rideList);
        startActivity(intent);
    }
}
