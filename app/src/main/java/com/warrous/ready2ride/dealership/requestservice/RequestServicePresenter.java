package com.warrous.ready2ride.dealership.requestservice;

import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.dealership.model.RequestServiceRequest;

import java.util.ArrayList;

public class RequestServicePresenter extends BasePresenter implements RequestServiceContract.Presenter {


    RequestServiceContract.View view;

    public RequestServicePresenter(RequestServiceContract.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void getServiceTypes(int serviceCatId) {
        execute(getApiInterface().getServiceTypes(0));
    }

    @Override
    public void getCycleDetails(int CycleId, int DealerId) {
        execute(getApiInterface().getBikeDetails(CycleId,DealerId));
    }

    @Override
    public void saveAppointment(RequestServiceRequest requestServiceRequest) {
        execute(getApiInterface().createAppointment(requestServiceRequest));
    }


    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/GetServiceCategories")) {
            ArrayList<ServiceTypeResponse> list = (ArrayList<ServiceTypeResponse>) result;
            view.onServiceTypeResponseSucess(list);
        } else  if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/GetCycleDetails")) {
            ArrayList<DefaultBikeDetailsResponse> list = (ArrayList<DefaultBikeDetailsResponse>) result;
            view.onCycleDetailsSucess(list);
            }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/BookAppoinment")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onSaveAppointmentsucess(list);

        }
    }
}
