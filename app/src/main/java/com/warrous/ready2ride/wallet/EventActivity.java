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

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class EventActivity extends BaseActivity implements DealerShipContract.View {

    @BindView(R.id.tv_meet_ride)
    TextView tvName;
    @BindView(R.id.tv_details_desc)
    TextView tvDescName;
    @BindView(R.id.tv_date_desc)
            TextView tvDate;
    DealerShipContract.Presenter mPresenter;


    int dealerId;
    EventsPromotionsResponse eventsPromotionsResponse;
    //ArrayList<EventsPromotionsResponse> eventsListRE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        injectButterKnife(this);
//        eventsListRE=new ArrayList<>();
//        eventsListRE=(ArrayList<EventsPromotionsResponse>)getIntent().getSerializableExtra("eventsList");
        mPresenter=new DealershipPresenter(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            dealerId=bundle.getInt("dealerId");
        }
        eventsPromotionsResponse=(EventsPromotionsResponse) getIntent().getSerializableExtra("EventDesc");
        if(eventsPromotionsResponse!=null){
            tvName.setText(eventsPromotionsResponse.getName());
            tvDate.setText(eventsPromotionsResponse.getStartDate());
            tvDescName.setText(eventsPromotionsResponse.getDescription());
        }

    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
       toActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toActivity();
       // finish();
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
      //  ActivityUtils.startActivity(this,PromotionsActivity.class,null);
        Intent intent=new Intent(this,PromotionsActivity.class);
        intent.putExtra("dealerId",dealerId);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_attend)
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
