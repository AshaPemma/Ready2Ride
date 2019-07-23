package com.warrous.ready2ride.finddealer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.finddealer.adapter.FindDealerAdapter;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.setasdealership.SetasDealerShipActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import android.location.LocationListener;
import android.widget.Toast;


public class FindDealerActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, OnMapReadyCallback, FindDealerAdapter.OnItemClickListener, FindDealerContract.View {
    private MapView mapView;

    FindDealerAdapter findDealerAdapter;
    FindDealerContract.Presenter mPresenter;
    ArrayList<DealershipResponse> dealershipResponses;
    LinearLayoutManager memberLinearLayoutManager;
    private GoogleMap gmap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    @BindView(R.id.et_search)
    EditText etSearch;


    @BindView(R.id.rv_map_items)
    RecyclerView rvDealerShipItems;
    @BindView(R.id.tv_no_dealerships)
    TextView tvNoDealerships;

    SupportMapFragment mapFragment;
    String stringLatitude;

    String stringLongitude;
    String mprovider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_dealer);
        injectButterKnife(this);
        dealershipResponses = new ArrayList<>();
     //   checkLocationPermission();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();



//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        mprovider = locationManager.getBestProvider(criteria, false);
//
//        if (mprovider != null && !mprovider.equals("")) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            Location location = locationManager.getLastKnownLocation(mprovider);
//            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
//
//            if (location != null)
//                onLocationChanged(location);
//            else
//                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
//        }

       // rvDealerShipItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        memberLinearLayoutManager=new LinearLayoutManager(getContext());
        findDealerAdapter=new FindDealerAdapter(this,this,null);
        mPresenter=new FindDealerPresenter(this);
//        GPSTracker gpsTracker = new GPSTracker(this);
//        if (gpsTracker.getIsGPSTrackingEnabled())
//        {
//             stringLatitude = String.valueOf(gpsTracker.getLatitude());
//
//             stringLongitude = String.valueOf(gpsTracker.getLongitude());
//
//        }
//        else
//        {
//            // can't get location
//            // GPS or Network is not enabled
//            // Ask user to enable GPS/network in settings
//            gpsTracker.showSettingsAlert();
//        }

        if (stringLatitude!=null){

        }if(stringLongitude!=null){

        }

      //  rvDealerShipItems.setAdapter(findDealerAdapter);
        // Gets the MapView from the XML layout and creates it
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AndroidUtil.hideKeyBoard(FindDealerActivity.this,etSearch);
                filter(etSearch.getText().toString());
            }
        });



    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<DealershipResponse> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (int i=0;i<dealershipResponses.size();i++) {
            //if the existing elements contains the search input
            if (dealershipResponses.get(i).getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(dealershipResponses.get(i));
            }
        }

        //calling a method of the adapter class and passing the filtered list
        findDealerAdapter.filterList(filterdNames);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(37.4233438, -122.0728817))
//                .title("LinkedIn")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(37.4629101,-122.2449094))
//                .title("Facebook")
//                .snippet("Facebook HQ: Menlo Park"));
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(37.3092293, -122.1136845))
//                .title("Apple"));

      //  googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
    }
    @OnClick(R.id.iv_back)
    public void onClickBack(){
        finish();
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
                                ActivityCompat.requestPermissions(FindDealerActivity.this,
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
                            mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                        } else {
                            /*if there is no last known location. Which means the device has no data for the loction currently.
                             * So we will get the current location.
                             * For this we'll implement Location Listener and override onLocationChanged*/
                            Log.i("Current Location", "No data for location found");

                            if (!mGoogleApiClient.isConnected())
                                mGoogleApiClient.connect();

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, FindDealerActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    public void setMarkers(ArrayList<DealershipResponse> dealershipResponses){
        for(int  i=0;i<dealershipResponses.size();i++){
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);

            gmap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(dealershipResponses.get(i).getDealerLatitude()), Double.parseDouble(dealershipResponses.get(i).getDealerLongitude())))
                    .title(dealershipResponses.get(i).getName()))
            .setIcon(icon);
        }

        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(Double.parseDouble(dealershipResponses.get(0).getDealerLatitude()), Double.parseDouble(dealershipResponses.get(0).getDealerLongitude())), new LatLng(Double.parseDouble(dealershipResponses.get(0).getDealerLatitude()),Double.parseDouble(dealershipResponses.get(0).getDealerLongitude())));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
      //  gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(dealershipResponses.get(0).getDealerLatitude()), Double.parseDouble(dealershipResponses.get(0).getDealerLongitude())), 10));
    }
    @Override
    public void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses) {
        dealershipResponses=new ArrayList<>();
        dealershipResponses=segmentResponses;

        if(segmentResponses.size()!=0){
            setMarkers(segmentResponses);
            tvNoDealerships.setVisibility(View.GONE);
            rvDealerShipItems.setVisibility(View.VISIBLE);
            memberLinearLayoutManager = new LinearLayoutManager(getContext());
            rvDealerShipItems.setLayoutManager(memberLinearLayoutManager);
            findDealerAdapter = new FindDealerAdapter(this, this, segmentResponses);
            rvDealerShipItems.setAdapter(findDealerAdapter);
        }else{
            tvNoDealerships.setVisibility(View.VISIBLE);
            rvDealerShipItems.setVisibility(View.GONE);
        }



    }

    @Override
    public void onItemClick(View view, int position, DealershipResponse dealershipResponse) {

//        Bundle bundle=new Bundle();
//        bundle.putParcelable("dealerShip",dealershipResponse);
//

        Intent intent=new Intent(this,SetasDealerShipActivity.class);
        intent.putExtra("dealerShip",dealershipResponse);
        startActivity(intent);



      //  ActivityUtils.startActivity(this,SetasDealerShipActivity.class,null);
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
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
                            status.startResolutionForResult(FindDealerActivity.this, 1000);
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
                mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, FindDealerActivity.this);
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
