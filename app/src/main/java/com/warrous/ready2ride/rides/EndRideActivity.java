package com.warrous.ready2ride.rides;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class EndRideActivity extends BaseActivity implements
        OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,SaveRideContract.View{

    private double calculatedSpeed = 0;
    double timeC = 0,distanceC=0,speedC = 0;
    int subrideId=1;

    SupportMapFragment mapFragment;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private GoogleMap gmap;
    private Polyline gpsTrack;
    int cycleId,rideId;
    double odometerReading;
    boolean isPause=false;
    SaveRideRequest saveRideRequest;
    SaveRideContract.Presenter mPresenter;
    private ArrayList<LatLng> points;//added
    ArrayList<LatLongPoints> usersCurent;
    ArrayList<LatLongPoints> pausedPoints;
    Polyline line; //added
    private LatLng lastKnownLatLng;
    ArrayList<RidePoints> ridePointsList;
    private static final float MINIMUM_ACCURACY = 10; // If the accuracy of the measurement is worse than 10 meters, we will not take the location into account
    private static final float MINIMUM_DISTANCE_BETWEEN_POINTS = 10; // If the user hasn't moved at least 20 meters, we will not take the location into account
    private Location lastLocationloc;
    boolean isPaused=false;
    @BindView(R.id.iv_pause_ride)
    ImageView ivPauseRide;
    @BindView(R.id.tv_mph)
    TextView tvMph;

    @BindView(R.id.tvo1)
    TextView tvO1;
    @BindView(R.id.tvo2)
    TextView tvO2;
    @BindView(R.id.tvo3)
    TextView tvO3;
    @BindView(R.id.tvo4)
    TextView tvO4;
    @BindView(R.id.tvo5)
    TextView tvO5;
    @BindView(R.id.tvo6)
    TextView tv06;



    int randomAndroidColor;

    String startLat="",startLong="",endLat="",endLong="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_ride);
        injectButterKnife(this);
mPresenter=new SaveRidePresenter(this);
        points = new ArrayList<>();
        ridePointsList=new ArrayList<>();
        usersCurent=new ArrayList<>();
        pausedPoints=new ArrayList<>();


        Bundle extras = getIntent().getExtras();
        String value = null;
        if (extras != null) {
            value = extras.getString("rideId");
            cycleId=extras.getInt("cycleId");
            startLat=extras.getString("startLat");
            startLong=extras.getString("startlong");
            odometerReading=extras.getInt("odometerReading");
        }
        if(value!=null){
            rideId=Integer.parseInt(value);
        }

        double num = odometerReading;

        String number = String.valueOf(num);
        String newOR=String.format("%.1f", num);
       // Toast.makeText(this,newOR,Toast.LENGTH_LONG).show();
        String[] str = newOR.split("\\.");//now str[0] is "hello" and str[1] is "goodmorning,2,1"
        String str1 = null,str2 = null;
        if(str.length==0){

        }else if(str.length==1){
            str1 = str[0];
        }else if(str.length==2){
            str1=str[0];
            str2=str[1];
        }
        if(!TextUtils.isEmpty(str1)) {
            for (int i = 0; i < str1.length(); i++) {
                int j = Character.digit(number.charAt(i), 10);

                if (str1.length() == 5) {
                    if (i == 0) {
                        tv06.setText(String.valueOf(j));
                    }
                    if (i == 1) {
                        tvO1.setText(String.valueOf(j));
                    }
                    if (i == 2) {
                        tvO2.setText(String.valueOf(j));
                    }
                    if (i == 3) {
                        tvO3.setText(String.valueOf(j));
                    }
                    if (i == 4) {
                        tvO4.setText(String.valueOf(j));
                    }

                }
                else if (str1.length() == 4) {
                    if (i == 0) {
                        tvO1.setText(String.valueOf(j));
                    }
                    if (i == 1) {
                        tvO2.setText(String.valueOf(j));
                    }
                    if (i == 2) {
                        tvO3.setText(String.valueOf(j));
                    }
                    if (i == 3) {
                        tvO4.setText(String.valueOf(j));
                    }
                } else if (str1.length() == 3) {
                    if (i == 0) {
                        tvO2.setText(String.valueOf(j));
                    }
                    if (i == 1) {
                        tvO3.setText(String.valueOf(j));
                    }
                    if (i == 2) {
                        tvO4.setText(String.valueOf(j));
                    }
                    tvO1.setText("0");

                } else if (str1.length() == 2) {
                    if (i == 0) {
                        tvO3.setText(String.valueOf(j));
                    }
                    if (i == 1) {
                        tvO4.setText(String.valueOf(j));
                    }
                    tvO1.setText("0");
                    tvO2.setText("0");
                } else if (str1.length() == 1) {
                    if (i == 0) {
                        tvO4.setText(String.valueOf(j));
                    }
                    tvO1.setText("0");
                    tvO2.setText("0");
                    tvO3.setText("0");

                }


            }
        }
        if(TextUtils.isEmpty(str2)){
            tvO5.setText("0");
        }else{
            tvO5.setText(str2);
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
//       mapFragment.getMapAsync(this);
////
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
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
                                ActivityCompat.requestPermissions(EndRideActivity.this,
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

                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);

//                            gmap.addMarker(new MarkerOptions()
//                                    .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))
//                                    .title("DealerShip"));

                            LatLngBounds AUSTRALIA = new LatLngBounds(
                                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                          //  gmap.addMarker(new MarkerOptions()
                                   // .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).icon(icon));
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
                           // gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), 18));//
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

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, EndRideActivity.this);
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

    @OnClick(R.id.btn_end_ride)
    public void onClickButtonEndride(){

        if(mLastLocation!=null){
            endLat=String.valueOf(mLastLocation.getLatitude());
            endLong=String.valueOf(mLastLocation.getLongitude());
        }
        saveRideRequest=new SaveRideRequest();
        saveRideRequest.setCycleId(cycleId);
        saveRideRequest.setDescription("");
        saveRideRequest.setEndGeoLat(endLat);
        saveRideRequest.setEndGeoLong(endLong);
        saveRideRequest.setOdometerReading(0);
        saveRideRequest.setName("");
        saveRideRequest.setOwnerId(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
        saveRideRequest.setRideId(rideId);
        saveRideRequest.setStartGeoLat(startLat);
        saveRideRequest.setStartGeoLon(startLong);
        saveRideRequest.setIsRide("2");
        saveRideRequest.setRideTypeId(1);
        saveRideRequest.setRideEnded(true);
        saveRideRequest.setRideSaved(false);
        saveRideRequest.setRideLog(ridePointsList);

        mPresenter.saveRideRequest(saveRideRequest);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;

//        LatLng calymayor = new LatLng(19.345822, -99.152274);
//        gmap.moveCamera(CameraUpdateFactory.newLatLng(calymayor));
//        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(calymayor, 15));
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(getResources().getColor(R.color.googlemapsBlue));
        polylineOptions.width(12);
        gpsTrack = gmap.addPolyline(polylineOptions);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gmap.setMyLocationEnabled(true);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

//    @Override
//    protected void onStop() {
//        mGoogleApiClient.disconnect();
//        super.onStop();
//    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
        startLocationUpdates();
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
        mLocationRequest.setInterval(5000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(5000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(500);

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
                            status.startResolutionForResult(EndRideActivity.this, 1000);
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
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);

//                gmap.addMarker(new MarkerOptions()
//                        .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))
//                        .title("DealerShip"));

                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), 18));//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, EndRideActivity.this);
               //LocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000,10, EndRideActivity.this);
            }
        }
    }

    /*When Location changes, this method get called. */
    @Override
    public void onLocationChanged(Location location) {



//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        LatLng latLng = new LatLng(latitude, longitude); //you already have this
//
//        points.add(latLng); //added
//
//        redrawLine(); //added


//        gmap.addMarker(new MarkerOptions()
//                .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))
//                .title("DealerShip"));
//



//        if (location.getAccuracy() < MINIMUM_ACCURACY) {
//            if (lastLocationloc == null || lastLocationloc.distanceTo(location) > MINIMUM_DISTANCE_BETWEEN_POINTS) {
                // Add the new location to your polyline
                if (mLastLocation != null) {
                    double elapsedTime = (location.getTime() - mLastLocation.getTime()) / 1_000;// Convert milliseconds to seconds
                    timeC=elapsedTime;
                    calculatedSpeed = mLastLocation.distanceTo(location) / elapsedTime;
                    distanceC=mLastLocation.distanceTo(location);
                }


        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                /* There you have it, a speed value in m/s */
                mLastLocation = location;
        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
//        gmap.addMarker(new MarkerOptions()
//                .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).icon(icon));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
        //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), 18));//

        String speedS = null;

        double num = odometerReading+distanceC;
        odometerReading=num;
        String numb=String.valueOf(num);
        String newOR=String.format("%.1f", num);
      //  Toast.makeText(this,newOR,Toast.LENGTH_LONG).show();
        String[] str = newOR.split("\\.");//now str[0] is "hello" and str[1] is "goodmorning,2,1"
        String str1 = null,str2 = null;
       if(str.length==0){
           
       }else if(str.length==1){
            str1 = str[0];
       }else if(str.length==2){
           str1=str[0];
            str2=str[1];
       }
      
       


        String number = String.valueOf(num);
       if(!TextUtils.isEmpty(str1)){
           for(int i = 0; i <str1.length(); i++) {
               int j = Character.digit(number.charAt(i), 10);

               if (str1.length() == 5) {
                   if (i == 0) {
                       tv06.setText(String.valueOf(j));
                   }
                   if (i == 1) {
                       tvO1.setText(String.valueOf(j));
                   }
                   if (i == 2) {
                       tvO2.setText(String.valueOf(j));
                   }
                   if (i == 3) {
                       tvO3.setText(String.valueOf(j));
                   }
                   if (i == 4) {
                       tvO4.setText(String.valueOf(j));
                   }

               }
               else if(str1.length()==4){
                   if(i==0){
                       tvO1.setText(String.valueOf(j));
                   }
                   if(i==1){
                       tvO2.setText(String.valueOf(j));
                   }
                   if(i==2){
                       tvO3.setText(String.valueOf(j));
                   }
                   if(i==3){
                       tvO4.setText(String.valueOf(j));
                   }
               }else if(str1.length()==3){
                   if(i==0){
                       tvO2.setText(String.valueOf(j));
                   }
                   if(i==1){
                       tvO3.setText(String.valueOf(j));
                   }
                   if(i==2){
                       tvO4.setText(String.valueOf(j));
                   }
                   tvO1.setText("0");

               }else if(str1.length()==2){
                   if(i==0){
                       tvO3.setText(String.valueOf(j));
                   }
                   if(i==1){
                       tvO4.setText(String.valueOf(j));
                   }
                   tvO1.setText("0");
                   tvO2.setText("0");
               }else if(str1.length()==1){
                   if(i==0){
                       tvO4.setText(String.valueOf(j));
                   }
                   tvO1.setText("0");
                   tvO2.setText("0");
                   tvO3.setText("0");

               }



           }
       }


            if(TextUtils.isEmpty(str2)){
                tvO5.setText("0");
            }else{
                tvO5.setText(str2);
            }



            double speed = location.hasSpeed() ? location.getSpeed() : calculatedSpeed;
                //Convert meters/second to miles/hour
        if(speed!=0){
            speed = speed * 2.2369362920544f/3.6f;
        }else{
            speedC=0;
        }

                if(speed!=0){
                    speedS=String.format("%.3f", speed);
                    if(speed!=Double.NaN){
                        speedC=speed;
                    }else{
                        speedC=0;
                    }

                }

             //   Toast.makeText(this,"Speed in "+speedS+" miles/hour",Toast.LENGTH_LONG).show();
                int a = (int) Math.round(speed);
                tvMph.setText(String.valueOf(a));
                lastKnownLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                LatLongPoints latLongPoints=new LatLongPoints();
                latLongPoints.setLatitude(location.getLatitude());
                latLongPoints.setLongitude(location.getLongitude());
                usersCurent.add(latLongPoints);

                if(!isPaused){
                    RidePoints ridePoints=new RidePoints();
                    ridePoints.setColor(String.valueOf(randomAndroidColor));
                    if (Double.isNaN(timeC)) {
                        ridePoints.setTime(0);
                    }else{
                        ridePoints.setTime(timeC);
                    }
                    if (Double.isNaN(distanceC)) {
                        ridePoints.setDistance(0);
                    }else{
                        ridePoints.setDistance(distanceC);
                    }
                    if (Double.isNaN(speedC)) {
                        ridePoints.setSpeed(0);
                    }else{
                        ridePoints.setSpeed(speedC);
                    }
                    ridePoints.setStartLatitude(mLastLocation.getLatitude());
                    ridePoints.setStartLongitude(mLastLocation.getLongitude());
                    ridePoints.setEndLatitude(mLastLocation.getLatitude());
                    ridePoints.setEndLongitude(mLastLocation.getLongitude());
                    ridePoints.setRideId(rideId);
                   // if(!isPaused){
                        ridePoints.setSubRideId(subrideId);
                        ridePoints.setPaused(false);
//                    }else{
//                        ridePoints.setSubRideId(subrideId);
//                        ridePoints.setPaused(true);
//                    }

                    ridePointsList.add(ridePoints);
                    updateTrack();

                }
                //else{
//                    subrideId++;
//                    isPaused=true;
//                    RidePoints RideLog=new RidePoints();
//                    RideLog.setColor(String.valueOf(generateRandomColor()));
//                    RideLog.setTime(timeC);
//                    RideLog.setDistance(distanceC);
//                    RideLog.setSpeed(speedC);
//                    RideLog.setStartLatitude(mLastLocation.getLatitude());
//                    RideLog.setStartLongitude(mLastLocation.getLongitude());
//                    RideLog.setRideId(rideId);
//                    RideLog.setSubRideId(subrideId);
//                    RideLog.setPaused(isPaused);
//                    ridePointsList.add(RideLog);
//                    updateTrack();
              //  }






          //  }
      //  }

        //  _progressBar.setVisibility(View.INVISIBLE);
        //  mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude());

    }
    public int generateRandomColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }
    private void updateTrack() {
        List<LatLng> points = gpsTrack.getPoints();
        points.add(lastKnownLatLng);
        gpsTrack.setPoints(points);
    }

    public void covertListtoJson(){
        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(ridePointsList).getAsJsonArray();
    }
    private void redrawLine(){

        gmap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
       // addMarker(); //add Marker in current position

        line = gmap.addPolyline(options); //add Polyline
    }

    @OnClick(R.id.iv_pause_ride)
    public void onClickPauseRide(){



        if(!isPaused){
            isPause=false;
            ivPauseRide.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_button));
            isPaused=true;
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pause_icon);

            RidePoints ridePoints=new RidePoints();
            ridePoints.setColor(String.valueOf(randomAndroidColor));
            if (Double.isNaN(timeC)) {
                ridePoints.setTime(0);
            }else{
                ridePoints.setTime(timeC);
            }
            if (Double.isNaN(distanceC)) {
                ridePoints.setDistance(0);
            }else{
                ridePoints.setDistance(distanceC);
            }
            if (Double.isNaN(speedC)) {
                ridePoints.setSpeed(0);
            }else{
                ridePoints.setSpeed(speedC);
            }
            ridePoints.setStartLatitude(mLastLocation.getLatitude());
            ridePoints.setStartLongitude(mLastLocation.getLongitude());
            ridePoints.setEndLatitude(mLastLocation.getLatitude());
            ridePoints.setEndLongitude(mLastLocation.getLongitude());
            ridePoints.setRideId(rideId);
//            if(!isPaused){
//                ridePoints.setSubRideId(subrideId);
//                ridePoints.setPaused(true);
//            }else{
                ridePoints.setSubRideId(subrideId);
                ridePoints.setPaused(true);
          //  }

            ridePointsList.add(ridePoints);

            LatLongPoints latLongPoints=new LatLongPoints();
            latLongPoints.setLatitude(mLastLocation.getLatitude());
            latLongPoints.setLongitude(mLastLocation.getLongitude());
            pausedPoints.add(latLongPoints);
            gmap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).icon(icon));

            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), 18));
        }else{
            subrideId++;
            ivPauseRide.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_ride));
            isPaused=false;
            isPause=true;

        }

     //
      //  lastKnownLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

//        if(mLastLocation!=null){
//            endLat=String.valueOf(mLastLocation.getLatitude());
//            endLong=String.valueOf(mLastLocation.getLongitude());
//        }
//        saveRideRequest=new SaveRideRequest();
//        saveRideRequest.setCycleId(cycleId);
//        saveRideRequest.setDescription("");
//        saveRideRequest.setEndGeoLat(endLat);
//        saveRideRequest.setEndGeoLong(endLong);
//        saveRideRequest.setOdometerReading(0);
//        saveRideRequest.setName("");
//        saveRideRequest.setOwnerId(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
//        saveRideRequest.setRideId(rideId);
//        saveRideRequest.setStartGeoLat(startLat);
//        saveRideRequest.setStartGeoLon(startLong);
//        saveRideRequest.setIsRide("2");
//        saveRideRequest.setRideTypeId(1);
//        saveRideRequest.setRideEnded(false);
//        saveRideRequest.setRideSaved(false);
//        saveRideRequest.setRideLog(ridePointsList);
//
//        mPresenter.saveRideRequest(saveRideRequest);

    }

    @Override
    public void onCycleDetailsSucess(String rided,ArrayList<SaveRideresponse> pathsList) {
//        if(isPause){
//
//            AndroidUtil.showSnackBarSafe(this,"Ride Paused");
//        }else{
            Intent intent=new Intent(this,RideSummaryActivity.class);
            intent.putExtra("cycleId",cycleId);
            intent.putExtra("rideId",rideId);
            intent.putExtra("startLat",startLat);
            intent.putExtra("startLong",startLong);
            intent.putExtra("endLat",endLat);
            intent.putExtra("endLong",endLong);
            intent.putExtra("usersPoints",usersCurent);
            intent.putExtra("pausedPoints",pausedPoints);
            intent.putExtra("pathData",pathsList);
            intent.putExtra("odometer",odometerReading);
            startActivity(intent);
            finish();
           // ActivityUtils.startActivityFinish(this,RideSummaryActivity.class,bundle);
        //}

    }
}
