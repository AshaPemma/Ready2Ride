package com.warrous.ready2ride.bike;

import android.widget.ArrayAdapter;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;

import java.util.ArrayList;

public class DefaultBikePresenter extends BasePresenter implements DefaultBikeContract.Presenter {


    DefaultBikeContract.View view;

    public DefaultBikePresenter(DefaultBikeContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getCycleDetails(int CycleId, int DealerId) {
        execute(getApiInterface().getBikeDetails(CycleId,DealerId));
    }

    @Override
    public void getRideDetails(int cycleId, int ownerId) {
        execute(getApiInterface().getRideLog(cycleId,ownerId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Cycle/GetCycleDetails")){
        ArrayList<DefaultBikeDetailsResponse> list = (ArrayList<DefaultBikeDetailsResponse>) result;

        ArrayList<DefaultBikeDetailsResponse> bikeList=new ArrayList<>();
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                if(i<5){
                    bikeList.add(list.get(i));
                }

            }
        }
        view.onCycleDetailsSucess(bikeList);
        }else{
            ArrayList<RideList> list = (ArrayList<RideList>) result;
            view.onGetRideLogResponse(list);
         //  view.onDeleteLibraryImageSucess();
        }



    }
}
