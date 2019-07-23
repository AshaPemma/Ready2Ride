package com.warrous.ready2ride.bike;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.bike.model.RideLogResponse;

import java.util.ArrayList;

public class DefaultBikeContract {


    public interface View extends BaseContract.View {


       void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses);
       void onGetRideLogResponse(ArrayList<RideList> segmentResponses);





    }

    public  interface Presenter extends BaseContract.Presenter {


       void getCycleDetails(int CycleId ,int DealerId);
       void getRideDetails(int cycleId,int ownerId);




    }
}
