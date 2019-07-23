package com.warrous.ready2ride.alerts;

import com.warrous.ready2ride.alerts.model.AlertListResponse;
import com.warrous.ready2ride.base.BasePresenter;

import java.util.ArrayList;

public class ALertsPresenter extends BasePresenter implements AlertsContract.Presenter{

    AlertsContract.View view;

    public ALertsPresenter(AlertsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getAlertsList(int ownerId) {
        execute(getApiInterface().getAlertList(ownerId));
    }
    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        // if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/GetDealerDetails")){
        ArrayList<AlertListResponse> list = (ArrayList<AlertListResponse>) result;
        view.onGetAlertsSucess(list);
//        }else{
//         //   view.onDeleteLibraryImageSucess();
//        }



    }
}
