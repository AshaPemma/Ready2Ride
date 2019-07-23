package com.warrous.ready2ride.forgotpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;


import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.login.LoginActivity;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordResetActivity extends BaseActivity implements ForgotPasswordContract.View{

    String otp,email;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.et_password)
    EditText etPassword;

    PasswordModel passwordModel;
    ForgotPasswordContract.Presenter presenter;




    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        injectButterKnife(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            otp=extras.getString("Otp");
            email=extras.getString("email");
        }
        presenter=new ForgotPasswordPresenter(this);
        passwordModel=new PasswordModel();
    }



    @OnClick(R.id.btn_reset)
    public void onClickReset(){
        if(etPassword.getText().toString().equalsIgnoreCase("")){
            AndroidUtil.showSnackBarSafe(etPassword,"please enter password");
            return;
        }
        if(etConfirmPassword.getText().toString().equalsIgnoreCase("")){
            AndroidUtil.showSnackBarSafe(etPassword,"please enter confirm password");
            return;
        }
        if (!etConfirmPassword.getText().toString().equalsIgnoreCase(etPassword.getText().toString())) {
            AndroidUtil.showSnackBarSafe(etPassword,"Both Passwords should be equal");
            return;
        }
        passwordModel.setEmailAddress(email);
        passwordModel.setOtp(otp);
        passwordModel.setPassword(etPassword.getText().toString());
        presenter.onUpdatePassword(passwordModel);

        }

    @Override
    public void onForgotPasswordSucess(ArrayList<ForgotpasswordResponse> forgotpasswordResponses) {

    }

    @Override
    public void onUpdatePasswordSucess(ArrayList<UpdatePasswordResponse> updatePasswordResponses) {
        ActivityUtils.startActivity(this,LoginActivity.class,null);
    }
}
