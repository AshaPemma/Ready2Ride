package com.warrous.ready2ride.dealership;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.discover.DiscoverActivity;
import com.warrous.ready2ride.finddealer.FindDealerActivity;
import com.warrous.ready2ride.framework.ActivityUtils;

import butterknife.OnClick;

public class DefaultDelaerShipActivity extends BaseActivity {


      @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_dealersip);
        injectButterKnife(this);
    }
    @OnClick(R.id.btn_find_my_dealership)
    public void onClickFindMydealerShip(){
        ActivityUtils.startActivity(this,FindDealerActivity.class,null);
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
ActivityUtils.startActivity(this,DiscoverActivity.class,null);
finish();
    }
}
