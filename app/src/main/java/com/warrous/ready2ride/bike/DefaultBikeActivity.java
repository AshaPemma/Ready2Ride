package com.warrous.ready2ride.bike;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.ImagePickerUtils;
import com.warrous.ready2ride.auth.profile.PofileFragment;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.bike.model.RideLogResponse;
import com.warrous.ready2ride.common.ViewPagerAdapter;
import com.warrous.ready2ride.createbike.CreateBikeActivity;
import com.warrous.ready2ride.createbike.CreateBikeActivityGarage;
import com.warrous.ready2ride.dealership.DealerShipActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.navigation.fragments.InboxFragment;
import com.warrous.ready2ride.navigation.fragments.InfoFragment;
import com.warrous.ready2ride.navigation.fragments.TrackFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DefaultBikeActivity extends BaseActivity implements DefaultBikeContract.View {

    @BindView(R.id.vp_dealership)
    ViewPager vpDealerships;
    @BindView(R.id.vp_bike)
    ViewPager viewPagerBike;
    @BindView(R.id.rv_default_bike)
    RelativeLayout rvDefaultBike;

    @BindView(R.id.iv_homme)
    ImageView ivHome;
    @BindView(R.id.iv_bike)
    ImageView ivBike;
    @BindView(R.id.iv_road_track)
    ImageView ivTrack;
    @BindView(R.id.iv_store)
    ImageView ivStore;
    @BindView(R.id.iv_profile_image)
    ImageView ivProfileImage;

    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    private int menuItemId;

    DefaultBikeContract.Presenter mpresenter;

    ArrayList<DefaultBikeDetailsResponse> bikesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealership);
        injectButterKnife(this);
        ivBike.setImageDrawable(getResources().getDrawable(R.drawable.ic_bike));
        mpresenter=new DefaultBikePresenter(this);
        bikesList=new ArrayList<>();
        mpresenter.getCycleDetails(0,PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));

        vpDealerships.setVisibility(View.GONE);
        viewPagerBike.setVisibility(View.VISIBLE);

        rlToolbar.setVisibility(View.GONE);
       // vpDealerships.setVisibility(View.GONE);
        //loading the default fragment



        //getting bottom navigation view and attaching the listener
//        BottomNavigationView navigation = findViewById(R.id.navigationView);
//        navigation.setSelectedItemId(R.id.navigation_bike);
//        navigation.setOnNavigationItemSelectedListener(this);
        viewPagerBike.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),getPages()));
    }

    @OnClick(R.id.btn_create_bike)
    public void btnCreateBike(){
        ActivityUtils.startActivity(this,CreateBikeActivityGarage.class,null);
        finish();
    }
    private List<Fragment> getPages() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i <bikesList.size(); i++) {
            bikesList.get(i).setPosition(i);
            fragmentList.add(DefaultBikeFragment.getInstance(bikesList.get(i),bikesList.size(),i));
        }
        return fragmentList;
    }

    @OnClick(R.id.iv_homme)
    public void onClickHome(){
        Intent intent;
        vpDealerships.setVisibility(View.VISIBLE);
        intent = new Intent(this, DealerShipActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

        //  fragment = new HomeFragment();
        // fragment=new DealerShipFragment();
        rlToolbar.setVisibility(View.GONE);
        rvDefaultBike.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_bike)
    public void onClickBike(){
        Intent intent;
        vpDealerships.setVisibility(View.GONE);
        viewPagerBike.setVisibility(View.VISIBLE);
        intent = new Intent(this, DefaultBikeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        // fragmentj = new BikeFragment();
        // fragment = new DefaultBikeFragment();
        rlToolbar.setVisibility(View.GONE);
        rvDefaultBike.setVisibility(View.GONE);

    }
    @OnClick(R.id.iv_road_track)
    public void onClickTrack(){
        ivTrack.setImageDrawable(getResources().getDrawable(R.drawable.ic_track_blue));
        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.iv_home_light));
        ivBike.setImageDrawable(getResources().getDrawable(R.drawable.iv_bike_light));
        ivProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_profile));
        ivStore.setImageDrawable(getResources().getDrawable(R.drawable.ic_store_light));
        Fragment fragment = null;
        rvDefaultBike.setVisibility(View.GONE);
        fragment = new TrackFragment();
        viewPagerBike.setVisibility(View.GONE);
        rlToolbar.setVisibility(View.GONE);
        vpDealerships.setVisibility(View.GONE);
        loadFragment(fragment);
    }
    @OnClick(R.id.iv_store)
    public void onClickStore(){
        ivTrack.setImageDrawable(getResources().getDrawable(R.drawable.ic_track_light));
        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.iv_home_light));
        ivBike.setImageDrawable(getResources().getDrawable(R.drawable.iv_bike_light));
        ivProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_profile));
        ivStore.setImageDrawable(getResources().getDrawable(R.drawable.ic_store_blue));
        Fragment fragment = null;
        fragment = new InboxFragment();
        viewPagerBike.setVisibility(View.GONE);
        vpDealerships.setVisibility(View.GONE);
        rlToolbar.setVisibility(View.GONE);
        rvDefaultBike.setVisibility(View.GONE);
        loadFragment(fragment);
    }
    @OnClick(R.id.iv_profile_image)
    public void onClickProfile(){
        ivTrack.setImageDrawable(getResources().getDrawable(R.drawable.ic_track_light));
        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.iv_home_light));
        ivBike.setImageDrawable(getResources().getDrawable(R.drawable.iv_bike_light));
        ivProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_profile));
        ivStore.setImageDrawable(getResources().getDrawable(R.drawable.ic_store_light));
        Fragment fragment = null;
        fragment = new PofileFragment();
        viewPagerBike.setVisibility(View.GONE);
        vpDealerships.setVisibility(View.GONE);
        rlToolbar.setVisibility(View.GONE);
        loadFragment(fragment);
        rvDefaultBike.setVisibility(View.GONE);
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;
//        Intent intent;
//        if (menuItemId == item.getItemId()) {
//            return false;
//        } else {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    viewPagerBike.setVisibility(View.GONE);
//                    vpDealerships.setVisibility(View.VISIBLE);
//                    intent = new Intent(this, DealerShipActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    this.startActivity(intent);
//
//                    //  fragment = new HomeFragment();
//                    // fragment=new DealerShipFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//
//                case R.id.navigation_bike:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.VISIBLE);
//                    intent = new Intent(this, DefaultBikeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    this.startActivity(intent);
//                    // fragmentj = new BikeFragment();
//                    // fragment=new DefaultBikeFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//
//
//                case R.id.navigation_info:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.GONE);
//                    fragment = new TrackFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    rvDefaultBike.setVisibility(View.GONE);
//                    break;
//
//                case R.id.navigation_track:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.GONE);
//                    fragment = new InboxFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    rvDefaultBike.setVisibility(View.GONE);
//                    break;
//                case R.id.navigation_profile:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.GONE);
//                    fragment = new PofileFragment();
//                    rvDefaultBike.setVisibility(View.GONE);
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//            }
//            menuItemId=item.getItemId();
//            return loadFragment(fragment);
//        }
//    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses) {
        if(segmentResponses.size()>0){
            viewPagerBike.setVisibility(View.VISIBLE);
            rvDefaultBike.setVisibility(View.GONE);
            bikesList=segmentResponses;
            viewPagerBike.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),getPages()));
        }else{
            viewPagerBike.setVisibility(View.GONE);
            rvDefaultBike.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onGetRideLogResponse(ArrayList<RideList> segmentResponses) {

    }


}
