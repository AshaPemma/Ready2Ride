package com.warrous.ready2ride.auth.login;

import android.util.Log;
import android.widget.Toast;

import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.framework.AndroidUtil;

import java.util.ArrayList;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getOwnerId(int userId) {

        execute(getApiInterface().getOwnerId(userId));
    }

    @Override
    public void updateFcm(FcmRequest fcmRequest) {
        execute(getApiInterface().updateFcmToken(fcmRequest));
}

    @Override
    public void onResponse(String method,String message, Object result) {
        super.onResponse(method,message,result);
        Log.e("message",message);
       // Toast.makeText(view.getContext(),message,Toast.LENGTH_LONG).show();
        if(method.equalsIgnoreCase("r2r.ms.auth/r2r.ms.auth.api/api/User/GetOwnerId")){
            ArrayList<OwnerIdResponse> signUpResponse=(ArrayList<OwnerIdResponse>) result;
            if(!signUpResponse.isEmpty()){
                view.onSucess(signUpResponse.get(0).getOwnerid());
            }else{
                AndroidUtil.showSnackBarSafe(view.getAppActivity(),"No OwnerId Linked");
            }

        }else{
            ArrayList<SignUpResponse> signUpResponse=(ArrayList<SignUpResponse>) result;
            //view.onFcmUpdateSucess(signUpResponse);
        }


//        if(signUpResponse.get(0).isIsuser()){
//            view.userAlreadyExists();
//        }else{




        //}

    }
}
