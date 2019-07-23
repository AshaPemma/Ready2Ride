package com.warrous.ready2ride.dealership;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.model.SavePromotionRequest;

import java.util.ArrayList;

public interface DealerShipContract {

    interface View extends BaseContract.View {


        void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses);
        void onEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses);
        void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponsesss);
        void onSavePromotionSUcess(ArrayList<SignUpResponse> signUpResponses);






    }

    interface Presenter extends BaseContract.Presenter {


        void getDealerShips(int DealerId,double latitude ,double longitude, int ownerId);
        void getEventList(int dealerId);
        void getSelectedDealers(int ownerId);
        void savePromotion(SavePromotionRequest savePromotionRequest);



    }
}
