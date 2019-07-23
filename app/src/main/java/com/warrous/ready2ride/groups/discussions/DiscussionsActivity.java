package com.warrous.ready2ride.groups.discussions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscussionsActivity extends BaseActivity implements DiscussionsContract.View,DiscussionsAdapter.OnItemClickListener {
    @BindView(R.id.rv_discussions)
    RecyclerView rvDiscussions;
    DiscussionsAdapter discussionsAdapter;
    int groupId;
    DiscussionsContract.Presenter mPresenter;
    @BindView(R.id.tv_no_discussion)
    TextView tvNoDiscusions;
    String groupName;



   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_discussions);
       injectButterKnife(this);
       mPresenter=new DiscussionsPresenter(this);


       Bundle extras = getIntent().getExtras();
       if (extras != null) {
           groupId = extras.getInt("GroupId");
           groupName=extras.getString("groupName");
       }
       rvDiscussions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
       discussionsAdapter=new DiscussionsAdapter(this,this,null);
       rvDiscussions.setAdapter(discussionsAdapter);
       mPresenter.ongetDiscussions(groupId);
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

    @OnClick(R.id.btn_start_new_discussion)
    public void onClickStartNewDiscussion(){
        Bundle bundle=new Bundle();
        bundle.putInt("GroupId",groupId);
        bundle.putString("groupName",groupName);
        ActivityUtils.startActivityFinish(this,StartNewDiscussionActicity.class,bundle);
    }

    public void onDisplayDiscussionComments(int discussionId){

        Bundle bundle=new Bundle();
        bundle.putInt("discussionId",discussionId);
        ActivityUtils.startActivity(this,DiscussionCommentsActivity.class,bundle);
    }

    @Override
    public void ongetDiscussionsSucess(ArrayList<DiscussionsResponse> discussionsResponseArrayList) {
       if(discussionsResponseArrayList!=null){
           if(discussionsResponseArrayList.size()!=0){
               rvDiscussions.setVisibility(View.VISIBLE);
               tvNoDiscusions.setVisibility(View.GONE);
               rvDiscussions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
               discussionsAdapter=new DiscussionsAdapter(this,this,discussionsResponseArrayList);
               rvDiscussions.setAdapter(discussionsAdapter);
           }else{
               rvDiscussions.setVisibility(View.GONE);
               tvNoDiscusions.setVisibility(View.VISIBLE);
           }
       }else{
           rvDiscussions.setVisibility(View.GONE);
           tvNoDiscusions.setVisibility(View.VISIBLE);
       }
    }

    @Override
    public void onErrorStartDiscussion(String message) {

    }


    @Override
    public void onItemClick(View view, int position, int discussionId) {
        onDisplayDiscussionComments(discussionId);
    }
}
