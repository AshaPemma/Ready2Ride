package com.warrous.ready2ride.ridelog;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.ridelog.ridedetails.RideLogDetail;

import java.util.ArrayList;

public class RideLogListPresenter extends BasePresenter implements RideLogListContarct.Presenter {

    RideLogListContarct.View view;

    public RideLogListPresenter(RideLogListContarct.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getRidePath(int rideId) {
execute(getApiInterface().getRidePath(rideId));
    }

    @Override
    public void getRideLogDetails(int rideId) {
execute(getApiInterface().getRideLogPath(rideId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Ride/GetRideLog")) {
            ArrayList<RideLogDetail> list = (ArrayList<RideLogDetail>) result;
            view.ongetRideLogSucess(list);

        }else{
            ArrayList<RidePath> list = (ArrayList<RidePath>) result;
            view.ongetRidePathSucess(list);
        }

    }
}
