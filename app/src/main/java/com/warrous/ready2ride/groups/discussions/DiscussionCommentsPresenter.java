package com.warrous.ready2ride.groups.discussions;

import android.util.Log;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.groups.discussions.model.DiscussionCommentsResponse;

import java.util.ArrayList;

public class DiscussionCommentsPresenter extends BasePresenter implements DiscussionCommentsContract.Presenter {

    DiscussionCommentsContract.View view;

    public DiscussionCommentsPresenter(DiscussionCommentsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void ongetDiscussions(int discussionId) {

        execute(getApiInterface().getDiscussionComments(discussionId));
    }

    @Override
    public void onResponse(String method,String message, Object result) {
        super.onResponse(method, message, result);
        Log.e("message", message);
        // Toast.makeText(view.getContext(),message,Toast.LENGTH_LONG).show();
        if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/GetDiscussionComments")) {

            ArrayList<DiscussionCommentsResponse> signUpResponse = (ArrayList<DiscussionCommentsResponse>) result;
            view.ongetDiscussionsSucess(signUpResponse);

        }
    }

    }
