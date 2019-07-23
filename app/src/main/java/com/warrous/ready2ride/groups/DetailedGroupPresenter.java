package com.warrous.ready2ride.groups;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;
import com.warrous.ready2ride.groups.model.LeaveGroupRequest;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;

import java.util.ArrayList;

public class DetailedGroupPresenter extends BasePresenter implements DetailedGroupContract.Presenter {
    DetailedGroupContract.View view;

    public DetailedGroupPresenter(DetailedGroupContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getMembers(int dealerId) {
        execute(getApiInterface().getFeaturedRiders(dealerId));
    }

    @Override
    public void leaveGroup(LeaveGroupRequest leaveGroupRequest) {
        execute(getApiInterface().leaveGroup(leaveGroupRequest));
    }

    @Override
    public void ongetDiscussions(int groupId) {
        execute(getApiInterface().getDiscussions(groupId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetRiders")){

            ArrayList<FeaturedRidersResponse> list = (ArrayList<FeaturedRidersResponse>) result;
            view.onGetMembersSucess(list);
        }else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Buddy/LeaveGroup")){
            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onLeaveGroupSucess(list);
        } else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetGroupDiscussion")){

            ArrayList<DiscussionsResponse> signUpResponse=(ArrayList<DiscussionsResponse>) result;
            view.ongetDiscussionsSucess(signUpResponse);

        }




    }
}
