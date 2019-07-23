package com.warrous.ready2ride.tracksearch;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.tracksearch.Model.ParkingResponse;

import java.util.ArrayList;

public class TrackSearchPresenter extends BasePresenter implements TrackSEarchContract.Presenter {

    TrackSEarchContract.View view;

    public TrackSearchPresenter(TrackSEarchContract.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void getParkingDetails(double latitude, double longitude) {
        execute(getApiInterface().getParkingDetails(latitude,longitude));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        //if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Ride/GetRideLog")) {
            ArrayList<ParkingResponse> list = (ArrayList<ParkingResponse>) result;
            view.ongetParkingDetailsSucess(list);



    }
}
