package com.warrous.ready2ride.tracksearch;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.tracksearch.Model.ParkingResponse;

import java.util.ArrayList;

public interface TrackSEarchContract {

    public interface View extends BaseContract.View {


        void ongetParkingDetailsSucess(ArrayList<ParkingResponse> rideLogDetails);






    }

    public  interface Presenter extends BaseContract.Presenter {


        void getParkingDetails(double latitude,double longitude);





    }
}
