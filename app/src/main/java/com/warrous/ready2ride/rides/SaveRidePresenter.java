package com.warrous.ready2ride.rides;

import com.warrous.ready2ride.base.BasePresenter;

import java.util.ArrayList;

public class SaveRidePresenter extends BasePresenter implements SaveRideContract.Presenter{

    SaveRideContract.View view;

    public SaveRidePresenter(SaveRideContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void saveRideRequest(SaveRideRequest saveRideRequest) {
        execute(getApiInterface().saveRideRequest(saveRideRequest));
        }
    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        // if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/GetDealerDetails")){
        ArrayList<SaveRideresponse> list = (ArrayList<SaveRideresponse>) result;
String rideId = null;

        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                rideId=list.get(0).getId();
                break;
            }
        }
//
//        if(list!=null){
//            if(list.size()>0){
                view.onCycleDetailsSucess(rideId,list);
            }
//        }else{
//            view.onCycleDetailsSucess(rideId,null);
//        }

//        }else{
//         //   view.onDeleteLibraryImageSucess();
//        }



  //  }
}
