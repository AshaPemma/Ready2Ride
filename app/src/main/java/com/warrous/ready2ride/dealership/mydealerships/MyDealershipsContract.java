package com.warrous.ready2ride.dealership.mydealerships;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.auth.signup.SignupPresenter;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.setasdealership.SetDefaultDealerRequest;

import java.util.ArrayList;

public interface MyDealershipsContract {

    interface View extends BaseContract.View {


        void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses);
        void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponses);
        void onRemoveasDealerSucess(ArrayList<SignUpResponse> signUpResponses);
        void onSetDealerSucess(ArrayList<SignUpResponse> signUpResponses);
        void setSelectedDealerSucess();





    }

    interface Presenter extends BaseContract.Presenter {


        void getDealerShips(int DealerId,double latitude ,double longitude, int ownerId);
        void getSelectedDealers(int ownerId);
        void removeAsSelectedDealer(int ownerId,int dealerid);
        void setasdefaultDealer(SetDefaultDealerRequest setDefaultDealerRequest);
        void setasSelectedDealer(int ownwerid,int dealerid);



    }
}
