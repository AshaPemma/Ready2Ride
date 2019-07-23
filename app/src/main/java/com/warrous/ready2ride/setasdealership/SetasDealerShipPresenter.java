package com.warrous.ready2ride.setasdealership;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;

import java.util.ArrayList;

public class SetasDealerShipPresenter extends BasePresenter implements SetasDealershipContract.Presenter{

    SetasDealershipContract.View view;

    public SetasDealerShipPresenter(SetasDealershipContract.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void setAsDealerShip(SetDefaultDealerRequest setDefaultDealerRequest) {

        execute(getApiInterface().setasDefaultDealer(setDefaultDealerRequest));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();

            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.setDealerSucess(list);





    }
}
