package com.warrous.ready2ride.rides;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.rides.adapter.RideSummaryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RideSummaryActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, SaveRideContract.View {

    SupportMapFragment mapFragment;
    @BindView(R.id.rv_ride_log)
    RecyclerView rvRideLog;
    RideSummaryAdapter rideSummaryAdapter;
    int cycleId, rideId;
    String startLat = "", startLong = "", endLat = "", endLong = "";
    String rideName;
    SaveRideContract.Presenter mPresenter;
    boolean isOdometer = false;
    GoogleMap gmap;
    GoogleApiClient mGoogleApiClient;
    private Polyline gpsTrack;
    ArrayList<LatLongPoints> latLongPoints;
    ArrayList<LatLongPoints> pausedPoints;
    ArrayList<SaveRideresponse> saveRideresponses;
    double odometerRead;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_summary);
        injectButterKnife(this);
        mPresenter = new SaveRidePresenter(this);
        pausedPoints = new ArrayList<>();
        saveRideresponses = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cycleId = extras.getInt("cycleId");
            rideId = extras.getInt("rideId");
            startLat = extras.getString("startLat");
            startLong = extras.getString("startLong");
            endLat = extras.getString("endLat");
            endLong = extras.getString("endLong");
            odometerRead=extras.getDouble("odometer");

            latLongPoints = (ArrayList<LatLongPoints>) getIntent().getSerializableExtra("usersPoints");
            pausedPoints = (ArrayList<LatLongPoints>) getIntent().getSerializableExtra("pausedPoints");
            saveRideresponses = (ArrayList<SaveRideresponse>) getIntent().getSerializableExtra("pathData");
//            if(pathsLists!=null){
//                if(pathsLists.size()>0){
//                    for(int i=0;i<pathsLists.size();i++){
//                        if(pathsLists.get(i).isPaused()){
//                            pausedPoints.add(pathsLists.get(i));
//                        }
//                    }
//                }
//            }


            // latLongPoints=extras.getCharSequenceArrayList("")
        }
        rvRideLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rideSummaryAdapter = new RideSummaryAdapter(this, null, saveRideresponses);
        rvRideLog.setAdapter(rideSummaryAdapter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

        // rideList = (RideList) getIntent().getSerializableExtra("rideList");
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
    }


    public void addPolyLine() {
        List<LatLng> points = gpsTrack.getPoints();

        if (latLongPoints != null) {
            if (latLongPoints.size() > 0) {
                for (int i = 0; i < latLongPoints.size(); i++) {
                    LatLng latLng = new LatLng(latLongPoints.get(i).getLatitude(), latLongPoints.get(i).getLongitude());
                    points.add(latLng);
                }
                gpsTrack.setPoints(points);

                LatLngBounds AUSTRALIA = new LatLngBounds(
                        new LatLng(Double.parseDouble(startLat), Double.parseDouble(startLong)), new LatLng(Double.parseDouble(startLat),Double.parseDouble(startLong)));
                //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), 15));
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
              //   gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(startLat),Double.parseDouble(startLat)), 10));

            }

        }
    }

    public void addMarkers() {
        if (pausedPoints != null) {
            if (pausedPoints.size() > 0) {
                for (int i = 0; i < pausedPoints.size(); i++) {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pause_icon);

                    gmap.addMarker(new MarkerOptions()
                            .position(new LatLng(pausedPoints.get(i).getLatitude(), pausedPoints.get(i).getLongitude()))
                            .title("Apple").icon(icon));

                }

                LatLngBounds AUSTRALIA = new LatLngBounds(
                        new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), new LatLng(pausedPoints.get(pausedPoints.size()-1).getLatitude(), pausedPoints.get(pausedPoints.size()-1).getLongitude()));
                //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), 15));
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
            }
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(37.3092293, -122.1136845))
//                .title("Apple").icon(icon));
//
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));

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
        addPolyLine();
        addMarkers();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @OnClick(R.id.btn_save_ride)
    public void onClickSaveRide() {
        isOdometer = false;
        AlertUtil.showSaveRideDialog(this, new AlertUtil.OnSaveRideClick() {
            @Override
            public void onClick(String saveRide, AlertDialog dialog) {
                rideName = saveRide;
                dialog.dismiss();
                SaveRideRequest saveRideRequest = new SaveRideRequest();
                saveRideRequest.setCycleId(cycleId);
                saveRideRequest.setDescription("");
                saveRideRequest.setEndGeoLat(endLat);
                saveRideRequest.setEndGeoLong(endLong);
                saveRideRequest.setOdometerReading(0);
                saveRideRequest.setName(rideName);
                saveRideRequest.setOwnerId(PreferenceManager.getIntegerValue(getContext(), PreferenceManager.KEY_OWNER_ID));
                saveRideRequest.setRideId(rideId);
                saveRideRequest.setStartGeoLat(startLat);
                saveRideRequest.setStartGeoLon(startLong);
                saveRideRequest.setIsRide("2");
                saveRideRequest.setRideTypeId(1);
                saveRideRequest.setRideEnded(true);
                saveRideRequest.setRideSaved(true);
                saveRideRequest.setRideLog(new ArrayList<RidePoints>());

                mPresenter.saveRideRequest(saveRideRequest);

            }
        });
    }


    public void showDialogOdometer(AlertDialog alertDialogdialog) {
        alertDialogdialog.dismiss();
        AlertUtil.showOdometerOptDialog(this);
    }

    @OnClick(R.id.tv_discard)
    public void onClickDiscard() {
        finish();
    }

    @Override
    public void onCycleDetailsSucess(String rideid, ArrayList<SaveRideresponse> pathLists) {
        if (isOdometer) {
            AndroidUtil.showSnackBarSafe(this, "Odometer reading Updated..");
            finish();
        } else {
            AlertUtil.showRideSavedDialog(getAppActivity(), new AlertUtil.OnRideSavedClick() {
                @Override
                public void onClick(AlertDialog dialog) {
                    dialog.dismiss();
                    isOdometer = true;
                    AlertUtil.showOdometerUpdateDialog(RideSummaryActivity.this,String.valueOf(odometerRead),new AlertUtil.OnOdometerUpdtaeClick() {
                        @Override
                        public void onClick(String odometerReading, AlertDialog dialog) {
                            dialog.dismiss();

                            SaveRideRequest saveRideRequest = new SaveRideRequest();
                            saveRideRequest.setCycleId(cycleId);
                            saveRideRequest.setDescription("");
                            saveRideRequest.setEndGeoLat(endLat);
                            saveRideRequest.setEndGeoLong(endLong);
                            double or=Double.valueOf(odometerReading);
                            int value = (int)or;
                            saveRideRequest.setOdometerReading(value);
                            saveRideRequest.setName(rideName);
                            saveRideRequest.setOwnerId(PreferenceManager.getIntegerValue(getContext(), PreferenceManager.KEY_OWNER_ID));
                            saveRideRequest.setRideId(rideId);
                            saveRideRequest.setStartGeoLat(startLat);
                            saveRideRequest.setStartGeoLon(startLong);
                            saveRideRequest.setIsRide("2");
                            saveRideRequest.setRideTypeId(1);

                            mPresenter.saveRideRequest(saveRideRequest);


                        }
                    });

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
