package com.warrous.ready2ride.auth.profile;

import com.warrous.ready2ride.auth.profile.model.ProfileRequest;
import com.warrous.ready2ride.auth.profile.model.ProfileResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface ProfileContarct {

    interface View extends BaseContract.View {
        void onGetProfileSucess(ArrayList<ProfileResponse> profileResponse);
        void onGetProfileCreateSucess(ArrayList<SignUpResponse> signUpResponses);


    }

    interface Presenter extends BaseContract.Presenter {

        void getProfileDetails(int ownerId);
        void saveprofile(ProfileRequest profileRequest);

    }
}
