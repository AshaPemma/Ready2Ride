package com.warrous.ready2ride.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.login.LoginActivity;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;

import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        injectButterKnife(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.tv_logout)
    public void onClickLogout(){
        PreferenceManager.clearAllPreferences(this);
        ActivityUtils.startActivity(this, LoginActivity.class, null);
        finishAffinity();
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
     onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
