package com.warrous.ready2ride.createbike;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.createbike.model.Brand;
import com.warrous.ready2ride.createbike.model.CreateBikeRequest;
import com.warrous.ready2ride.createbike.model.Model;
import com.warrous.ready2ride.createbike.model.Year;

import java.util.ArrayList;

public class CreateBikePresenter extends BasePresenter implements CreateBikeContract.Presenter {

 CreateBikeContract.View view;

public CreateBikePresenter(CreateBikeContract.View view) {
        super(view);
        this.view = view;
        }

    @Override
    public void ongetBrands(int brandId) {
execute(getApiInterface().getBrands(brandId));
    }

    @Override
    public void onGetYears(int brandId) {
        execute(getApiInterface().getYears(brandId));
    }

    @Override
    public void onGetModels(int brandYearId) {
    execute(getApiInterface().getModels(brandYearId));

    }

    @Override
    public void createBike(CreateBikeRequest createBikeRequest) {
        execute(getApiInterface().createBike(createBikeRequest));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/GetBrands")){
            ArrayList<Brand> list = (ArrayList<Brand>) result;
            view.onsucessBrands(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/GetYearsData")){
            ArrayList<Year> list = (ArrayList<Year>) result;
            view.onSucessYears(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/GetModelsData")){
            ArrayList<Model> list = (ArrayList<Model>) result;
            view.onSUceesModels(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/SaveCycleDetails")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onSucessCreateBike(list);
        }



    }
}
