package com.warrous.ready2ride.forgotpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class OtpActivity extends BaseActivity implements View.OnKeyListener {

    @BindView(R.id.tv_Resend)
    TextView tvResend;
    @BindView(R.id.et_otp_one)
    EditText etOtpOne;
    @BindView(R.id.et_otp_two)
    EditText etOtpTwo;
    @BindView(R.id.et_otp_three)
    EditText etOtpThree;
    @BindView(R.id.et_otp_four)
    EditText etOtpFour;
    @BindView(R.id.et_otp_five)
    EditText etOtpFive;
    @BindView(R.id.et_otp_six)
    EditText etOtpsix;


    String otp,email;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_otp_screen);
        injectButterKnife(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            otp=extras.getString("Otp");
            email=extras.getString("email");

        }
    }



    @OnTextChanged(value = {R.id.et_otp_one, R.id.et_otp_two, R.id.et_otp_three, R.id.et_otp_four,R.id.et_otp_five,R.id.et_otp_six},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterEmailInput(Editable editable) {
        View view = this.getCurrentFocus();
        if(view == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.et_otp_one:
                if (editable.length() == 1) {
                    etOtpTwo.requestFocus();
                }
                break;
            case R.id.et_otp_two:
                if (editable.length() == 1) {
                    etOtpThree.requestFocus();
                }
                break;
            case R.id.et_otp_three:
                if (editable.length() == 1) {
                    etOtpFour.requestFocus();
                }
                break;
            case R.id.et_otp_four:
                if (editable.length() == 1) {
                    etOtpFive.requestFocus();
                }
                break;
            case R.id.et_otp_five:
                if (editable.length() == 1) {
                    etOtpsix.requestFocus();
                }
                break;
            case R.id.et_otp_six:
                AndroidUtil.hideKeyBoard(this);
                break;

        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
            switch (view.getId()) {
                case R.id.et_otp_two:
                    if (etOtpTwo.length() == 0) {
                        etOtpOne.requestFocus();
                    }
                    break;
                case R.id.et_otp_three:
                    if (etOtpThree.length() == 0) {
                        etOtpTwo.requestFocus();
                    }
                    break;
                case R.id.et_otp_four:
                    if (etOtpFour.length() == 0) {
                        etOtpThree.requestFocus();
                    }
                    break;

                case R.id.et_otp_five:
                    if (etOtpFive.length() == 0) {
                        etOtpFour.requestFocus();
                    }
                    break;
                case R.id.et_otp_six:
                    if (etOtpsix.length() == 0) {
                        etOtpFive.requestFocus();
                    }
                    break;
            }
        }
        return false;
    }



    @OnClick(R.id.btn_verify)
    public void onClickVerify(){
        if(etOtpOne.getText().toString().equalsIgnoreCase("")&&etOtpTwo.getText().toString().equalsIgnoreCase("")&&etOtpThree.getText().toString().equalsIgnoreCase("")
                &&etOtpFour.getText().toString().equalsIgnoreCase("")){
            AndroidUtil.showSnackBar(etOtpFive,"Please enter valid Otp");
            return;
        }
        String userOtp=etOtpOne.getText().toString()+etOtpTwo.getText().toString()+etOtpThree.getText().toString()+
                etOtpFour.getText().toString()+etOtpFive.getText().toString()+etOtpsix.getText().toString();
        if(otp.equalsIgnoreCase(userOtp.trim())){
            Bundle bundle=new Bundle();
            bundle.putString("Otp",userOtp);
            bundle.putString("email",email);
            ActivityUtils.startActivity(this,PasswordResetActivity.class,bundle);
        }else{
            AndroidUtil.showSnackBar(etOtpFive,"Please enter valid Otp");
        }

    }
}
