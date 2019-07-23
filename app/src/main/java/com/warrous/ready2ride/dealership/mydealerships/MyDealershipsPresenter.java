package com.warrous.ready2ride.dealership.mydealerships;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.popup.DataManager;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.setasdealership.SetDefaultDealerRequest;

import java.util.ArrayList;

public class MyDealershipsPresenter extends BasePresenter implements MyDealershipsContract.Presenter {

    MyDealershipsContract.View view;

    public MyDealershipsPresenter(MyDealershipsContract.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void getDealerShips(int DealerId, double latitude, double longitude, int ownerId) {
        execute(getApiInterface().getLibraryImages(DealerId,latitude,longitude,ownerId));

    }

    @Override
    public void getSelectedDealers(int ownerId) {

        execute(getApiInterface().getselectedDealerships(ownerId));

    }

    @Override
    public void removeAsSelectedDealer(int ownerId, int dealerid) {
        execute(getApiInterface().removeAsSelectedDealer(ownerId,dealerid));
    }

    @Override
    public void setasdefaultDealer(SetDefaultDealerRequest setDefaultDealerRequest) {
        execute(getApiInterface().setasDefaultDealer(setDefaultDealerRequest));
    }

    @Override
    public void setasSelectedDealer(int ownwerid, int dealerid) {
        execute(getApiInterface().setAsSelectedDealer(ownwerid,dealerid));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetSelectedDealers")){
            ArrayList<DealershipResponse> list = (ArrayList<DealershipResponse>) result;
            view.ongetSelectedDealerssucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/GetDealerDetails")){
            ArrayList<DealershipResponse> list = (ArrayList<DealershipResponse>) result;
            ArrayList<DealershipResponse> newList=new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(!list.get(i).isSelected()){
                    newList.add(list.get(i));
                }
            }
            DataManager.getInstance().setDealerLists(list);
            view.onDealershipSucess(newList);

        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/DeleteSelectedDealer")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onRemoveasDealerSucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Dealer/SetDefaultDealer")){
            ArrayList<SignUpResponse> list=(ArrayList<SignUpResponse>) result;
            view.onSetDealerSucess(list);
        }
        else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/SaveSelectedDealers")){

            if (message.equalsIgnoreCase("Limit Exceeded")) {
                AndroidUtil.showSnackBarSafe(view.getAppActivity(),"You have exceded maximum limit.Please remove atleast one from selected dealers");
            }else{
                ArrayList<SignUpResponse> list=(ArrayList<SignUpResponse>) result;
                view.setSelectedDealerSucess();
            }

        }



    }


}
