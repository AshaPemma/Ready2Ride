package com.warrous.ready2ride.auth.signup;

import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface SignUpContract {

    interface View extends BaseContract.View {

//        void onMobileError(String error);
//        void onEmailError(String error);
//        void onPasswordError(String error);
//        void onFnameError(String error);
//        void onLnameError(String error);
//        void onAllFieldsError();
//        void onConfirmPasswordError(String error);
//        void onPasswordEqual();
        void onSaveSuccess(String userName,String password);
        void onSucess(int ownerId);
       void userAlreadyExists();
       void onWrongPwd();
        void onFcmUpdateSucess(ArrayList<SignUpResponse> signUpResponses);

    }

    interface Presenter extends BaseContract.Presenter {

        void onSaveClick(SignUpRequest inputs);
        void getOwnerId(int userId);
        void updateFcm(FcmRequest fcmRequest);

    //    void registerDevice(String token);


    }
}
