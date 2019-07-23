package com.warrous.ready2ride.ridelog;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.ridelog.ridedetails.RideLogDetail;

import java.util.ArrayList;

public interface RideLogListContarct {

    public interface View extends BaseContract.View {


      void ongetRidePathSucess(ArrayList<RidePath> ridePaths);
        void ongetRideLogSucess(ArrayList<RideLogDetail> rideLogDetails);






    }

    public  interface Presenter extends BaseContract.Presenter {


       void getRidePath(int rideId);
       void getRideLogDetails(int rideId);





    }
}
