package com.warrous.ready2ride.forgotpassword;

import com.warrous.ready2ride.base.BasePresenter;

import java.util.ArrayList;

public class ForgotPasswordPresenter extends BasePresenter implements ForgotPasswordContract.Presenter  {

    ForgotPasswordContract.View view;


    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
        super(view);
        this.view = view;
    }


    @Override
    public void onForgotPassword(ForgotPasswordModel forgotPasswordModel) {

        execute(getApiInterface().getForgotpwdEmail(forgotPasswordModel));
    }

    @Override
    public void onUpdatePassword(PasswordModel passwordModel) {

        execute(getApiInterface().updatePassword(passwordModel));
    }

    @Override
    public void onResponse(String method,String message, Object result) {
        super.onResponse(method,message,result);
        if(method.equalsIgnoreCase("r2r.ms.auth/r2r.ms.auth.api/api/Mobile/SendForgotPasswordEmail")){
            ArrayList<ForgotpasswordResponse> forgotResponse=(ArrayList<ForgotpasswordResponse>) result;
            view.onForgotPasswordSucess(forgotResponse);
        }else{
            ArrayList<UpdatePasswordResponse> updateResponse=(ArrayList<UpdatePasswordResponse>) result;
            view.onUpdatePasswordSucess(updateResponse);
        }

    }
}
