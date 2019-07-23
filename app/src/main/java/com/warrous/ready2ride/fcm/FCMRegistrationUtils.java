package com.warrous.ready2ride.fcm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by chaithanyav on 10/25/2017.
 */

public class FCMRegistrationUtils {
    private Context mContext;
    private static final String TAG = "FCMRegistrationUtils";

    public FCMRegistrationUtils(Context context) {
        mContext = context;
    }

    // To Register for push notification service
    public void setUpFcmNotification() {
        // Check device for Play Services APK. If check succeeds, proceed with
        // GCM registration.
        if (checkPlayServices()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            sendRegistrationToServer(refreshedToken);
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    private void sendRegistrationToServer(String refreshedToken) {

//        DeviceRegister device = new DeviceRegister();
//        device.setDeviceOs("Android " + AndroidUtil.getDeviceOsVersion());
//        device.setRegistrationId(refreshedToken);
//        device.setDeviceName(AndroidUtil.getDeviceName());
//        device.setDeviceId(AndroidUtil.getDeviceId(mContext));
    }
//    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If it doesn't, display a dialog that allows users
     * to download the APK from the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Toast.makeText(mContext, "Please download the Google play store apk", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "This device is not supported for Google Play Services");
            }
            return false;
        }
        return true;
    }

}
