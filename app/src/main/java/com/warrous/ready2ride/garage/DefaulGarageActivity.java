package com.warrous.ready2ride.garage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.createbike.CreateBikeActivity;
import com.warrous.ready2ride.dealership.DefaultDelaerShipActivity;
import com.warrous.ready2ride.framework.ActivityUtils;

import butterknife.OnClick;

public class DefaulGarageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_garage);
        injectButterKnife(this);
    }
    @OnClick(R.id.btn_set_bike)
    public void onclickSetBike(){
        ActivityUtils.startActivity(this,CreateBikeActivity.class,null);
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
   ActivityUtils.startActivity(this,DefaultDelaerShipActivity.class,null);
   finish();

    }
}
