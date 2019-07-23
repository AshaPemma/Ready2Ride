package com.warrous.ready2ride.invitebuddies;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.dealership.DealerShipContract;
import com.warrous.ready2ride.invitebuddies.model.AppUsers;
import com.warrous.ready2ride.invitebuddies.model.BuddiesKnown;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequests;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;

import java.util.ArrayList;

public class DefaultInvitebuddiesPresenter extends BasePresenter implements DefaultInviteBuddiesContract.Presenter {


    DefaultInviteBuddiesContract.View view;

    public DefaultInvitebuddiesPresenter(DefaultInviteBuddiesContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getFeaturedGroups(int ownerId) {

        execute(getApiInterface().getFeaturedGroups(ownerId));
    }

    @Override
    public void getFeaturedRiders(int dealerId) {
        execute(getApiInterface().getFeaturedRiders(dealerId));
    }

    @Override
    public void inviteBuddies(InviteBuddiesRequest inviteBuddiesRequest) {
    execute(getApiInterface().inviteBuddies(inviteBuddiesRequest));
    }

    @Override
    public void getInviteBuddies(int senderId) {
execute(getApiInterface().getBuddies(senderId));
    }

    @Override
    public void getAppUsers() {
        execute(getApiInterface().getAppUsers());
    }

    @Override
    public void getBuddies(int ownerId) {
        execute(getApiInterface().getBuddiesList(ownerId));
    }

    @Override
    public void sendBuddyRequest(BuddyRequests buddyRequests) {
        execute(getApiInterface().sendBuddyRequest(buddyRequests));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Buddy/GetGroupByOwnerId")){
            ArrayList<FeaturedGroupsResponse> list = (ArrayList<FeaturedGroupsResponse>) result;
            view.onFeaturedGroupSucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetRiders")){

            ArrayList<FeaturedRidersResponse> list = (ArrayList<FeaturedRidersResponse>) result;
            view.onFeaturedRidersSucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Buddy/GetBuddiesByOwnerId")){
            ArrayList<BuddyListResponse> list = (ArrayList<BuddyListResponse>) result;
            view.ongetInvideBudddiesSucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/User/GetAppUsers")){
            ArrayList<AppUsers> list = (ArrayList<AppUsers>) result;
            view.onGetAppUsersSucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Buddy/GetBuddiesYouMayKnow")){
            ArrayList<BuddiesKnown> list = (ArrayList<BuddiesKnown>) result;
            view.onGetBuddiesKnown(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Buddy/SendBuddyRequestToBuddy")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onSendBudyRequest(list);
        }
        else{
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onInviteBuddiesSucess(list);
        }



    }
}
