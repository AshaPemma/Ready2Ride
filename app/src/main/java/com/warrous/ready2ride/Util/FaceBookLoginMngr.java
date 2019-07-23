package com.warrous.ready2ride.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.Ready2RideLog;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by asha on 19/1/18.
 */


public class FaceBookLoginMngr {

    Ready2RideLog lmLog;
    CallbackManager callbackManager;
    private Activity activity;

    // Constructor
    public FaceBookLoginMngr(Activity activity) {
        lmLog = Ready2RideLog.getInstance();
        this.activity = activity;
        genarateHashKey();
    }

    public static void logout() {
        LoginManager.getInstance().logOut();
    }

    public void genarateHashKey() {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "com.youavail.avail",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void fbLogin() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList(
                "public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        String accessToken = loginResult.getAccessToken().getToken();

                        // save accessToken to SharedPreference
                        saveAccessToken(accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject jsonObject,
                                                            GraphResponse response) {

                                        // Getting FB User Data
                                        Bundle facebookData = getFacebookData(jsonObject);
                                        //ActivityUtils.startActivity(activity, DashBoardActivity.class, null);
                                      //  ActivityUtils.startActivity(activity, BlankActivity.class, null);

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel() {
                        lmLog.info("Login attempt cancelled.");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        error.printStackTrace();
                        lmLog.info("Login attempt failed.");
                        deleteAccessToken();
                    }


                }
        );

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void saveAccessToken(String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fb_access_token", token);
        editor.apply(); // This line is IMPORTANT !!!
    }


    public String getToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getString("fb_access_token", null);
    }

    public void clearToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply(); // This line is IMPORTANT !!!
    }

    public void saveFacebookUserInfo(String first_name, String last_name, String email, String gender, String profileURL) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fb_first_name", first_name);
        editor.putString("fb_last_name", last_name);
        editor.putString("fb_email", email);
        editor.putString("fb_gender", gender);
        editor.putString("fb_profileURL", profileURL);
        editor.apply(); // This line is IMPORTANT !!!
        lmLog.info("MyApp", "Shared Name : " + first_name + "\nLast Name : " + last_name + "\nEmail : " + email + "\nGender : " + gender + "\nProfile Pic : " + profileURL);
    }

    public void getFacebookUserInfo() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        //lmLog.d("MyApp", "Name : "+prefs.getString("fb_name",null)+"\nEmail : "+prefs.getString("fb_email",null));
        lmLog.info("MyApp", "Name : " + prefs.getString("fb_name", null) + "\nEmail : " + prefs.getString("fb_email", null));
    }

    public void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };

    }

    public Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                lmLog.info("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


            saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"), object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
            // lmLog.info("BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }


}