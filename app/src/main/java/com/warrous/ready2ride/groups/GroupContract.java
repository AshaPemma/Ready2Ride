package com.warrous.ready2ride.groups;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.model.GroupsListResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import java.util.ArrayList;

public interface GroupContract {

    interface View extends BaseContract.View {


        void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses);
        void onGetGroupSucess(ArrayList<FeaturedGroupsResponse> groupLists);






    }

    interface Presenter extends BaseContract.Presenter {


        void getFeaturedGroups(int ownerId,boolean group);
        void getGroups(int ownerId,boolean group);




    }
}
