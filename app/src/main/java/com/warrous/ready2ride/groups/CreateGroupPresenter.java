package com.warrous.ready2ride.groups;

import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.groups.model.CreateGroupRequest;

import java.util.ArrayList;

public class CreateGroupPresenter extends BasePresenter implements CreateGroupContract.Presenter {

    CreateGroupContract.View view;

    public CreateGroupPresenter(CreateGroupContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void createGroup(CreateGroupRequest createGroupRequest) {
        execute(getApiInterface().createGroup(createGroupRequest));

    }
    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Buddy/SaveGroup")){

            ArrayList<SignUpResponse> list = (ArrayList<SignUpResponse>) result;
            view.onCreateGroupSucess(list);
        }



    }
}
