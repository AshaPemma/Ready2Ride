package com.warrous.ready2ride.groups.discussions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.groups.DetailedGroupActivity;
import com.warrous.ready2ride.groups.discussions.model.StartDiscussionRequest;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class StartNewDiscussionE extends BaseActivity implements StartDiscussionContract.View {

    @BindView(R.id.et_email)
    EditText etDiscussionTitle;
    @BindView(R.id.et_message)
    EditText etMessage;
    StartDiscussionRequest startDiscussionRequest;
    int groupId;
    String grpName;
    StartDiscussionContract.Presenter mPresenter;

    FeaturedGroupsResponse featuredGroupsResponse;

    @BindView(R.id.tv_to)
    TextView tvTo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_conversation);
        injectButterKnife(this);
        mPresenter=new StartDiscussionPresenter(this);
        featuredGroupsResponse=(FeaturedGroupsResponse) getIntent().getSerializableExtra("featuredGroup");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            groupId = extras.getInt("GroupId");
            grpName=extras.getString("groupName");
        }

        tvTo.setText("To : "+grpName);
    }

    @OnClick(R.id.btn_send)
    public void onClickSend(){

        startDiscussionRequest= new StartDiscussionRequest();
        if(TextUtils.isEmpty(etDiscussionTitle.getText().toString())){
            AndroidUtil.showSnackBarSafe(etDiscussionTitle,"Please enter Discussion Title");
            return;
        }
        if(TextUtils.isEmpty(etMessage.getText().toString())){
            AndroidUtil.showSnackBarSafe(etDiscussionTitle,"Please enter Message");
            return;
        }

        startDiscussionRequest.setDiscussionTittle(etDiscussionTitle.getText().toString());
        startDiscussionRequest.setDiscussionDescription(etMessage.getText().toString());
        startDiscussionRequest.setDiscussionId(0);
        startDiscussionRequest.setGroupId(featuredGroupsResponse.getGroupId());
        startDiscussionRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        mPresenter.createGroup(startDiscussionRequest);


    }
    @OnClick(R.id.ic_back)
    public void onClickBack(){
        onLoadActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onLoadActivity();

    }
    public void onLoadActivity(){
        Intent intent=new Intent(this,DetailedGroupActivity.class);
        intent.putExtra("featuredGroup",featuredGroupsResponse);
        intent.putExtra("groupId",featuredGroupsResponse.getGroupId());
        startActivity(intent);
        finish();
        //   ActivityUtils.startActivityFinish(this,DetailedGroupActivity.class,bundle);
    }

    @Override
    public void onStartDiscussionSucess(ArrayList<SignUpResponse> signUpResponses) {
        Bundle bundle=new Bundle();
        bundle.putInt("GroupId",featuredGroupsResponse.getGroupId());
        ActivityUtils.startActivityFinish(this,DiscussionsActivity.class,bundle);
    }

    @Override
    public void onErrorStartDiscussion(String message) {
        AndroidUtil.showSnackBarSafe(this,message);
        finish();
    }

    @Override
    public void onSTartbyAdmin(String message) {
        AndroidUtil.showSnackBarSafe(this,message);
        onLoadActivity();
    }
}
