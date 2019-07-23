package com.warrous.ready2ride.wallet;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;

import java.util.ArrayList;

public interface WalletContarct {

    interface View extends BaseContract.View {


void onGetSavedPromotionsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses);
void onGetAttendingEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses);





    }

    interface Presenter extends BaseContract.Presenter {

        void onGetSavedPromttions(int ownerId);
        void onGetAttendingEvents(int ownerId);

    }
}
