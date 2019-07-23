package com.warrous.ready2ride.invitebuddies;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.invitebuddies.model.AppUsers;
import com.warrous.ready2ride.invitebuddies.model.BuddiesKnown;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequest;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequests;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesResponse;

import java.util.ArrayList;

public interface DefaultInviteBuddiesContract {

    interface View extends BaseContract.View {


       void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses);
       void onFeaturedRidersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses);
       void onInviteBuddiesSucess(ArrayList<SignUpResponse> signUpResponses);
       void ongetInvideBudddiesSucess(ArrayList<BuddyListResponse> inviteBuddiesResponses);
       void onGetAppUsersSucess(ArrayList<AppUsers> appUsersList);
       void onGetBuddiesKnown(ArrayList<BuddiesKnown> buddiesKnownsList);
       void onSendBudyRequest(ArrayList<SignUpResponse> signUpResponses);






    }

    interface Presenter extends BaseContract.Presenter {


        void getFeaturedGroups(int ownerId);
        void getFeaturedRiders(int dealerId);
        void inviteBuddies(InviteBuddiesRequest inviteBuddiesRequest);
        void getInviteBuddies(int senderId);
        void getAppUsers();
        void getBuddies(int ownerId);
        void sendBuddyRequest(BuddyRequests buddyRequests);




    }
}
