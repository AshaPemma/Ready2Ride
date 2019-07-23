package com.warrous.ready2ride.dealership;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.data.remote.ApiInterface;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.model.SavePromotionRequest;
import com.warrous.ready2ride.network.RestClient;

import java.util.ArrayList;

public class DealershipPresenter extends BasePresenter implements DealerShipContract.Presenter {

    DealerShipContract.View view;

    public DealershipPresenter(DealerShipContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getDealerShips(int DealerId, double latitude, double longitude,int ownerid) {
        execute(getApiInterface().getLibraryImages(DealerId,latitude,longitude,ownerid));

    }

    @Override
    public void getEventList(int dealerId) {
        execute(getApiInterface().getEventList(dealerId));
    }

    @Override
    public void getSelectedDealers(int ownerId) {
        execute(getApiInterface().getselectedDealerships(ownerId));
    }

    @Override
    public void savePromotion(SavePromotionRequest savePromotionRequest) {
execute(getApiInterface().savePromotion(savePromotionRequest));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/GetDealerDetails")){
            ArrayList<DealershipResponse> list = (ArrayList<DealershipResponse>) result;
            ArrayList<DealershipResponse> dealerList=new ArrayList<>();

//            if(list.size()>0){
//                for(int i=0;i<5;i++){
//                    dealerList.add(list.get(i));
//                }
//            }

            view.onDealershipSucess(list);
        } else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetSelectedDealers")){
            ArrayList<DealershipResponse> list = (ArrayList<DealershipResponse>) result;
            view.ongetSelectedDealerssucess(list);
        }
        else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Notification/PerformNotificationAction")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onSavePromotionSUcess(list);
        }else{
            ArrayList<EventsPromotionsResponse> list = (ArrayList<EventsPromotionsResponse>) result;
           view.onEventsSucess(list);
        }



    }

}
