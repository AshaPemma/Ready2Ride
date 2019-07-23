package com.warrous.ready2ride.groups.discussions;

import android.util.Log;

import com.warrous.ready2ride.auth.signup.SignUpRequest;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.groups.discussions.model.StartDiscussionRequest;
import com.warrous.ready2ride.network.APIError;

import java.util.ArrayList;

public class StartDiscussionPresenter extends BasePresenter implements StartDiscussionContract.Presenter {

    StartDiscussionContract.View view;

    public StartDiscussionPresenter(StartDiscussionContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void createGroup(StartDiscussionRequest startDiscussionRequest) {

        execute(getApiInterface().startNewDiscussion(startDiscussionRequest));
    }


    @Override
    public void onResponse(String method,String message, Object result) {
        super.onResponse(method,message,result);
        Log.e("message",message);
        // Toast.makeText(view.getContext(),message,Toast.LENGTH_LONG).show();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/SaveGroupDiscussion")){

            ArrayList<SignUpResponse> signUpResponse=(ArrayList<SignUpResponse>) result;
            if(message.equalsIgnoreCase("Discussion must be created by group admin")){

            }else{
                view.onStartDiscussionSucess(signUpResponse);
            }


        }


    }
    @Override
    public void onError(APIError error) {
        super.onError(error);
        String message=error.getMessage();
        view.onErrorStartDiscussion(message);

    }
}
