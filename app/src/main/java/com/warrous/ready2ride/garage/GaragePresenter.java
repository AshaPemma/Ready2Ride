package com.warrous.ready2ride.garage;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;

import java.util.ArrayList;

public class GaragePresenter extends BasePresenter implements GarageContract.Presenter  {

    GarageContract.View view;

    public GaragePresenter(GarageContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getCycleDetails(int CycleId, int DealerId) {
        execute(getApiInterface().getBikeDetails(CycleId,DealerId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        // if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/GetDealerDetails")){
        ArrayList<DefaultBikeDetailsResponse> list = (ArrayList<DefaultBikeDetailsResponse>) result;
        view.onCycleDetailsSucess(list);
//        }else{
//         //   view.onDeleteLibraryImageSucess();
//        }



    }
}
