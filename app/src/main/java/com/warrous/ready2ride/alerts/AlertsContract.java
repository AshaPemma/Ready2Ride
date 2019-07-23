package com.warrous.ready2ride.alerts;

import com.warrous.ready2ride.alerts.model.AlertListResponse;
import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface AlertsContract {

    interface View extends BaseContract.View {

        void onGetAlertsSucess(ArrayList<AlertListResponse> alertListResponses);


    }

    interface Presenter extends BaseContract.Presenter {


        void getAlertsList(int ownerId);


    }
}
