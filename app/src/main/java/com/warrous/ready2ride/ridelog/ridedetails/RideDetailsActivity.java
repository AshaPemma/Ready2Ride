package com.warrous.ready2ride.ridelog.ridedetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.ridelog.RideLogListContarct;
import com.warrous.ready2ride.ridelog.RideLogListPresenter;
import com.warrous.ready2ride.ridelog.RidePath;
import com.warrous.ready2ride.rides.SaveRideresponse;
import com.warrous.ready2ride.rides.adapter.RideSummaryAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RideDetailsActivity extends BaseActivity implements   OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,RideLogListContarct.View {
    SupportMapFragment mapFragment;
    RideList rideList;
    GoogleMap gmap;
    GoogleApiClient mGoogleApiClient;
    @BindView(R.id.rv_ride_log)
    RecyclerView rvRideLog;
    RideSummaryAdapter rideSummaryAdapter;
    private Polyline gpsTrack;
    int rideId;

    ArrayList<RideLogDetail> rideLog;
    ArrayList<RidePath> ridePath;
    ArrayList<SaveRideresponse> saveRideresponses;

    RideLogListContarct.Presenter mPresenter;

    ArrayList<RidePath> pausedPoints;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_ride_log_details);
        injectButterKnife(this);
        mPresenter=new RideLogListPresenter(this);

        rideLog=new ArrayList<>();
        ridePath=new ArrayList<>();
        saveRideresponses=new ArrayList<>();
        pausedPoints=new ArrayList<>();

        rvRideLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rideSummaryAdapter = new RideSummaryAdapter(this, null,null);
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

        rideList = (RideList) getIntent().getSerializableExtra("rideList");
        rideId=rideList.getRideId();

        mPresenter.getRidePath(rideId);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

    private void updateTrack() {
        List<LatLng> points = gpsTrack.getPoints();
//        String startLat,startLong,endLat,endLong;
//        Double startdLa = null,startLo = null,endLo=null,enddLa = null;
//        if(!TextUtils.isEmpty(rideList.getStart_geo_lat())){
//             startLat =rideList.getStart_geo_lat();
//             startdLa=Double.parseDouble(startLat);
//        }
//        if(!TextUtils.isEmpty(rideList.getStart_geo_lon())){
//            startLong=rideList.getStart_geo_lon();
//             startLo=Double.parseDouble(startLong);
        for(int i=0;i<ridePath.size();i++){
            LatLng startGeo= new LatLng(ridePath.get(i).getLatitude(),ridePath.get(i).getLongitude());
            points.add(startGeo);
        }

//        if(!TextUtils.isEmpty(rideList.getEnd_geo_lat())){
//            endLat =rideList.getEnd_geo_lat();
//            enddLa=Double.parseDouble(endLat);
//        }
//        if(!TextUtils.isEmpty(rideList.getEnd_geo_long())){
//            endLong=rideList.getEnd_geo_long();
//            endLo=Double.parseDouble(endLong);
//            LatLng endGeo= new LatLng(enddLa,endLo);
//            points.add(endGeo);
//        }
        gpsTrack.setPoints(points);
        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(ridePath.get(0).getLatitude(), ridePath.get(0).getLongitude()), new LatLng(ridePath.get(0).getLatitude(), ridePath.get(0).getLongitude()));
        //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), 15));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
   //     gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ridePath.get(0).getLatitude(),ridePath.get(0).getLongitude()), 15));////                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));



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

    @Override
    public void ongetRidePathSucess(ArrayList<RidePath> ridePaths) {

        ridePath=ridePaths;
        if(ridePath!=null){
          if( ridePath.size()>0) {
                updateTrack();
            }
        }
        mPresenter.getRideLogDetails(rideId);

        if(ridePath!=null){
            if( ridePath.size()>0) {
                for(int i=0;i<ridePath.size();i++){
                    if(ridePath.get(i).isPaused()) {
                        pausedPoints.add(ridePath.get(i));
                    }
                }
            }
            }
            addMarkers();


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
                        new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()));
                //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), 15));
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
               // gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pausedPoints.get(0).getLatitude(), pausedPoints.get(0).getLongitude()), 15));

            }
        }


    }


    @Override
    public void ongetRideLogSucess(ArrayList<RideLogDetail> rideLogDetails) {

        saveRideresponses=new ArrayList<>();
        if(rideLogDetails!=null){
            if(rideLogDetails.size()>0){

                for(int i=0;i<rideLogDetails.size();i++){
                    SaveRideresponse saveRideresponse= new SaveRideresponse();
                    saveRideresponse.setDistance(rideLogDetails.get(i).getDistance());
                    saveRideresponse.setColor(rideLogDetails.get(i).getColor());
                    saveRideresponse.setSpeed(rideLogDetails.get(i).getSpeed());
                    saveRideresponse.setTime(rideLogDetails.get(i).getTime());
                   saveRideresponses.add(saveRideresponse);
                }
                rvRideLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                rideSummaryAdapter = new RideSummaryAdapter(this, null,saveRideresponses);
                rvRideLog.setAdapter(rideSummaryAdapter);

            }

        }




    }
}
