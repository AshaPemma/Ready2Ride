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

public class EventActivityE extends BaseActivity {

    @BindView(R.id.tv_meet_ride)
    TextView tvName;
    @BindView(R.id.tv_details_desc)
    TextView tvDescName;
    @BindView(R.id.tv_date_desc)
    TextView tvDate;
    @BindView(R.id.btn_attend)
    Button btnAttend;



    EventsPromotionsResponse eventsPromotionsResponse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        injectButterKnife(this);
        btnAttend.setVisibility(View.GONE);
        eventsPromotionsResponse=(EventsPromotionsResponse) getIntent().getSerializableExtra("EventDesc");
        tvName.setText(eventsPromotionsResponse.getName());
        tvDate.setText(eventsPromotionsResponse.getStartDate());
        tvDescName.setText(eventsPromotionsResponse.getDescription());
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
