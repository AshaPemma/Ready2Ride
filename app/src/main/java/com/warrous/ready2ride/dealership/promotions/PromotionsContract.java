package com.warrous.ready2ride.dealership.promotions;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;

import java.util.ArrayList;

public interface PromotionsContract {


    interface View extends BaseContract.View {

void onGetPromotionsSucess(ArrayList<EventsPromotionsResponse> promotionsList);
void onGetEventsSucess(ArrayList<EventsPromotionsResponse> eventsList);

    }

    interface Presenter extends BaseContract.Presenter {

        void onGetPromotions(int ownerId,int dealerId);
        void onGetEvents(int ownerId,int dealerId);
    }
}
