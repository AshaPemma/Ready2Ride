package com.warrous.ready2ride.alerts.createalerts;

import com.warrous.ready2ride.alerts.createalerts.model.CreateAlertRequest;
import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.base.BasePresenter;

import java.util.ArrayList;

public class CreateNewAlertPresenter extends BasePresenter implements CreateNewAlertContract.Presenter {


    CreateNewAlertContract.View view;

    public CreateNewAlertPresenter(CreateNewAlertContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getServiceTypes(int serviceCatId) {

        execute(getApiInterface().getServiceTypes(0));
    }

    @Override
    public void createAlert(CreateAlertRequest createAlertRequest) {
        execute(getApiInterface().createAlert(createAlertRequest));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/SaveAlert")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
      view.onCreateALertSucess(list);
        }else{
            ArrayList<ServiceTypeResponse> list = (ArrayList<ServiceTypeResponse>) result;
            view.onServiceTypeResponseSucess(list);
         //   view.onDeleteLibraryImageSucess();
        }



    }

}
