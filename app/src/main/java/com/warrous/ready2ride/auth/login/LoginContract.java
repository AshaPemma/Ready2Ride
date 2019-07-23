package com.warrous.ready2ride.auth.login;

import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface LoginContract {

    interface View extends BaseContract.View {

        void onSucess(int ownerId);
        void onFcmUpdateSucess(ArrayList<SignUpResponse> signUpResponses);

    }

    interface Presenter extends BaseContract.Presenter {

        void getOwnerId(int userId);
        void updateFcm(FcmRequest fcmRequest);




    }
}
