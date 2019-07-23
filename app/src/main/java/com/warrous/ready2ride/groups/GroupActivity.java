package com.warrous.ready2ride.groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.createbike.CreateBikeActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.framework.Ready2RideLog;
import com.warrous.ready2ride.groups.adapter.FeaturedGroupsAdapterG;
import com.warrous.ready2ride.groups.adapter.GroupBikeItemsAdapter;
import com.warrous.ready2ride.groups.model.GroupsListResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupActivity extends BaseActivity implements GroupBikeItemsAdapter.OnItemClickListener,GroupContract.View,FeaturedContract.View,FeaturedGroupsAdapterG.OnItemClickListener{
    @BindView(R.id.rv_bike_items)
    RecyclerView rvGroupItems;
    @BindView(R.id.rv_featured_groups)
    RecyclerView rvFeaturedGroups;
    @BindView(R.id.tv_no_featured_groups)
    TextView tvNoFeatureGroups;


    FeaturedGroupsAdapterG featuredGroupsAdapterG;
    GroupBikeItemsAdapter groupBikeItemsAdapter;

    LinearLayoutManager memberLinearLayoutManagerF;

    GroupContract.Presenter mPresenter;
    FeaturedContract.Presenter fPresenter;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        injectButterKnife(this);



        rvGroupItems.setLayoutManager(
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        groupBikeItemsAdapter=new GroupBikeItemsAdapter(this,this,null);
        rvGroupItems.setAdapter(groupBikeItemsAdapter);


        memberLinearLayoutManagerF=new LinearLayoutManager(getContext());

      //  rvFeaturedGroups.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        featuredGroupsAdapterG=new FeaturedGroupsAdapterG(this,this,null);
       // rvFeaturedGroups.setAdapter(featuredGroupsAdapterG);
        mPresenter=new GroupPresenter(this);
        fPresenter=new FeaturedGroupPresenter(this);

        mPresenter.getGroups(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID),true);

        fPresenter.getFeaturedGroups(0,true);
      //  Ready2RideLog.getInstance().info("OwnerId",""+PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));




    }
    @OnClick(R.id.btn_create_group)
    public void onButtonCreateGroup(){
        ActivityUtils.startActivityFinish(this,CreateGroupActivity.class,null);
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

    @Override
    public void onItemClick(View view, int position,FeaturedGroupsResponse featuredGroupsResponse) {
//        Bundle bundle=new Bundle();
//        bundle.putInt("groupId",featuredGroupsResponse.getGroupId());
//        bundle.put
//        ActivityUtils.startActivity(this,DetailedGroupActivity.class,bundle);
        Intent intent=new Intent(this,DetailedGroupActivity.class);
        intent.putExtra("featuredGroup",featuredGroupsResponse);

        startActivity(intent);
    }

    @Override
    public void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses) {



    }

    @Override
    public void onGetGroupSucess(ArrayList<FeaturedGroupsResponse> groupLists) {
        if(groupLists.size()!=0){
            rvGroupItems.setLayoutManager(
                    new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
            groupBikeItemsAdapter=new GroupBikeItemsAdapter(this,this,groupLists);
            rvGroupItems.setAdapter(groupBikeItemsAdapter);
        }


    }

    @Override
    public void onFeaturedGroupSucessF(ArrayList<FeaturedGroupsResponse> segmentResponses) {
        if(segmentResponses.size()!=0){
            tvNoFeatureGroups.setVisibility(View.GONE);
            rvFeaturedGroups.setVisibility(View.VISIBLE);
            memberLinearLayoutManagerF = new LinearLayoutManager(getContext());
            rvFeaturedGroups.setLayoutManager(memberLinearLayoutManagerF);
            featuredGroupsAdapterG = new FeaturedGroupsAdapterG(this, this, segmentResponses);
            rvFeaturedGroups.setAdapter(featuredGroupsAdapterG);
        }else{
            tvNoFeatureGroups.setVisibility(View.VISIBLE);
            rvFeaturedGroups.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetGroupSucessF(ArrayList<FeaturedGroupsResponse> groupLists) {

    }

    @Override
    public void onItemClickF(View view, int position, FeaturedGroupsResponse featuredGroupsResponse) {
        Intent intent=new Intent(this,DetailedGroupActivityFea.class);
        intent.putExtra("featuredGroup",featuredGroupsResponse);

        startActivity(intent);
    }
}
