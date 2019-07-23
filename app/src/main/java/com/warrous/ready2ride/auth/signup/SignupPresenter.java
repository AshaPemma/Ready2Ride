package com.warrous.ready2ride.auth.signup;

import android.util.Log;
import android.widget.Toast;

import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.auth.login.OwnerIdResponse;
import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.network.APIError;

import java.util.ArrayList;

public class SignupPresenter extends BasePresenter implements SignUpContract.Presenter {

    SignUpContract.View view;
    SignUpRequest signUpRequest;

    public SignupPresenter(SignUpContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onSaveClick(SignUpRequest inputs) {
        signUpRequest=new SignUpRequest();
        signUpRequest=inputs;
        execute(getApiInterface().signUp(inputs));

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

//        if(signUpResponse.get(0).isIsuser()){
//            view.userAlreadyExists();
//        }else{
            view.onSucess(signUpResponse.get(0).getOwnerid());
            //}
        }
        else if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Notification/InsertUpdateFCMbyUser")){
            ArrayList<SignUpResponse> signUpResponse=(ArrayList<SignUpResponse>) result;
         //   view.onFcmUpdateSucess(signUpResponse);
        } else{
            ArrayList<SignUpResponse> signUpResponse=(ArrayList<SignUpResponse>) result;

//        if(signUpResponse.get(0).isIsuser()){
//            view.userAlreadyExists();
//        }else{
            view.onSaveSuccess(signUpRequest.getUserName(),signUpRequest.getPassword());
        }

        //}

    }

    @Override
    public void onError(APIError error) {
        super.onError(error);
        String message=error.getMessage();

        if (message.equalsIgnoreCase("Failed")) {
            view.onWrongPwd();

        }else{
            view.userAlreadyExists();
        }

    }
}
