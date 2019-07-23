package com.warrous.ready2ride.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class InstorePromotionActivtyE extends BaseActivity {

    EventsPromotionsResponse eventsPromotionsResponse;

    @BindView(R.id.tv_ascesory_heading)
    TextView tvHeading;
    @BindView(R.id.tv_offer_desc)
    TextView tvDesc;
    @BindView(R.id.btn_save_to_wallet)
    Button btnSavetoWallet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instore_promotion);
        injectButterKnife(this);
        btnSavetoWallet.setVisibility(View.GONE);
        eventsPromotionsResponse=(EventsPromotionsResponse) getIntent().getSerializableExtra("EventDesc");
        tvHeading.setText(eventsPromotionsResponse.getName());
        tvDesc.setText(eventsPromotionsResponse.getDescription());
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
}
