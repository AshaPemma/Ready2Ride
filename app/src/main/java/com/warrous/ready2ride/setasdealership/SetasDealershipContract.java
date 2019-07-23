package com.warrous.ready2ride.setasdealership;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface SetasDealershipContract {

    interface View extends BaseContract.View {


void setDealerSucess(ArrayList<SignUpResponse> signUpResponses);





    }

    interface Presenter extends BaseContract.Presenter {



void setAsDealerShip(SetDefaultDealerRequest setDefaultDealerRequest);



    }
}
