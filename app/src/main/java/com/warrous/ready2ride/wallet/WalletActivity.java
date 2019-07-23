package com.warrous.ready2ride.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.DealerShipContract;
import com.warrous.ready2ride.dealership.DealershipPresenter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.wallet.adapter.AttendingEventsAdapter;
import com.warrous.ready2ride.wallet.adapter.SavedPromotionsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity implements WalletContarct.View,SavedPromotionsAdapter.OnItemClickListener,AttendingEventsAdapter.OnItemClickListenerEvent {

    @BindView(R.id.rv_saved_promotions)
    RecyclerView rvSavedPromotions;
    @BindView(R.id.rv_attending_events)
    RecyclerView rvAttendingEvents;

    SavedPromotionsAdapter savedPromotionsAdapter;
    AttendingEventsAdapter attendingEventsAdapter;
    WalletContarct.Presenter mPresenter;

    ArrayList<EventsPromotionsResponse> eventsList;
    ArrayList<EventsPromotionsResponse> promotionsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        injectButterKnife(this);
        mPresenter=new WalletPresenter(this);

        mPresenter.onGetSavedPromttions(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        mPresenter.onGetAttendingEvents(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));

      //  mPresenter.getEventList(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_DEFAUKT_DEALER_ID));





    }


    @Override
    public void onItemClick(View view, int position,EventsPromotionsResponse eventsPromotionsResponse) {

        Intent intent=new Intent(this,InstorePromotionActivtyE.class);
        intent.putExtra("EventDesc",eventsPromotionsResponse);
        startActivity(intent);
      // ActivityUtils.startActivity(this,InstorePromotionActivity.class,null);
    }

    @Override
    public void onItemClickEvent(View view, int position,EventsPromotionsResponse eventsPromotionsResponse) {
        Intent intent=new Intent(this,EventActivityE.class);
        intent.putExtra("EventDesc",eventsPromotionsResponse);
        startActivity(intent);
     //   ActivityUtils.startActivity(this,EventActivity.class,null);
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

//    @Override
//    public void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses) {
//
//    }
//
//    @Override
//    public void onEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {
//        eventsList=new ArrayList<>();
//        promotionsList=new ArrayList<>();
//
//        if(eventsPromotionsResponses!=null){
//            if(eventsPromotionsResponses.size()!=0){
//                for(int i=0;i<eventsPromotionsResponses.size();i++){
//                    if(eventsPromotionsResponses.get(i).getType().equalsIgnoreCase("Event")){
//                        eventsList.add(eventsPromotionsResponses.get(i));
//                    }else{
//                        promotionsList.add(eventsPromotionsResponses.get(i));
//                    }
//                }
//
//
//
//
//
//
//            }
//        }
//    }
//
//    @Override
//    public void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponsesss) {
//
//
//    }

    @Override
    public void onGetSavedPromotionsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {
        if (eventsPromotionsResponses != null) {
            if (eventsPromotionsResponses.size() != 0) {
                rvSavedPromotions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                savedPromotionsAdapter=new SavedPromotionsAdapter(getContext(),this,eventsPromotionsResponses);
                rvSavedPromotions.setAdapter(savedPromotionsAdapter);

            }
        }
    }

    @Override
    public void onGetAttendingEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {

        if (eventsPromotionsResponses != null) {
            if (eventsPromotionsResponses.size() != 0) {
                rvAttendingEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                attendingEventsAdapter=new AttendingEventsAdapter(getContext(),this,eventsPromotionsResponses);
                rvAttendingEvents.setAdapter(attendingEventsAdapter);

            }
        }
    }
}
