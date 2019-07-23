package com.warrous.ready2ride.groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.groups.adapter.GroupMembersAdapter;
import com.warrous.ready2ride.groups.adapter.LatestDiscussionsAdapter;
import com.warrous.ready2ride.groups.discussions.DiscussionCommentsActivity;
import com.warrous.ready2ride.groups.discussions.DiscussionsActivity;
import com.warrous.ready2ride.groups.discussions.StartNewDiscussionActicity;
import com.warrous.ready2ride.groups.discussions.StartNewDiscussionE;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;
import com.warrous.ready2ride.groups.model.GroupMembers;
import com.warrous.ready2ride.groups.model.LeaveGroupRequest;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailedGroupActivity extends BaseActivity implements DetailedGroupContract.View,LatestDiscussionsAdapter.OnItemClickListener{

    @BindView(R.id.rv_members)
    RecyclerView rvMembers;
    @BindView(R.id.rv_latest_discussions)
    RecyclerView rvLatestDiscussions;
    @BindView(R.id.tv_promotion_events)
    TextView tvMembers;
    @BindView(R.id.tv_no_members)
    TextView tvNoMemBers;
    @BindView(R.id.tv_adress_desc)
            TextView tvGroupDesc;
    @BindView(R.id.tv_dealer_name)
            TextView tvGroupName;
    @BindView(R.id.iv_dealership_bg)
    ImageView ivGroupBg;
    @BindView(R.id.tv_discusion)
            TextView tvDiscussion;
    @BindView(R.id.btn_start_new_discussion)
    Button btnstartDiscuss;



    GroupMembersAdapter groupMembersAdapter;
    LatestDiscussionsAdapter latestDiscussionsAdapter;
    LinearLayoutManager memberLinearLayoutManagerM;
    DetailedGroupContract.Presenter mPresenter;
    FeaturedGroupsResponse featuredGroupsResponse;

    ArrayList<GroupMembers> groupMembers;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_group);
        injectButterKnife(this);
        featuredGroupsResponse=(FeaturedGroupsResponse) getIntent().getSerializableExtra("featuredGroup");
        tvGroupDesc.setText(featuredGroupsResponse.getDescription());
        tvGroupName.setText(featuredGroupsResponse.getName());
        groupMembers=new ArrayList<>();
        for(int i=0;i<featuredGroupsResponse.getOwnerDetails().size();i++){
            groupMembers.add(featuredGroupsResponse.getOwnerDetails().get(i));
        }


        GlideManager.loadImageUrl(this,featuredGroupsResponse.getIcon(),ivGroupBg);


       // rvMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if(groupMembers!=null){
            if(groupMembers.size()>0){
                tvMembers.setText(groupMembers.get(0).getGroupCount()+" members");
            }
        }

        memberLinearLayoutManagerM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMembers.setLayoutManager(memberLinearLayoutManagerM);
        groupMembersAdapter=new GroupMembersAdapter(this,null,groupMembers);
        rvMembers.setAdapter(groupMembersAdapter);

        memberLinearLayoutManagerM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
////            rvMembers.setLayoutManager(memberLinearLayoutManagerM);
////            groupMembersAdapter = new GroupMembersAdapter(this, null, featuredRidersResponses);
////            rvMembers.setAdapter(groupMembersAdapter);
        mPresenter=new DetailedGroupPresenter(this);
//      //  mPresenter.getMembers(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_DEALER_ID));

        rvLatestDiscussions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        latestDiscussionsAdapter=new LatestDiscussionsAdapter(this,this,null);
        rvLatestDiscussions.setAdapter(latestDiscussionsAdapter);
        mPresenter.ongetDiscussions(featuredGroupsResponse.getGroupId());

    }

    @OnClick(R.id.tv_see_all)
    public void OnCLickSeeAll(){
        Bundle bundle=new Bundle();
        bundle.putInt("GroupId",featuredGroupsResponse.getGroupId());
        bundle.putString("groupName",featuredGroupsResponse.getName());
        ActivityUtils.startActivityFinish(this,DiscussionsActivity.class,bundle);
    }
    @OnClick(R.id.iv_back)
    public void onClickBack(){
        finish();
    }

    @OnClick(R.id.tv_phone)
    public void onClickLeave(){
        LeaveGroupRequest leaveGroupRequest=new LeaveGroupRequest();
        leaveGroupRequest.setGroupId(featuredGroupsResponse.getGroupId());
        leaveGroupRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        mPresenter.leaveGroup(leaveGroupRequest);

    }
    public void onDisplayDiscussionComments(int discussionId){

        Bundle bundle=new Bundle();
        bundle.putInt("discussionId",discussionId);
        ActivityUtils.startActivity(this,DiscussionCommentsActivity.class,bundle);


    }
    @OnClick(R.id.btn_start_new_discussion)
    public void onClickStartDiscuss(){
        Intent intent=new Intent(this,StartNewDiscussionE.class);
        intent.putExtra("featuredGroup",featuredGroupsResponse);
        intent.putExtra("groupId",featuredGroupsResponse.getGroupId());
        intent.putExtra("groupName",featuredGroupsResponse.getName());
        startActivity(intent);
        finish();
      //  ActivityUtils.startActivity(this,StartNewDiscussionActicity.class,null);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onGetMembersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses) {

//        if(featuredRidersResponses.size()!=0){
//            tvNoMemBers.setVisibility(View.GONE);
//            rvMembers.setVisibility(View.VISIBLE);
//            tvMembers.setText("Members("+featuredRidersResponses.size()+")");
//            memberLinearLayoutManagerM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//            rvMembers.setLayoutManager(memberLinearLayoutManagerM);
//            groupMembersAdapter = new GroupMembersAdapter(this, null, featuredRidersResponses);
//            rvMembers.setAdapter(groupMembersAdapter);
//        }else{
//            tvNoMemBers.setVisibility(View.VISIBLE);
//            rvMembers.setVisibility(View.GONE);
//
//        }

    }

    @Override
    public void onLeaveGroupSucess(ArrayList<SignUpResponse> signUpResponses) {
        finish();

    }

    @Override
    public void ongetDiscussionsSucess(ArrayList<DiscussionsResponse> discussionsResponseArrayList) {
        if(discussionsResponseArrayList!=null) {
            if (discussionsResponseArrayList.size() != 0) {
                rvLatestDiscussions.setVisibility(View.VISIBLE);
                btnstartDiscuss.setVisibility(View.GONE);
                tvDiscussion.setVisibility(View.GONE);
                rvLatestDiscussions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                latestDiscussionsAdapter=new LatestDiscussionsAdapter(this,this,discussionsResponseArrayList);
                rvLatestDiscussions.setAdapter(latestDiscussionsAdapter);
            }else{
                rvLatestDiscussions.setVisibility(View.GONE);
                btnstartDiscuss.setVisibility(View.VISIBLE);
                tvDiscussion.setVisibility(View.VISIBLE);
            }
        }else{
            rvLatestDiscussions.setVisibility(View.GONE);
            btnstartDiscuss.setVisibility(View.VISIBLE);
            tvDiscussion.setVisibility(View.VISIBLE);
        }


    }



    @Override
    public void onItemClick(View view, int position, int discussionId) {
        onDisplayDiscussionComments(discussionId);
    }
}
