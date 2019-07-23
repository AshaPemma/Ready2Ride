package com.warrous.ready2ride.groups;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;
import com.warrous.ready2ride.groups.model.LeaveGroupRequest;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;

import java.util.ArrayList;

public interface DetailedGroupContract {

    interface View extends BaseContract.View {


      //  void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses);
        void onGetMembersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses);
        void onLeaveGroupSucess(ArrayList<SignUpResponse> signUpResponses);
        void ongetDiscussionsSucess(ArrayList<DiscussionsResponse> discussionsResponseArrayList);




    }

    interface Presenter extends BaseContract.Presenter {


      //  void getFeaturedGroups(int ownerId);
        void getMembers(int dealerId);
        void leaveGroup(LeaveGroupRequest leaveGroupRequest);
        void ongetDiscussions(int groupId);






    }
}
