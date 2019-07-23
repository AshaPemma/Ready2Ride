package com.warrous.ready2ride.dealership.sendmessage;

import android.os.Bundle;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;

import butterknife.OnClick;

public class SendMessageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        injectButterKnife(this);}

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
