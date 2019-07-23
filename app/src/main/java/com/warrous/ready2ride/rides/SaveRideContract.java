package com.warrous.ready2ride.rides;

import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface SaveRideContract {

    public interface View extends BaseContract.View {


        void onCycleDetailsSucess(String rideId,ArrayList<SaveRideresponse> pathLists);





    }

    public  interface Presenter extends BaseContract.Presenter {


      //  void getCycleDetails(int CycleId ,int DealerId);
        void saveRideRequest(SaveRideRequest saveRideRequest);




    }
}
