package com.warrous.ready2ride.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.DealerShipContract;
import com.warrous.ready2ride.dealership.DealershipPresenter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.model.SavePromotionRequest;
import com.warrous.ready2ride.dealership.promotions.PromotionsActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class InstorePromotionActivity extends BaseActivity implements DealerShipContract.View {

    EventsPromotionsResponse eventsPromotionsResponse;

    @BindView(R.id.tv_ascesory_heading)
    TextView tvHeading;
    @BindView(R.id.tv_offer_desc)
    TextView tvDesc;
    DealerShipContract.Presenter mPresenter;
    ArrayList<EventsPromotionsResponse> eventsListRE;
    int dealerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instore_promotion);
        injectButterKnife(this);
        mPresenter=new DealershipPresenter(this);
//        eventsListRE=new ArrayList<>();
//        eventsListRE=(ArrayList<EventsPromotionsResponse>)getIntent().getSerializableExtra("eventsList");
        eventsPromotionsResponse=(EventsPromotionsResponse) getIntent().getSerializableExtra("EventDesc");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            dealerId=bundle.getInt("dealerId");
        }
        tvHeading.setText(eventsPromotionsResponse.getName());
        tvDesc.setText(eventsPromotionsResponse.getDescription());
    }


    @OnClick(R.id.iv_back)
    public void onClickBack(){
       toActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        toActivity();
    }

    public void toActivity(){
        Intent intent=new Intent(this,PromotionsActivity.class);
        intent.putExtra("dealerId",dealerId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses) {

    }

    @Override
    public void onEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {

    }

    @Override
    public void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponsesss) {

    }

    @Override
    public void onSavePromotionSUcess(ArrayList<SignUpResponse> signUpResponses) {

        Intent intent=new Intent(this,PromotionsActivity.class);
        intent.putExtra("eventsList",eventsListRE);
        startActivity(intent);
        finish();
        //ActivityUtils.startActivity(this,PromotionsActivity.class,null);
    }
    @OnClick(R.id.btn_save_to_wallet)
    public void onClickSavetoWallet(){
        SavePromotionRequest savePromotionRequest= new SavePromotionRequest();
        savePromotionRequest.setAction(true);
        savePromotionRequest.setId(eventsPromotionsResponse.getId());
        savePromotionRequest.setNotificationType(eventsPromotionsResponse.getType());
        savePromotionRequest.setNotificationId(0);
        savePromotionRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));

        mPresenter.savePromotion(savePromotionRequest);
    }
}
