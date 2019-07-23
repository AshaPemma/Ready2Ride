package com.warrous.ready2ride.finddealer;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;

import java.util.ArrayList;

public class FindDealerPresenter extends BasePresenter implements FindDealerContract.Presenter {


    FindDealerContract.View view;

    public FindDealerPresenter(FindDealerContract.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/GetDealerDetails")){
            ArrayList<DealershipResponse> list = (ArrayList<DealershipResponse>) result;
            view.onDealershipSucess(list);
        }else{

        }



    }

    @Override
    public void getDealerShips(int DealerId, double latitude, double longitude,int ownerId) {
        execute(getApiInterface().getLibraryImages(DealerId,latitude,longitude,ownerId));

    }
}
