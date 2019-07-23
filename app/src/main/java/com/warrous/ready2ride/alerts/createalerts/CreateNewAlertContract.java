package com.warrous.ready2ride.alerts.createalerts;

import com.warrous.ready2ride.alerts.createalerts.model.CreateAlertRequest;
import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.signup.SignUpRequest;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface CreateNewAlertContract {

    interface View extends BaseContract.View {


        void onServiceTypeResponseSucess(ArrayList<ServiceTypeResponse> segmentResponses);
        void onCreateALertSucess(ArrayList<SignUpResponse> signUpResponses);





    }

    interface Presenter extends BaseContract.Presenter {


        void getServiceTypes(int serviceCatId);
        void createAlert(CreateAlertRequest createAlertRequest);




    }
}
