package com.warrous.ready2ride.network;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CallbackHandler<T> implements Callback<T> {

    Retrofit mRetrofit;
    ApiCallBack<T> apiCallBack;
    ApiResponseHandler responseHandler;

    public CallbackHandler(Retrofit retrofit, ApiCallBack<T> apiCallBack) {
        mRetrofit = retrofit;
        this.apiCallBack = apiCallBack;
        responseHandler = new ApiResponseHandler(apiCallBack);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (call.isCanceled()) {
            return;
        }
        if (response.isSuccessful()) {
            responseHandler.onResponse(call, response);
        } else {
            responseHandler.onError(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (call.isCanceled()) {
            return;
        }
        responseHandler.onFails(call, t);
       //ContactPointLog.getInstance().info("onFailure Api Call : " + t.getMessage());

    }

}
