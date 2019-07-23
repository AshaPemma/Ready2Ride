package com.warrous.ready2ride.createbike;


import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.createbike.model.Brand;
import com.warrous.ready2ride.createbike.model.CreateBikeRequest;
import com.warrous.ready2ride.createbike.model.Model;
import com.warrous.ready2ride.createbike.model.Year;

import java.util.ArrayList;


public interface CreateBikeContract {

    interface View extends BaseContract.View {


        void onsucessBrands(ArrayList<Brand> brandsList);
        void onSucessYears(ArrayList<Year> yearsList);
        void onSUceesModels(ArrayList<Model> modelsList);
        void onSucessCreateBike(ArrayList<SignUpResponse> signUpResponses);






    }

    interface Presenter extends BaseContract.Presenter {


          void ongetBrands(int brandId);
          void onGetYears(int brandId);
          void onGetModels(int brandYearId);
          void createBike(CreateBikeRequest createBikeRequest);



    }
}
