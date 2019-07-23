package com.warrous.ready2ride.auth.termsofservice;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;

public class TermsandServiceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        injectButterKnife(this);
    }
}
