package com.warrous.ready2ride.trackrides;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.navigation.HomeActivity;

import butterknife.OnClick;

public class TrackRideActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_rides);
        injectButterKnife(this);
    }
    @OnClick(R.id.btn_start_tracking)
    public void onClickStartTrack(){
        ActivityUtils.startActivity(this,HomeActivity.class,null);
    }

    @OnClick(R.id.iv_back)
    public void onBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.tv_skip_tour)
    public void onClickSkipTour(){

    }
}
