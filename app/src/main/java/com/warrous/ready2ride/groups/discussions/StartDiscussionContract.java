package com.warrous.ready2ride.groups.discussions;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.groups.discussions.model.StartDiscussionRequest;

import java.util.ArrayList;

public interface StartDiscussionContract {

    interface View extends BaseContract.View {


        void onStartDiscussionSucess(ArrayList<SignUpResponse> signUpResponses);
        void onErrorStartDiscussion(String message);
        void onSTartbyAdmin(String message);





    }

    interface Presenter extends BaseContract.Presenter {


        void createGroup(StartDiscussionRequest startDiscussionRequest);




    }

}
