package com.warrous.ready2ride.invitebuddies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.invitebuddies.adapter.MyBuddiesAdapter;
import com.warrous.ready2ride.invitebuddies.model.AppUsers;
import com.warrous.ready2ride.invitebuddies.model.BuddiesKnown;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBuddiesActivity extends BaseActivity implements DefaultInviteBuddiesContract.View {

    @BindView(R.id.rv_buddies)
    RecyclerView rvBuddies;
    MyBuddiesAdapter myBuddiesAdapter;
    DefaultInviteBuddiesContract.Presenter mPresenter;
    @BindView(R.id.tv_no_buddies)
    TextView tvNoBuddies;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buddies);
        injectButterKnife(this);

        mPresenter=new DefaultInvitebuddiesPresenter(this);
        mPresenter.getInviteBuddies(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));


        rvBuddies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myBuddiesAdapter=new MyBuddiesAdapter(this,null,null);
        rvBuddies.setAdapter(myBuddiesAdapter);
    }

    @OnClick(R.id.iv_back)
    public void onClickBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @OnClick(R.id.btn_invite_buddy)
    public void onClickInviteBuddies(){
        ActivityUtils.startActivity(this,InviteBuddiesActivity.class,null);
    }

    @Override
    public void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses) {

    }

    @Override
    public void onFeaturedRidersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses) {

    }

    @Override
    public void onInviteBuddiesSucess(ArrayList<SignUpResponse> signUpResponses) {

    }

    @Override
    public void ongetInvideBudddiesSucess(ArrayList<BuddyListResponse> inviteBuddiesResponses) {
        if (inviteBuddiesResponses != null) {
            if(inviteBuddiesResponses.size()!=0){
                tvNoBuddies.setVisibility(View.GONE);
                rvBuddies.setVisibility(View.VISIBLE);
                rvBuddies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                myBuddiesAdapter=new MyBuddiesAdapter(this,null,inviteBuddiesResponses);
                rvBuddies.setAdapter(myBuddiesAdapter);
            }
            else{
                tvNoBuddies.setVisibility(View.VISIBLE);
                rvBuddies.setVisibility(View.GONE);
            }
        }else{
            tvNoBuddies.setVisibility(View.VISIBLE);
            rvBuddies.setVisibility(View.GONE);
        }

    }

    @Override
    public void onGetAppUsersSucess(ArrayList<AppUsers> appUsersList) {

    }

    @Override
    public void onGetBuddiesKnown(ArrayList<BuddiesKnown> buddiesKnownsList) {

    }

    @Override
    public void onSendBudyRequest(ArrayList<SignUpResponse> signUpResponses) {

    }
}
