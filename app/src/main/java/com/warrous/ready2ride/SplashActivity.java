package com.warrous.ready2ride;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.navigation.HomeActivity;
import com.warrous.ready2ride.onboardingviewpager.OnBoardingViewPagerActivity;

public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 1000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);




        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if (isUserLogedIn(SplashActivity.this)) {
                    ActivityUtils.startActivity(SplashActivity.this, HomeActivity.class, null);

                }else {
                    ActivityUtils.startActivity(SplashActivity.this, OnBoardingViewPagerActivity.class, null);
                }
                finish();
                // This method will be executed once the timer is over
                // Start your app main activity
            }
        }, SPLASH_TIME_OUT);

    }

    private boolean isUserLogedIn(Context context) {
        boolean userLogedInStatus = true;
        String token = PreferenceManager.getStringValue(context, PreferenceManager.KEY_TOKEN);
        if (token.equalsIgnoreCase("")) {
            userLogedInStatus = false;
        }
        return userLogedInStatus;
    }


//        private boolean isAppFirstLogin(Context context) {
//            boolean appFirstLaunch = PreferenceManager.getBooleanValue(context, PreferenceManager.KEY_FIRST_LOGIN);
//            return appFirstLaunch;
//        }

}
