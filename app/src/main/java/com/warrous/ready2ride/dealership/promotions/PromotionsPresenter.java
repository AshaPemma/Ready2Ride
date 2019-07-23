package com.warrous.ready2ride.dealership.promotions;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;

import java.util.ArrayList;

public class PromotionsPresenter extends BasePresenter implements PromotionsContract.Presenter{

    PromotionsContract.View view;

    public PromotionsPresenter(PromotionsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onGetPromotions(int ownerId, int dealerId) {
        execute(getApiInterface().getOwnerPromotions(ownerId,dealerId));
    }

    @Override
    public void onGetEvents(int ownerId, int dealerId) {
      execute(getApiInterface().getOwnerEvents(ownerId,dealerId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Events/GetOwnerPromotions")) {
            ArrayList<EventsPromotionsResponse> list = (ArrayList<EventsPromotionsResponse>) result;
            view.onGetPromotionsSucess(list);
        } else {
            ArrayList<EventsPromotionsResponse> list = (ArrayList<EventsPromotionsResponse>) result;
            view.onGetEventsSucess(list);
        }
    }

}
