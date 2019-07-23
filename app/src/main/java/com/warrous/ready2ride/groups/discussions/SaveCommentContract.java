package com.warrous.ready2ride.groups.discussions;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.discussions.model.DiscussionCommentRequest;

import java.util.ArrayList;

public interface SaveCommentContract {

    interface View extends BaseContract.View {


        void onStartDiscussionSucess(ArrayList<SignUpResponse> signUpResponses);
       //void onErrorStartDiscussion(String message);





    }

    interface Presenter extends BaseContract.Presenter {

        void createGroup(DiscussionCommentRequest discussionCommentRequest);
    }

}
