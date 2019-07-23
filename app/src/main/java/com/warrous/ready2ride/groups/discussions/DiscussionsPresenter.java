package com.warrous.ready2ride.groups.discussions;

import android.util.Log;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;

import java.util.ArrayList;

public class DiscussionsPresenter extends BasePresenter implements DiscussionsContract.Presenter {


    DiscussionsContract.View view;

    public DiscussionsPresenter(DiscussionsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void ongetDiscussions(int groupId) {
        execute(getApiInterface().getDiscussions(groupId));
    }

    @Override
    public void onResponse(String method,String message, Object result) {
        super.onResponse(method,message,result);
        Log.e("message",message);
        // Toast.makeText(view.getContext(),message,Toast.LENGTH_LONG).show();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetGroupDiscussion")){

            ArrayList<DiscussionsResponse> signUpResponse=(ArrayList<DiscussionsResponse>) result;
            view.ongetDiscussionsSucess(signUpResponse);

        }


    }
}
