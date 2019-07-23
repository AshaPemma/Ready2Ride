package com.warrous.ready2ride.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.invitebuddies.DefaultInviteBuddiesActivity;
import com.warrous.ready2ride.trackrides.TrackRideActivity;

import butterknife.OnClick;

public class DiscoverActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        injectButterKnife(this);
    }
    @OnClick(R.id.btn_buddies)
    public void onClickBuddies(){
        ActivityUtils.startActivity(this,DefaultInviteBuddiesActivity.class,null);
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

    @OnClick(R.id.tv_skip_tour)
    public void onClickSkip(){

        ActivityUtils.startActivity(this,TrackRideActivity.class,null);
        finish();

    }
}
