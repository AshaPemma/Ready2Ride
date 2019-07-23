package com.warrous.ready2ride.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.warrous.ready2ride.Ready2RideApp;
import com.warrous.ready2ride.data.remote.ApiInterface;
import com.warrous.ready2ride.network.RestClient;


public interface BaseContract {

    interface View {
        Context getContext();

        Activity getAppActivity();

        Ready2RideApp getReady2RideApp();

        void showLoader();

        void dismissLoader();

        void showError(String message);
    }

    interface Presenter {
        Resources getResource();

        String getString(int resId);

        // Life cycle methods.
        void onStart();

        void onResume();

        void onPause();

        void onDestroy();

        RestClient getRestClient();

        ApiInterface getApiInterface();
    }
}
