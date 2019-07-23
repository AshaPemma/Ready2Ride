package com.warrous.ready2ride.navigation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.Utils;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.bike.DefaultBikeContract;
import com.warrous.ready2ride.bike.DefaultBikePresenter;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.bike.model.RideLogResponse;
import com.warrous.ready2ride.createbike.CreateBikeActivityGarage;
import com.warrous.ready2ride.finddealer.FindDealerActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.jsonparser.JSONParser;
import com.warrous.ready2ride.navigation.HourlyWeather;
import com.warrous.ready2ride.rides.EndRideActivity;
import com.warrous.ready2ride.rides.PathList;
import com.warrous.ready2ride.rides.SaveRideContract;
import com.warrous.ready2ride.rides.SaveRidePresenter;
import com.warrous.ready2ride.rides.SaveRideRequest;
import com.warrous.ready2ride.rides.SaveRideresponse;
import com.warrous.ready2ride.tracksearch.TrackSearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TrackFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, OnMapReadyCallback,DefaultBikeContract.View ,SaveRideContract.View {

    private MapView mapView;
    private GoogleMap gmap;
    boolean visible=false;

    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    @BindView(R.id.ll_items)
    LinearLayout llValues;
    DefaultBikeContract.Presenter mPresenter;
    SaveRideContract.Presenter rPresenter;
    int odometerReading;

    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.iv_sun)
    ImageView ivSun;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.tv_time2)
            TextView tvTime2;
    @BindView(R.id.tv_time3)
            TextView tvTime3;
    @BindView(R.id.tv_time4)
            TextView tvtime4;
    @BindView(R.id.iv_sun1)
    ImageView ivSun1;
    @BindView(R.id.iv_sun2)
            ImageView ivSun2;
    @BindView(R.id.iv_sun3)
            ImageView ivSun3;
    @BindView(R.id.iv_sun4)
            ImageView ivSun4;
    @BindView(R.id.tv_temp1)
            TextView tvTemp1;
    @BindView(R.id.tv_temp2)
            TextView tvTemp2;
    @BindView(R.id.tv_temp3)
            TextView tvTemp3;
    @BindView(R.id.tv_temp4)
            TextView tvTemp4;




    String latitude,longitude;
    private final String APPKEYWITH ="&cnt=10&mode=json&units=imperial&APPID=5a3c099d390d1f4f986412264d3da193";

    int cycleId,rideId;



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.activity_tracking_hide, container, false);
        injectView(view);
        mPresenter=new DefaultBikePresenter(this);
        rPresenter=new SaveRidePresenter(this);

        mPresenter.getCycleDetails(0,PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(getContext(), "Not Connected!", Toast.LENGTH_SHORT).show();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

//        mapView = view.findViewById(R.id.map_view);
//
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);

        llValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llValues.setVisibility(View.GONE);
            }
        });
        return view;
        }

        @OnClick(R.id.et_search)
    public void onClickSearch(){
        ActivityUtils.startActivityFinish(getActivity(),TrackSearchActivity.class,null);

        }
        @OnClick(R.id.btn_trackRide)
        public void onClickTrackRide(){

            if (cycleId >0) {
                SaveRideRequest saveRideRequest=new SaveRideRequest();
                saveRideRequest.setCycleId(cycleId);
                saveRideRequest.setDescription("");
                saveRideRequest.setEndGeoLat("");
                saveRideRequest.setEndGeoLong("");
                saveRideRequest.setOdometerReading(0);
                saveRideRequest.setName("");
                saveRideRequest.setOwnerId(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
                saveRideRequest.setRideId(0);
                saveRideRequest.setStartGeoLat(latitude);
                saveRideRequest.setStartGeoLon(longitude);
                saveRideRequest.setIsRide("2");
                saveRideRequest.setRideTypeId(1);
                saveRideRequest.setRideSaved(false);
                saveRideRequest.setRideEnded(false);


                rPresenter.saveRideRequest(saveRideRequest);
            }else{
                String message="If you want to start a Ride atleast Create a Bike";
                AlertUtil.showSaveAlert(LayoutInflater.from(getContext()).inflate(R.layout.dialog_save,null), message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startActivity(getActivity(),CreateBikeActivityGarage.class,null);
                    }
                });
            }


         //   ActivityUtils.startActivity(getAppActivity(),EndRideActivity.class,null);
        }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(37.3092293, -122.1136845))
//                .title("Apple").icon(icon));
//
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }


    @Override
    public void onPause() {
        super.onPause();
      //  stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
          //  startLocationUpdates();
            getLocation();
        }
    }
    class AttemptGetWeather extends AsyncTask<String, String, String> {

        String requestJson = "";

        boolean issuccess = false;
        boolean failure = false;
        ProgressDialog pDialog;

        String errorMsg = "";
        String resultVal = "";

        private String cityName = "", countryName = "";
        private String weatherURL = "http://api.openweathermap.org/data/2.5/forecast?";

        private List<HourlyWeather> hourlyWeatherList = new ArrayList<>();

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Processing...");
            // pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected String doInBackground(String... args) {

            try {

                JSONObject jsonp = new JSONObject();

                String finalURL = weatherURL + "lat=" + latitude + "&lon=" + longitude
                        + APPKEYWITH;

                Log.e("final url  ", "" + finalURL);

                JSONObject json = new JSONParser().makeHttpRequest(finalURL,
                        "GET", jsonp);

                Log.e("response ", "" + json);

                if (json != null) {

                    cityName = ""
                            + json.getJSONObject("city").getString("name");
                    countryName = ""
                            + json.getJSONObject("city").getString("country");
                    // searchEditText.setText(""+cityName+", "+countryName);

                    JSONArray jsonArrayList = json.getJSONArray("list");

                    for (int i = 0; i < jsonArrayList.length(); i++) {
                        HourlyWeather hourlyWeather = new HourlyWeather();

                        JSONObject jsonList_WEATHER = jsonArrayList
                                .getJSONObject(i).getJSONArray("weather")
                                .getJSONObject(0);

                        hourlyWeather.setWeather_icon(jsonList_WEATHER
                                .getString("icon"));

                        hourlyWeather.setClouds_all(jsonArrayList
                                .getJSONObject(i).getJSONObject("clouds")
                                .getDouble("all"));
                        hourlyWeather.setWind_speed(jsonArrayList
                                .getJSONObject(i).getJSONObject("wind")
                                .getDouble("speed"));
                        hourlyWeather.setMain_temp(jsonArrayList
                                .getJSONObject(i).getJSONObject("main")
                                .getDouble("temp"));

                        hourlyWeatherList.add(hourlyWeather);

                    }

                    issuccess = true;

                }// json null check condition
                else {
                    failure = true;
                    Log.d("Json Null!", "starting");
                }

            } catch (JSONException e) {
                e.printStackTrace();
                failure = true;
                Log.d("Json Exception !", "starting");
            } catch (Exception e) {
                e.printStackTrace();
                failure = true;
                Log.d("Exception !", "starting");
            }

            return null;
        }

        // After completing background task Dismiss the progress dialog

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (failure) {
                if (errorMsg.length() > 1) {
                    Utils.setToast(getContext(), errorMsg);
                } else {

                    Utils.setToast(getContext(), "failure.....EXCEPTION------1");
                }
            } else if (issuccess) {
                try {
                    Log.e("JSON--", "Success----" + resultVal);

                  //  searchEditText.setText("" + cityName + ", " + countryName);

                    int currentHourVal = Calendar.getInstance().get(
                            Calendar.HOUR_OF_DAY);
                    int hourList[] = {3, 6, 9, 12, 15, 18, 21, 24};
                    int pos = 0;
                    for (int i = 0; i < hourList.length; i++) {
                        if (hourList[i] > currentHourVal) {
                            pos = i;
                            break;
                        }

                    }
                    pos = pos - 1;
                    if (pos < 0) {
                        pos = 8 + pos;
                    }
                    // SetActionBar.setToast(context,""+pos);

                    // private TextView weather1_dayTextView,
                    // weather1_TempTextView, weather1_RainTextView,
                    // weather1_WindTextView;
                    // private ImageView weather1_ImgImageView;

                    tvTime.setText(""
                            + (getHourName(hourList[(pos + 0) % 8])) + " "
                            + getAMPM(hourList[(pos + 0) % 8]));
                    ivSun.setImageResource(getWeatherIcon(""
                            + hourlyWeatherList.get(0).getWeather_icon()));
                    tvTemp.setText(""
                            + Math.round(hourlyWeatherList.get(0)
                            .getMain_temp()) + (char) 0x00B0);
//                    weather1_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(0)
//                            .getClouds_all()) + " %");
//                    weather1_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(0).getWind_speed()
//                                    + " mph");
//
                    tvTemp1.setText(""
                            + (getHourName(hourList[(pos + 1) % 8])) + " "
                            + getAMPM(hourList[(pos + 1) % 8]));
                    ivSun1.setImageResource(getWeatherIcon(""
                            + hourlyWeatherList.get(1).getWeather_icon()));
                    tvTemp1.setText(""
                            + Math.round(hourlyWeatherList.get(1)
                            .getMain_temp()) + (char) 0x00B0);
//                    weather2_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(1)
//                            .getClouds_all()) + " %");
//                    weather2_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(1).getWind_speed()
//                                    + " mph");
//
                    tvTime2.setText(""
                            + (getHourName(hourList[(pos + 2) % 8])) + " "
                            + getAMPM(hourList[(pos + 2) % 8]));
                    ivSun2.setImageResource(getWeatherIcon(""
                            + hourlyWeatherList.get(2).getWeather_icon()));
                    tvTemp2.setText(""
                            + Math.round(hourlyWeatherList.get(2)
                            .getMain_temp()) + (char) 0x00B0);
//                    weather3_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(2)
//                            .getClouds_all()) + " %");
//                    weather3_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(2).getWind_speed()
//                                    + " mph");
//
                    tvTime3.setText(""
                            + (getHourName(hourList[(pos + 3) % 8])) + " "
                            + getAMPM(hourList[(pos + 3) % 8]));
                    ivSun3.setImageResource(getWeatherIcon(""
                            + hourlyWeatherList.get(3).getWeather_icon()));
                    tvTemp3.setText(""
                            + Math.round(hourlyWeatherList.get(3)
                            .getMain_temp()) + (char) 0x00B0);
//                    weather4_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(3)
//                            .getClouds_all()) + " %");
//                    weather4_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(3).getWind_speed()
//                                    + " mph");
//
                    tvtime4.setText(""
                            + (getHourName(hourList[(pos + 4) % 8])) + " "
                            + getAMPM(hourList[(pos + 4) % 8]));
                    ivSun4.setImageResource(getWeatherIcon(""
                            + hourlyWeatherList.get(4).getWeather_icon()));
                    tvTemp4.setText(""
                            + Math.round(hourlyWeatherList.get(4)
                            .getMain_temp()) + (char) 0x00B0);
//                    weather5_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(4)
//                            .getClouds_all()) + " %");
//                    weather5_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(4).getWind_speed()
//                                    + " mph");
//
//                    weather6_dayTextView.setText(""
//                            + (getHourName(hourList[(pos + 5) % 8])) + " "
//                            + getAMPM(hourList[(pos + 5) % 8]));
//                    weather6_ImgImageView.setImageResource(getWeatherIcon(""
//                            + hourlyWeatherList.get(5).getWeather_icon()));
//                    weather6_TempTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(5)
//                            .getMain_temp()) + (char) 0x00B0);
//                    weather6_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(5)
//                            .getClouds_all()) + " %");
//                    weather6_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(5).getWind_speed()
//                                    + " mph");
//
//                    weather7_dayTextView.setText(""
//                            + (getHourName(hourList[(pos + 6) % 8])) + " "
//                            + getAMPM(hourList[(pos + 6) % 8]));
//                    weather7_ImgImageView.setImageResource(getWeatherIcon(""
//                            + hourlyWeatherList.get(6).getWeather_icon()));
//                    weather7_TempTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(6)
//                            .getMain_temp()) + (char) 0x00B0);
//                    weather7_RainTextView.setText(""
//                            + Math.round(hourlyWeatherList.get(6)
//                            .getClouds_all()) + " %");
//                    weather7_WindTextView
//                            .setText(""
//                                    + hourlyWeatherList.get(6).getWind_speed()
//                                    + " mph");

                } catch (Exception e) {

                    Utils.setToast(getContext(), "exception.....in success");
                }

            }

        }

    }
    private String getHourName(int temp) {
        int i = temp % 12;
        if (i == 0)
            return "" + 12;
        else
            return "" + i;
    }

    private String getAMPM(int i) {
        if (i > 12)
            return "pm";
        else
            return "am";
    }

    private int getWeatherIcon(String weatherIconValue) {
        if (weatherIconValue.equalsIgnoreCase("01d"))
            return R.raw.weather_01d_small;
        else if (weatherIconValue.equalsIgnoreCase("01n"))
            return R.raw.weather_01n_small;
        else if (weatherIconValue.equalsIgnoreCase("02d"))
            return R.raw.weather_02d_small;
        else if (weatherIconValue.equalsIgnoreCase("02n"))
            return R.raw.weather_02n_small;
        else if (weatherIconValue.equalsIgnoreCase("03d"))
            return R.raw.weather_03d_small;
        else if (weatherIconValue.equalsIgnoreCase("03n"))
            return R.raw.weather_03n_small;
        else if (weatherIconValue.equalsIgnoreCase("04d"))
            return R.raw.weather_04d_small;
        else if (weatherIconValue.equalsIgnoreCase("04n"))
            return R.raw.weather_04n_small;
        else if (weatherIconValue.equalsIgnoreCase("09d"))
            return R.raw.weather_09d_small;
        else if (weatherIconValue.equalsIgnoreCase("09n"))
            return R.raw.weather_09n_small;
        else if (weatherIconValue.equalsIgnoreCase("10d"))
            return R.raw.weather_10d_small;
        else if (weatherIconValue.equalsIgnoreCase("10n"))
            return R.raw.weather_10n_small;
        else if (weatherIconValue.equalsIgnoreCase("11d"))
            return R.raw.weather_11d_small;
        else if (weatherIconValue.equalsIgnoreCase("11n"))
            return R.raw.weather_11n_small;
        else if (weatherIconValue.equalsIgnoreCase("13d"))
            return R.raw.weather_13d_small;
        else if (weatherIconValue.equalsIgnoreCase("13n"))
            return R.raw.weather_13n_small;
        else if (weatherIconValue.equalsIgnoreCase("50d"))
            return R.raw.weather_50d_small;
        else if (weatherIconValue.equalsIgnoreCase("50n"))
            return R.raw.weather_50n_small;
        else
            return R.raw.weather_04n_small;
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location")
                        .setMessage("Please provide acess to ready2ride to get current Location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);

                        if (mLastLocation != null) {
                            //_progressBar.setVisibility(View.INVISIBLE);
                            latitude=String.valueOf(mLastLocation.getLatitude());
                            longitude=String.valueOf(mLastLocation.getLongitude());
                            new AttemptGetWeather().execute();
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_oval_blue);
                           // BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_oval_blue);
                            LatLngBounds AUSTRALIA = new LatLngBounds(
                                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                            gmap.addMarker(new MarkerOptions()
                                    .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).icon(icon));
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
                            //gmap.animateCamera(CameraUpdateFactory.zoomOut());
//                            gmap.addMarker(new MarkerOptions()
//                                    .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).icon(icon));
//
//                            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), 15));////

                            // animating with a duration of 2 seconds.
                            //gmap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                            //              _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                        } else {
                            /*if there is no last known location. Which means the device has no data for the loction currently.
                             * So we will get the current location.
                             * For this we'll implement Location Listener and override onLocationChanged*/
                            Log.i("Current Location", "No data for location found");

                            if (!mGoogleApiClient.isConnected())
                                mGoogleApiClient.connect();

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
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
    @OnClick(R.id.iv_menu)
    public void onClickMenu(){
        if(visible==false){
            visible=true;
            llValues.setVisibility(View.VISIBLE);
        }else {
            visible=false;
            llValues.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStop() {
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), 90000);
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
                            status.startResolutionForResult(getActivity(), 1000);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        Toast.makeText(getContext(), "Location Service not Enabled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                latitude=String.valueOf(mLastLocation.getLatitude());
                longitude=String.valueOf(mLastLocation.getLongitude());
                new AttemptGetWeather().execute();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_oval_blue);
                LatLngBounds AUSTRALIA = new LatLngBounds(
                        new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                gmap.addMarker(new MarkerOptions()
                        .position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude())).icon(icon));
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));

//                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), 15));////

                // animating with a duration of 2 seconds.
               // gmap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                //               _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
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

    @Override
    public void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses) {

        if(segmentResponses!=null){
            if(segmentResponses.size()!=0){
                cycleId=segmentResponses.get(0).getCycleId();
                odometerReading=segmentResponses.get(0).getOdometerReading();
            }

        }

    }

    @Override
    public void onGetRideLogResponse(ArrayList<RideList> segmentResponses) {

    }

    @Override
    public void onCycleDetailsSucess(String rideId, ArrayList<SaveRideresponse> pathslists) {

        if (cycleId >0) {
            Bundle bundle=new Bundle();
            bundle.putInt("cycleId",cycleId);
            bundle.putString("rideId",rideId);
            bundle.putString("startLat",latitude);
            bundle.putString("startlong",longitude);
            bundle.putInt("odometerReading",odometerReading);

            ActivityUtils.startActivity(getActivity(),EndRideActivity.class,bundle);
        }else{
            String message="If you want to start a Ride atleast Create a Bike";
            AlertUtil.showSaveAlert(llValues, message, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.startActivity(getActivity(),CreateBikeActivityGarage.class,null);
                }
            });
        }



    }
}
