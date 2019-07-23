package com.warrous.ready2ride.tracksearch.restaurants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.maptest.PlaceJSONParser;
import com.warrous.ready2ride.tracksearch.DirectionsActivityR;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RestaurantActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, OnMapReadyCallback ,RestaurantAdapter.OnItemClickListener {

    private MapView mapView;
    private GoogleMap gmap;
    RestaurantAdapter findDealerAdapter;
   // TrackSEarchContract.Presenter mPresenter;
    LinearLayoutManager memberLinearLayoutManager;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    @BindView(R.id.et_search)
    EditText etSearch;

    ArrayList<RestaurantResponse> restaurantResponses;


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
        setContentView(R.layout.activity_restaurants);
        injectButterKnife(this);
        restaurantResponses = new ArrayList<>();
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
        memberLinearLayoutManager = new LinearLayoutManager(getContext());
        findDealerAdapter = new RestaurantAdapter(this, this, null);
        //mPresenter=new TrackSearchPresenter(this);
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

        if (stringLatitude != null) {

        }
        if (stringLongitude != null) {

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
                AndroidUtil.hideKeyBoard(RestaurantActivity.this, etSearch);
                filter(etSearch.getText().toString());
            }
        });

//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                gmap.clear();
//                gmap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
//                gmap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
//                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
//            }
//
//            @Override
//            public void onError(Status status) {
//
//            }
//        });
    }

    @Override
    public void onItemClick(View view, int position, RestaurantResponse dealershipResponse) {
        Bundle bundle=new Bundle();
        bundle.putDouble("startLat",mLastLocation.getLatitude());
        bundle.putDouble("startLong",mLastLocation.getLongitude());
        bundle.putDouble("EndLat",dealershipResponse.getLatitude());
        bundle.putDouble("EndLong",dealershipResponse.getLongitude());
        ActivityUtils.startActivity(this,DirectionsActivityR.class,bundle);
    }

    /** A class, to download Google Places */
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }

    }

    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);


            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){

            // Clears all the existing markers
            gmap.clear();

            restaurantResponses=new ArrayList<>();



            for(int i=0;i<list.size();i++){

                RestaurantResponse restaurantResponse= new RestaurantResponse();

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);

                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                String name = hmPlace.get("place_name");

                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");

                restaurantResponse.setLatitude(lat);
                restaurantResponse.setLongitude(lng);
                restaurantResponse.setRestaurantName(name);
                restaurantResponse.setAddress(vicinity);
                restaurantResponses.add(restaurantResponse);

//                LatLng latLng = new LatLng(lat, lng);
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                //This will be displayed on taping the marker
//                markerOptions.title(name + " : " + vicinity);
//
//                // Placing a marker on the touched position
//                gmap.addMarker(markerOptions);

            }
            setMarkers(restaurantResponses);

        }

    }

    public void setMarkers(ArrayList<RestaurantResponse> dealershipResponses) {
        for (int i = 0; i < dealershipResponses.size(); i++) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_restrnt_pointer);

            gmap.addMarker(new MarkerOptions()
                    .position(new LatLng(dealershipResponses.get(i).getLatitude(),dealershipResponses.get(i).getLongitude()))
                    .title(dealershipResponses.get(i).getRestaurantName()))
                    .setIcon(icon);
        }
        setRecyclerView(dealershipResponses);
//
// gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(dealershipResponses.get(0).getDealerLatitude()), Double.parseDouble(dealershipResponses.get(0).getDealerLongitude())), 10));
        }

        public void setRecyclerView(ArrayList<RestaurantResponse> restaurantResponsesL){

            if(restaurantResponsesL.size()!=0){
                // setMarkers(rideLogDetails);
                tvNoDealerships.setVisibility(View.GONE);
                rvDealerShipItems.setVisibility(View.VISIBLE);
                memberLinearLayoutManager = new LinearLayoutManager(getContext());
                rvDealerShipItems.setLayoutManager(memberLinearLayoutManager);
                findDealerAdapter = new RestaurantAdapter(this, this, restaurantResponsesL);
                rvDealerShipItems.setAdapter(findDealerAdapter);
            }else{
                tvNoDealerships.setVisibility(View.VISIBLE);
                rvDealerShipItems.setVisibility(View.GONE);
            }

        }



    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<RestaurantResponse> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (int i = 0; i < restaurantResponses.size(); i++) {
            //if the existing elements contains the search input
            if (restaurantResponses.get(i).getRestaurantName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(restaurantResponses.get(i));
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
    public void onClickBack() {
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
                                ActivityCompat.requestPermissions(RestaurantActivity.this,
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
                           // mPresenter.getParkingDetails( mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            onGetData(String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()),"restaurant");
                            LatLng latLng = new LatLng( mLastLocation.getLatitude(), mLastLocation.getLongitude());

                            gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            gmap.animateCamera(CameraUpdateFactory.zoomTo(15));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                        } else {
                            /*if there is no last known location. Which means the device has no data for the loction currently.
                             * So we will get the current location.
                             * For this we'll implement Location Listener and override onLocationChanged*/
                            Log.i("Current Location", "No data for location found");

                            if (!mGoogleApiClient.isConnected())
                                mGoogleApiClient.connect();

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, RestaurantActivity.this);
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





        @Override
        protected void onStop () {
            mGoogleApiClient.disconnect();
            super.onStop();
        }

        @Override
        public void onConnected (@Nullable Bundle bundle){
            settingRequest();
        }

        @Override
        public void onConnectionSuspended ( int i){
            Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectionFailed (@NonNull ConnectionResult connectionResult){
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
        public void settingRequest () {
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
                                status.startResolutionForResult(RestaurantActivity.this, 1000);
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
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
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

        public void getLocation () {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    onGetData(String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()),"restaurant");
                    LatLng latLng = new LatLng( mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    gmap.animateCamera(CameraUpdateFactory.zoomTo(15));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                } else {
                    /*if there is no last known location. Which means the device has no data for the loction currently.
                     * So we will get the current location.
                     * For this we'll implement Location Listener and override onLocationChanged*/
                    Log.i("Current Location", "No data for location found");

                    if (!mGoogleApiClient.isConnected())
                        mGoogleApiClient.connect();

                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, RestaurantActivity.this);
                }
            }
        }

        /*When Location changes, this method get called. */



    public void onGetData(String mLatitude,String mLongitude,String type){
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=5000");
        sb.append("&types=" + type);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyCAEYMybNVFZGXUUfkWZXkD8s5Cu2rRTds");


        // Creating a new non-ui thread task to download Google place json data
        PlacesTask placesTask = new PlacesTask();

        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng( location.getLatitude(), location.getLongitude());

        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gmap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


}
