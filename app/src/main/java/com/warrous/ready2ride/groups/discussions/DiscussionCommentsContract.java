package com.warrous.ready2ride.groups.discussions;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.discussions.model.DiscussionCommentsResponse;

import java.util.ArrayList;

public interface DiscussionCommentsContract  {

    interface View extends BaseContract.View {


        void ongetDiscussionsSucess(ArrayList<DiscussionCommentsResponse> discussionsResponseArrayList);
       // void onErrorStartDiscussion(String message);





    }

    interface Presenter extends BaseContract.Presenter {


        void ongetDiscussions(int discussionId);




    }
}
