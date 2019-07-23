package com.warrous.ready2ride.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.warrous.ready2ride.framework.PreferenceManager;

/**
 * Created by asha on 8/2/18.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService  {



    //this method will be called
    //when the token is generated
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //now we will have the token
        String token = FirebaseInstanceId.getInstance().getToken();

        PreferenceManager.storeValue(this,PreferenceManager.KEY_FCM_TOKEN,token);
        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared
        Log.d(MyFirebaseMessagingService.TAG, token);

    }
}
