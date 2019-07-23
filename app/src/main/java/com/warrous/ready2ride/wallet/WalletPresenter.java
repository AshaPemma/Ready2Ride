package com.warrous.ready2ride.wallet;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;

import java.util.ArrayList;

public class WalletPresenter extends BasePresenter implements WalletContarct.Presenter {

    WalletContarct.View view;

    public WalletPresenter(WalletContarct.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onGetSavedPromttions(int ownerId) {

execute(getApiInterface().getSavedPromotions(ownerId));
    }

    @Override
    public void onGetAttendingEvents(int ownerId) {
execute(getApiInterface().getAttendingEvents(ownerId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Events/GetSavedPromotions")){
            ArrayList<EventsPromotionsResponse> list = (ArrayList<EventsPromotionsResponse>) result;
            view.onGetSavedPromotionsSucess(list);

        }else{

            ArrayList<EventsPromotionsResponse> list = (ArrayList<EventsPromotionsResponse>) result;
            view.onGetAttendingEventsSucess(list);
        }



    }
}
