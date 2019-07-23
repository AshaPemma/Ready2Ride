package com.warrous.ready2ride.network;



public interface ApiCallBack<T> {

    void onResponse(String method, String message, Object result);
    void onError(APIError error);
}


