package com.warrous.ready2ride.garage;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;

import java.util.ArrayList;

public interface GarageContract {

    interface View extends BaseContract.View {


        void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses);





    }

    interface Presenter extends BaseContract.Presenter {


        void getCycleDetails(int CycleId ,int DealerId);



    }
}
