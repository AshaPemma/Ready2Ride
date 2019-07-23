package com.warrous.ready2ride.finddealer;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.dealership.model.DealershipResponse;

import java.util.ArrayList;

public interface FindDealerContract {

    interface View extends BaseContract.View {


        void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses);





    }

    interface Presenter extends BaseContract.Presenter {


        void getDealerShips(int DealerId,double latitude ,double longitude,int ownerId);



    }
}
