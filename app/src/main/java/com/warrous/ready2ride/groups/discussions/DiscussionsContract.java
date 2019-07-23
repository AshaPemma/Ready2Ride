package com.warrous.ready2ride.groups.discussions;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;

import java.util.ArrayList;

public interface DiscussionsContract {

    interface View extends BaseContract.View {


        void ongetDiscussionsSucess(ArrayList<DiscussionsResponse> discussionsResponseArrayList);
        void onErrorStartDiscussion(String message);





    }

    interface Presenter extends BaseContract.Presenter {


        void ongetDiscussions(int groupId);




    }
}
