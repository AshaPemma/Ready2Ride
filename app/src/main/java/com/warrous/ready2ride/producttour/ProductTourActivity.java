package com.warrous.ready2ride.producttour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.createbike.CreateBikeActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.garage.DefaulGarageActivity;
import com.warrous.ready2ride.navigation.HomeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductTourActivity extends BaseActivity {

    @BindView(R.id.tv_header)
    TextView tvHeader;

    @BindView(R.id.tv_skip_tour)
    TextView tvSkip;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_tour);
        injectButterKnife(this);
        tvHeader.setVisibility(View.GONE);
        tvSkip.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.btn_start)
    public void onClickStart(){
        ActivityUtils.startActivity(this,DefaulGarageActivity.class,null);
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
        ActivityUtils.startActivity(this,CreateBikeActivity.class,null);
        finish();
    }
}
