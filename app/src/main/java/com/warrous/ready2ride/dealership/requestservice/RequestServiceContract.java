package com.warrous.ready2ride.dealership.requestservice;

import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.dealership.model.RequestServiceRequest;

import java.util.ArrayList;

public interface RequestServiceContract {

    interface View extends BaseContract.View {


        void onServiceTypeResponseSucess(ArrayList<ServiceTypeResponse> segmentResponses);
        void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses);
        void onSaveAppointmentsucess(ArrayList<SignUpResponse> signUpResponses);




    }

    interface Presenter extends BaseContract.Presenter {


        void getServiceTypes(int serviceCatId);

        void getCycleDetails(int CycleId ,int DealerId);
        void saveAppointment(RequestServiceRequest requestServiceRequest);





    }
}
