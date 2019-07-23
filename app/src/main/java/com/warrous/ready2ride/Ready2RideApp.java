package com.warrous.ready2ride;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.warrous.ready2ride.constants.Constants;
import com.warrous.ready2ride.data.remote.ApiInterface;
import com.warrous.ready2ride.data.volley.volley.LruBitmapCache;
import com.warrous.ready2ride.fcm.MyFirebaseMessagingService;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.framework.Ready2RideLog;
import com.warrous.ready2ride.network.RestClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Ready2RideApp extends Application {

    public static final String TAG = Ready2RideApp.class.getSimpleName();
    private static Ready2RideApp mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public SharedPreferences sharedPreferences;
    RestClient restClient;
    ApiInterface apiInterface;

    boolean hasTakeTour;

    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
        mInstance = this;
        Fresco.initialize(this);
     //    FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
       // FirebaseApp.initializeApp(this);
        // Add code to print out the key hash

//        Places.initialize(getApplicationContext(), "AIzaSyCAEYMybNVFZGXUUfkWZXkD8s5Cu2rRTds");
//
//// Create a new Places client instance.
//        PlacesClient placesClient = Places.createClient(this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.warrous.ready2ride",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        //  String uniqueID = FirebaseInstanceId.getInstance().getId();

        // ContactPointLog.getInstance().info("IDD",uniqueID);

//        FirebaseMessaging.getInstance().subscribeToTopic("testing")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_subscribe_failed);
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(ContactPointApplication.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

//        String fcmToken = FirebaseInstanceId.getInstance().getToken();
//        LMLog.getInstance().error(MyFirebaseMessagingService.TAG,fcmToken);
        initializeRetrofit();
        //getNameEmailDetails();
//        ArrayList<String> namesList=new ArrayList<>();
//        namesList=getNameEmailDetails();
//        ContactPointLog.getInstance().info("NamesList",""+namesList);


        FirebaseApp.initializeApp(this);
        //  String uniqueID = FirebaseInstanceId.getInstance().getId();

        // ContactPointLog.getInstance().info("IDD",uniqueID);

       // String fcmToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        PreferenceManager.storeValue(getApplicationContext(),PreferenceManager.KEY_FCM_TOKEN,token);

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                       // Toast.makeText(Ready2RideApp.this, token, Toast.LENGTH_SHORT).show();
                        PreferenceManager.storeValue(getApplicationContext(),PreferenceManager.KEY_FCM_TOKEN,token);
                    }
                });

      //  Ready2RideLog.getInstance().error(MyFirebaseMessagingService.TAG,fcmToken);
        FirebaseMessaging.getInstance().subscribeToTopic("testing")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(Ready2RideApp.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void initializeRetrofit() {
        // Aws
        //RestClient.Builder builder = new RestClient.Builder(this, "http://ec2-13-127-142-169.ap-south-1.compute.amazonaws.com:3100/");
        RestClient.Builder builder = new RestClient.Builder(this, "https://portal.ready2ridemobile.com:19081");

//        RestClient.Builder builder = new RestClient.Builder(this, "http://192.168.0.150:3100/");
//        RestClient.Builder builder = new RestClient.Builder(this, "http://cardstel.co.in:3100/");
        apiInterface = builder.create(ApiInterface.class);
        restClient = builder.build();
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public void setHasTakeTour(boolean hasTakeTour) {
        this.hasTakeTour = hasTakeTour;
    }
    public static synchronized Ready2RideApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        try {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }
        } catch (Exception e) {

        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        StringRequest request;
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
