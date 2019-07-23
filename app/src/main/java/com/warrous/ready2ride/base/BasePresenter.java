package com.warrous.ready2ride.base;

import android.content.res.Resources;

import com.warrous.ready2ride.data.remote.ApiInterface;
import com.warrous.ready2ride.network.APIError;
import com.warrous.ready2ride.network.ApiCallBack;
import com.warrous.ready2ride.network.RestClient;

import retrofit2.Call;


public abstract class BasePresenter implements BaseContract.Presenter, ApiCallBack {
    protected BaseContract.View genericView;
    //protected LMLog lmLog = LMLog.getInstance();


    public BasePresenter(BaseContract.View genericView) {
        this.genericView = genericView;
    }

    @Override
    public Resources getResource() {
        return genericView.getContext().getResources();
    }

    @Override
    public String getString(int resId) {
        return genericView.getContext().getString(resId);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RestClient getRestClient() {
        return genericView.getReady2RideApp().getRestClient();
    }

    @Override
    public ApiInterface getApiInterface() {
        return genericView.getReady2RideApp().getApiInterface();
    }

    public void execute(Call<?> request) {
        genericView.showLoader();
        getRestClient().execute(request, this);
    }

    public void executeInBackground(Call<?> request) {
        getRestClient().execute(request, this);
    }

    @Override
    public void onResponse(String method,String message, Object result) {
        genericView.dismissLoader();
        //lmLog.info("method " + method);
        if(result!=null) {
            //lmLog.info(result.toString());
        }

    }

    @Override
    public void onError(APIError error) {
        // lmLog.info(error.toString());

        genericView.dismissLoader();
        genericView.showError(error.getMessage());
//        if (error.getCode() == ApiErrorCode.NO_NETWORK.getCode()) {
//
//        } else if (error.getCode() == ApiErrorCode.SERVER_ERROR_RESPONSE.getCode()) {
//            genericView.showError(error.getMessage());
//        } else if (error.getCode() == ApiErrorCode.SERVER_ERROR.getCode()) {
//            genericView.showError(error.getMessage());
//        }
//        else if (error.getCode() == ApiErrorCode.SERVER_ERROR_UPDATE.getCode()) {
//            genericView.showError(error.getMessage());
//        }


    }
}
