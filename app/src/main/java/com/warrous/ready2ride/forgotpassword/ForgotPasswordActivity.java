package com.warrous.ready2ride.forgotpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View {

    @BindView(R.id.et_email)
    EditText etEmail;
    String otp;
    ForgotPasswordModel forgotPasswordModel;
    ForgotPasswordContract.Presenter presenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        injectButterKnife(this);
        forgotPasswordModel=new ForgotPasswordModel();
        presenter=new ForgotPasswordPresenter(this);
    }
    @OnClick(R.id.btn_request)
    public void onClickSubmit(){
        if(etEmail.getText().toString().equalsIgnoreCase("")){
            AndroidUtil.showSnackBarSafe(this,"Please enter valid email");
            return;
        }
        forgotPasswordModel.setEmail(etEmail.getText().toString());

        presenter.onForgotPassword(forgotPasswordModel);

    }

    @Override
    public void onForgotPasswordSucess(ArrayList<ForgotpasswordResponse> forgotpasswordResponses) {
        if(forgotpasswordResponses!=null){
            otp=forgotpasswordResponses.get(0).getOtp();
            Bundle bundle=new Bundle();
            bundle.putString("Otp",otp);
            bundle.putString("email",etEmail.getText().toString());
            ActivityUtils.startActivity(this,OtpActivity.class,bundle);
        }
    }

    @Override
    public void onUpdatePasswordSucess(ArrayList<UpdatePasswordResponse> updatePasswordResponses) {

    }


}
