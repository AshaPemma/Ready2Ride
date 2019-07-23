package com.warrous.ready2ride.auth.profile;

import android.util.Log;

import com.warrous.ready2ride.auth.profile.model.ProfileRequest;
import com.warrous.ready2ride.auth.profile.model.ProfileResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;

import java.util.ArrayList;

public class ProfilePresenter extends BasePresenter implements ProfileContarct.Presenter {

    ProfileContarct.View view;

    public ProfilePresenter(ProfileContarct.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void getProfileDetails(int ownerId) {
        execute(getApiInterface().getProfileDetails(ownerId));
        }

    @Override
    public void saveprofile(ProfileRequest profileRequest) {
execute(getApiInterface().createUserProfile(profileRequest));
    }

    @Override
    public void onResponse(String method,String message, Object result) {
        super.onResponse(method,message,result);
        Log.e("message",message);
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/OwnerProfileDetails")){
            ArrayList<ProfileResponse> profileResponseArrayList=(ArrayList<ProfileResponse>) result;
            view.onGetProfileSucess(profileResponseArrayList);
        }else if (method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Owner/UpdateOwnerData")){
            ArrayList<SignUpResponse> signUpResponses=(ArrayList<SignUpResponse>) result;
            view.onGetProfileCreateSucess(signUpResponses);
            }



    }
}
