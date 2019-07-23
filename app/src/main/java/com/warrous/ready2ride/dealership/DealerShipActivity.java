package com.warrous.ready2ride.dealership;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.profile.PofileFragment;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.DefaultBikeActivity;
import com.warrous.ready2ride.bike.DefaultBikeFragment;
import com.warrous.ready2ride.common.ViewPagerAdapter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.mydealerships.MyDealershipsActivity;
import com.warrous.ready2ride.finddealer.FindDealerActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.navigation.fragments.InboxFragment;
import com.warrous.ready2ride.navigation.fragments.InfoFragment;
import com.warrous.ready2ride.navigation.fragments.TrackFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DealerShipActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener,DealerShipContract.View{
    @BindView(R.id.vp_dealership)
    ViewPager vpDealerships;
    @BindView(R.id.vp_bike)
    ViewPager viewPagerBike;
    @BindView(R.id.rv_default_bike)
    RelativeLayout rvDefaultBike;

    @BindView(R.id.rv_default_dealer)
    RelativeLayout rvDefaultDealer;
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
    int positon=0;
    DealerShipContract.Presenter mPresenter;
    ArrayList<DealershipResponse> dealershipResponses;
    String stringLatitude;

    String stringLongitude;
    String mprovider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealership);
        injectButterKnife(this);
        vpDealerships.setVisibility(View.VISIBLE);
        viewPagerBike.setVisibility(View.GONE);
        rlToolbar.setVisibility(View.GONE);
        // vpDealerships.setVisibility(View.GONE);
        //loading the default fragment
        dealershipResponses=new ArrayList<>();

          mPresenter=new DealershipPresenter(this);
          ivHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
          mPresenter.getSelectedDealers(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));


//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        } else
//            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
       //   mPresenter.getDealerShips(0,0,0);


        //getting bottom navigation view and attaching the listener
//        BottomNavigationView navigation = findViewById(R.id.navigationView);
//        navigation.setOnNavigationItemSelectedListener(this);
//        vpDealerships.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),getPages()));
    }

    private List<Fragment> getPages() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i <dealershipResponses.size(); i++) {
            dealershipResponses.get(i).setPosition(i);
            fragmentList.add(DealerShipFragment.getInstance(dealershipResponses.get(i),dealershipResponses.size()));
        }
        return fragmentList;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location")
                        .setMessage("Please provide acess to ready2ride to get current Location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(DealerShipActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);

                        if (mLastLocation != null) {
                            //_progressBar.setVisibility(View.INVISIBLE);
                           // mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                        } else {
                            /*if there is no last known location. Which means the device has no data for the loction currently.
                             * So we will get the current location.
                             * For this we'll implement Location Listener and override onLocationChanged*/
                            Log.i("Current Location", "No data for location found");

                            if (!mGoogleApiClient.isConnected())
                                mGoogleApiClient.connect();

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, DealerShipActivity.this);
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;
//        Intent intent;
//
//        if (menuItemId == item.getItemId()) {
//            return false;
//        } else {
//            switch (item.getItemId()) {
//
//                case R.id.navigation_home:
//                    positon=0;
//                    viewPagerBike.setVisibility(View.GONE);
//                    vpDealerships.setVisibility(View.VISIBLE);
//                    rvDefaultDealer.setVisibility(View.GONE);
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
//                    rvDefaultDealer.setVisibility(View.GONE);
//                    intent = new Intent(this, DefaultBikeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    this.startActivity(intent);
//                    // fragmentj = new BikeFragment();
//                    fragment = new DefaultBikeFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//
//                case R.id.navigation_info:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.GONE);
//                    rvDefaultDealer.setVisibility(View.GONE);
//                    fragment = new TrackFragment();
//                    rvDefaultBike.setVisibility(View.GONE);
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//
//                case R.id.navigation_track:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.GONE);
//                    rvDefaultDealer.setVisibility(View.GONE);
//                    rvDefaultBike.setVisibility(View.GONE);
//                    fragment = new InboxFragment();
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//                case R.id.navigation_profile:
//                    vpDealerships.setVisibility(View.GONE);
//                    viewPagerBike.setVisibility(View.GONE);
//                    rvDefaultDealer.setVisibility(View.GONE);
//                    fragment = new PofileFragment();
//                    rvDefaultBike.setVisibility(View.GONE);
//                    rlToolbar.setVisibility(View.GONE);
//                    break;
//            }
//            menuItemId=item.getItemId();
//            return loadFragment(fragment);
//
//        }
//    }
//
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

    @OnClick(R.id.iv_homme)
    public void onClickHome(){
     //   ivHome.setBackgroundResource(R.drawable.ic_home);
        Intent intent;
        vpDealerships.setVisibility(View.VISIBLE);
        viewPagerBike.setVisibility(View.GONE);
        rvDefaultDealer.setVisibility(View.GONE);

        intent = new Intent(this, DealerShipActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

        //  fragment = new HomeFragment();
        // fragment=new DealerShipFragment();
        rlToolbar.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_bike)
    public void onClickBike(){
      //  ivBike.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        Intent intent;
        vpDealerships.setVisibility(View.GONE);
        viewPagerBike.setVisibility(View.VISIBLE);
        vpDealerships.setVisibility(View.GONE);
        rvDefaultDealer.setVisibility(View.GONE);
        intent = new Intent(this, DefaultBikeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
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
        rvDefaultDealer.setVisibility(View.GONE);
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
        rvDefaultDealer.setVisibility(View.GONE);
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
        rvDefaultDealer.setVisibility(View.GONE);
        loadFragment(fragment);
    }

    @Override
    public void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses) {

        if(segmentResponses.size()>0){
            ArrayList<DealershipResponse> selecteddealershipResponses=new ArrayList<>();
            for(int i=0;i<segmentResponses.size();i++){
                if(segmentResponses.get(i).isDefault()){
                    selecteddealershipResponses.add(segmentResponses.get(i));
                }
                if(segmentResponses.get(i).isSelected()){
                    if(selecteddealershipResponses.size()>i){
                        //if(selecteddealershipResponses.get(i).getDealerId()!=segmentResponses.get(i).getDealerId()){
                            selecteddealershipResponses.add(segmentResponses.get(i));
                        //}
                    }


                }
            }
            rvDefaultBike.setVisibility(View.GONE);
            vpDealerships.setVisibility(View.VISIBLE);
            rvDefaultDealer.setVisibility(View.GONE);
            dealershipResponses=selecteddealershipResponses;
            if(dealershipResponses.size()==0){
                rvDefaultBike.setVisibility(View.GONE);
                rvDefaultDealer.setVisibility(View.VISIBLE);
                vpDealerships.setVisibility(View.GONE);
            }else
            {
                vpDealerships.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),getPages()));
            }

        }else{
            rvDefaultBike.setVisibility(View.GONE);
            rvDefaultDealer.setVisibility(View.VISIBLE);
            vpDealerships.setVisibility(View.GONE);
        }


    }
    @OnClick(R.id.btn_create_biked)
    public void onClickDefaultDealer(){
        ActivityUtils.startActivity(this,MyDealershipsActivity.class,null);
    }

    @Override
    public void onEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {


    }

    @Override
    public void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponsesss) {
        if(dealershipResponsesss.size()>0){

            rvDefaultBike.setVisibility(View.GONE);
            vpDealerships.setVisibility(View.VISIBLE);
            rvDefaultDealer.setVisibility(View.GONE);

            dealershipResponses=dealershipResponsesss;
            if(dealershipResponses.size()==0){
                rvDefaultBike.setVisibility(View.GONE);
                rvDefaultDealer.setVisibility(View.VISIBLE);
                vpDealerships.setVisibility(View.GONE);
            }else
            {
                vpDealerships.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),getPages()));
            }

        }else{
            rvDefaultBike.setVisibility(View.GONE);
            rvDefaultDealer.setVisibility(View.VISIBLE);
            vpDealerships.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSavePromotionSUcess(ArrayList<SignUpResponse> signUpResponses) {

    }

    @Override
    protected void onStop() {
       // mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /*Method to get the enable location settings dialog*/
    public void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(DealerShipActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(this, "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            checkLocationPermission();
            return;
        } else {
            /*Getting the location after aquiring location service*/
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                //_progressBar.setVisibility(View.INVISIBLE);
               // mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, DealerShipActivity.this);
            }
        }
    }

    /*When Location changes, this method get called. */
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //  _progressBar.setVisibility(View.INVISIBLE);
        //  mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude());

    }
}
