package com.warrous.ready2ride.groups;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.model.CreateGroupRequest;

import java.util.ArrayList;

public interface CreateGroupContract {


    interface View extends BaseContract.View {


        //  void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses);
        //void onGetMembersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses);
        void onCreateGroupSucess(ArrayList<SignUpResponse> signUpResponses);





    }

    interface Presenter extends BaseContract.Presenter {


        //  void getFeaturedGroups(int ownerId);
        void createGroup(CreateGroupRequest createGroupRequest);




    }
}
