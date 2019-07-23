package com.warrous.ready2ride.dealership.promotions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.DealershipPresenter;
import com.warrous.ready2ride.dealership.adapter.PromotionsEventsAdapter;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.wallet.EventActivity;
import com.warrous.ready2ride.wallet.InstorePromotionActivity;
import com.warrous.ready2ride.wallet.adapter.AttendingEventsAdapter;
import com.warrous.ready2ride.wallet.adapter.SavedPromotionsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PromotionsActivity extends BaseActivity implements SavedPromotionsAdapter.OnItemClickListener,AttendingEventsAdapter.OnItemClickListenerEvent,PromotionsContract.View {

    @BindView(R.id.rv_saved_promotions)
    RecyclerView rvSavedPromotions;
    @BindView(R.id.rv_attending_events)
    RecyclerView rvAttendingEvents;
    ArrayList<EventsPromotionsResponse> eventsListRE;
    PromotionsEventsAdapter promotionsEventsAdapter;
    SavedPromotionsAdapter savedPromotionsAdapter;
    AttendingEventsAdapter attendingEventsAdapter;

    ArrayList<EventsPromotionsResponse> eventsListe;
    ArrayList<EventsPromotionsResponse> promotionsListe;
    int dealerId;
    PromotionsContract.Presenter mPresenter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_events);
        injectButterKnife(this);
        eventsListRE=new ArrayList<>();
       // eventsListRE=(ArrayList<EventsPromotionsResponse>)getIntent().getSerializableExtra("eventsList");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            dealerId=bundle.getInt("dealerId");
        }

        mPresenter=new PromotionsPresenter(this);
        mPresenter.onGetPromotions(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID),dealerId);
        mPresenter.onGetEvents(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID),dealerId);


//        if(eventsListRE!=null){
//            if(eventsListRE.size()!=0){
//                for(int i=0;i<eventsListRE.size();i++){
//                    if(eventsListRE.get(i).getType().equalsIgnoreCase("Event")){
//                        eventsList.add(eventsListRE.get(i));
//                    }else{
//                        promotionsList.add(eventsListRE.get(i));
//                    }
//                }


//                rvSavedPromotions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                savedPromotionsAdapter=new SavedPromotionsAdapter(getContext(),this,null);
//                rvSavedPromotions.setAdapter(savedPromotionsAdapter);
//
//
//                rvAttendingEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                attendingEventsAdapter=new AttendingEventsAdapter(getContext(),this,null);
//                rvAttendingEvents.setAdapter(attendingEventsAdapter);
           // }
      //  }

    }
    @OnClick(R.id.iv_back)
    public void onClickBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemClick(View view, int position,EventsPromotionsResponse eventsPromotionsResponse) {

        Intent intent=new Intent(this,InstorePromotionActivity.class);
        intent.putExtra("dealerId",dealerId);
        intent.putExtra("EventDesc",eventsPromotionsResponse);
       // intent.putExtra("eventsList",eventsListRE);
        startActivity(intent);
      finish();

        // ActivityUtils.startActivity(this,InstorePromotionActivity.class,null);
    }

    @Override
    public void onItemClickEvent(View view, int position,EventsPromotionsResponse eventsPromotionsResponse) {
        Intent intent=new Intent(this,EventActivity.class);
        intent.putExtra("dealerId",dealerId);
        intent.putExtra("EventDesc",eventsPromotionsResponse);
       // intent.putExtra("eventsList",eventsListRE);
        startActivity(intent);
        finish();
        //   ActivityUtils.startActivity(this,EventActivity.class,null);
    }

    @Override
    public void onGetPromotionsSucess(ArrayList<EventsPromotionsResponse> promotionsList) {
        promotionsListe=new ArrayList<>();

        if(promotionsList!=null){
            if(promotionsList.size()!=0) {
                promotionsListe=promotionsList;
                rvSavedPromotions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                savedPromotionsAdapter=new SavedPromotionsAdapter(getContext(),this,promotionsListe);
                rvSavedPromotions.setAdapter(savedPromotionsAdapter);
            }
            }

    }

    @Override
    public void onGetEventsSucess(ArrayList<EventsPromotionsResponse> eventsList) {
        eventsListe=new ArrayList<>();

        if(eventsList!=null){
            if(eventsList.size()!=0) {
                eventsListe=eventsList;
                rvAttendingEvents.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                attendingEventsAdapter=new AttendingEventsAdapter(getContext(),this,eventsListe);
                rvAttendingEvents.setAdapter(attendingEventsAdapter);
            }
        }


    }
}
