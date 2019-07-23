package com.warrous.ready2ride.forgotpassword;

import com.warrous.ready2ride.base.BaseContract;

import java.util.ArrayList;

public interface ForgotPasswordContract {

    interface View extends BaseContract.View {

        void onForgotPasswordSucess(ArrayList<ForgotpasswordResponse> forgotpasswordResponses);

        void onUpdatePasswordSucess(ArrayList<UpdatePasswordResponse> updatePasswordResponses);


    }

    interface Presenter extends BaseContract.Presenter {


        void onForgotPassword(ForgotPasswordModel forgotPasswordModel);

        void onUpdatePassword(PasswordModel passwordModel);


    }
}
