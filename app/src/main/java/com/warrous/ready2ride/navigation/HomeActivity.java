package com.warrous.ready2ride.navigation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.auth.login.LoginContract;
import com.warrous.ready2ride.auth.login.LoginPresenter;
import com.warrous.ready2ride.auth.profile.PofileFragment;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.DefaultBikeActivity;
import com.warrous.ready2ride.bike.DefaultBikeFragment;
import com.warrous.ready2ride.dealership.DealerShipActivity;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.navigation.fragments.DefaultProfileFragment;
import com.warrous.ready2ride.navigation.fragments.InboxFragment;
import com.warrous.ready2ride.navigation.fragments.InfoFragment;
import com.warrous.ready2ride.navigation.fragments.TrackFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.vp_dealership)
    ViewPager vpDealerships;
    @BindView(R.id.vp_bike)
    ViewPager viewPagerBike;
    private int menuItemId;
    boolean profileAct=false;
    LoginContract.Presenter mPresenter;
    String fcmToken;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_bike)
    ImageView ivBike;
    @BindView(R.id.iv_road_track)
    ImageView ivTrack;
    @BindView(R.id.iv_store)
    ImageView ivStore;
    @BindView(R.id.iv_profile_image)
    ImageView ivProfileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        injectButterKnife(this);
        rlToolbar.setVisibility(View.GONE);
        vpDealerships.setVisibility(View.GONE);
        viewPagerBike.setVisibility(View.GONE);
        //loading the default fragment

        mPresenter=new LoginPresenter(this);
        fcmToken=PreferenceManager.getStringValue(this,PreferenceManager.KEY_FCM_TOKEN);
        FcmRequest fcmRequest=new FcmRequest();
        fcmRequest.setUserId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        fcmRequest.setFcmToken(fcmToken);
        fcmRequest.setId(0);
        fcmRequest.setPlatform("A");
        mPresenter.updateFcm(fcmRequest);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profileAct=extras.getBoolean("ProfileFrag");

        }
        if(profileAct){
            loadFragment(new PofileFragment());
        }else{
            loadFragment(new TrackFragment());
        }
        //getting bottom navigation view and attaching the listener
//        BottomNavigationView navigation = findViewById(R.id.navigationView);
//        navigation.setSelectedItemId(R.id.navigation_info);
//        navigation.setOnNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.iv_home)
    public void onClickHome(){
        Intent intent;
        vpDealerships.setVisibility(View.VISIBLE);
        intent = new Intent(this, DealerShipActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.overridePendingTransition(0,0);
        //  fragment = new HomeFragment();
        // fragment=new DealerShipFragment();
        rlToolbar.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_bike)
    public void onClickBike(){
        Intent intent;
        vpDealerships.setVisibility(View.GONE);
        viewPagerBike.setVisibility(View.VISIBLE);
        intent = new Intent(this, DefaultBikeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.overridePendingTransition(0,0);
        // fragmentj = new BikeFragment();
       // fragment = new DefaultBikeFragment();
        rlToolbar.setVisibility(View.GONE);

    }
    @OnClick(R.id.iv_road_track)
    public void onClickTrack(){
        ivTrack.setImageDrawable(getResources().getDrawable(R.drawable.ic_track_blue));
        ivHome.setImageDrawable(getResources().getDrawable(R.drawable.iv_home_light));
        ivBike.setImageDrawable(getResources().getDrawable(R.drawable.iv_bike_light));
        ivProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_profile));
        ivStore.setImageDrawable(getResources().getDrawable(R.drawable.ic_store_light));
        Fragment fragment = null;
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
    }


//    @OnClick()
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;
//        Intent intent;
//
//        if(menuItemId==item.getItemId()){
//            return false ;
//        }else {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//
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
//                    fragment = new DefaultBikeFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//
//                case R.id.navigation_info:
//                    fragment = new TrackFragment();
//                    viewPagerBike.setVisibility(View.GONE);
//                    rlToolbar.setVisibility(View.GONE);
//                    vpDealerships.setVisibility(View.GONE);
//                    break;
//
//                case R.id.navigation_track:
//                    fragment = new InboxFragment();
//                    viewPagerBike.setVisibility(View.GONE);
//                    vpDealerships.setVisibility(View.GONE);
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//                case R.id.navigation_profile:
//                    fragment = new PofileFragment();
//                    viewPagerBike.setVisibility(View.GONE);
//                    vpDealerships.setVisibility(View.GONE);
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//            }
//            menuItemId=item.getItemId();
//
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
    public void onSucess(int ownerId) {

    }

    @Override
    public void onFcmUpdateSucess(ArrayList<SignUpResponse> signUpResponses) {

    }
}
