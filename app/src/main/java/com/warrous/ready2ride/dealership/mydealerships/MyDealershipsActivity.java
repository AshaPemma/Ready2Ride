package com.warrous.ready2ride.dealership.mydealerships;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.DealerShipContract;
import com.warrous.ready2ride.dealership.DealershipPresenter;
import com.warrous.ready2ride.dealership.SelectedItemsAdapter;
import com.warrous.ready2ride.dealership.adapter.MyDealershipsAdapter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.popup.Popup;
import com.warrous.ready2ride.framework.AlertUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.setasdealership.SetDefaultDealerRequest;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyDealershipsActivity extends BaseActivity implements MyDealershipsContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener,MyDealershipsAdapter.OnItemClickListener {
    @BindView(R.id.rv_my_dealerships)
    RecyclerView rvMyDealerships;
    @BindView(R.id.btn_find_dealershp)
    Button btnDealer;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    MyDealershipsAdapter myDealershipsAdapter;
    SelectedItemsAdapter selectedItemsAdapter;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ArrayList<DealershipResponse> selecteddealershipResponses;
    @BindView(R.id.rv_selected_dealerships)
    RecyclerView rvSelectedDealers;
    Popup popup;
    MyDealershipsContract.Presenter mDPresenter;
    double lat, lon;
    ArrayList<DealershipResponse> selectedDealers;
    @BindView(R.id.tv_my_dealers)
    TextView tvMyDealers;



    SwipeController swipeController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dealerships);
        injectButterKnife(this);

//        SwipeController swipeController = new SwipeController();
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(rvSelectedDealers);

        tvMyDealers.setVisibility(View.GONE);
        rvMyDealerships.setVisibility(View.GONE);
        selecteddealershipResponses = new ArrayList<>();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

        rvMyDealerships.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myDealershipsAdapter = new MyDealershipsAdapter(this, this, null);
        rvMyDealerships.setAdapter(myDealershipsAdapter);

        rvSelectedDealers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        selectedItemsAdapter = new SelectedItemsAdapter(this, null, null);
        rvSelectedDealers.setAdapter(selectedItemsAdapter);


        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(final int position) {
                String message = null;
                DealershipResponse dealershipResponse=selectedDealers.get(position);
                String dealerName=dealershipResponse.getName();
                if(position==0){
                     message = "Do you want to remove "+dealerName+" as your Default Dealer";
                }else{
                    message = "Do you want to remove "+dealerName+" from your list of dealerships";
                }

                        AlertUtil.showSaveAlert(LayoutInflater.from(getContext()).inflate(R.layout.dialog_save, null),
                                message, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       removeDealer(selectedDealers.get(position));
                                    }
                                });


//                mAdapter.players.remove(position);
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rvSelectedDealers);

        rvSelectedDealers.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });




    mDPresenter=new MyDealershipsPresenter(this);
        mDPresenter.getSelectedDealers(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));

       // mPresenter.getDealerShips(0,0,0);

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

    @OnClick(R.id.btn_find_dealershp)
    public void onClickFindDealer(){
        tvMyDealers.setVisibility(View.VISIBLE);
        rvMyDealerships.setVisibility(View.VISIBLE);
    }
    @Override
    public void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses) {

        rvMyDealerships.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myDealershipsAdapter=new MyDealershipsAdapter(this,this,segmentResponses);
        rvMyDealerships.setAdapter(myDealershipsAdapter);
    }

    @Override
    public void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponses) {
        selectedDealers=new ArrayList<>();
        selectedDealers=dealershipResponses;
        rvSelectedDealers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        selectedItemsAdapter=new SelectedItemsAdapter(this,null,dealershipResponses);
        rvSelectedDealers.setAdapter(selectedItemsAdapter);
    }

    @Override
    public void onRemoveasDealerSucess(ArrayList<SignUpResponse> signUpResponses) {
        Toast.makeText(this,"Sucess",Toast.LENGTH_LONG).show();
        mDPresenter.getSelectedDealers(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        mDPresenter.getDealerShips(0,lat,lon,PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
    }

    @Override
    public void onSetDealerSucess(ArrayList<SignUpResponse> signUpResponses) {
        mDPresenter.getSelectedDealers(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
    }

    @Override
    public void setSelectedDealerSucess() {
        mDPresenter.getSelectedDealers(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        mDPresenter.getDealerShips(0,lat,lon,PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));


    }







    public void setSelectedDealerships(ArrayList<DealershipResponse> dealerships){

        for(int i=0;i<dealerships.size();i++){
            if(dealerships.get(i).isDefault()){
                selecteddealershipResponses.add(dealerships.get(i));
            }
            }

        rvSelectedDealers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        selectedItemsAdapter=new SelectedItemsAdapter(this,null,selecteddealershipResponses);
        rvSelectedDealers.setAdapter(selectedItemsAdapter);
    }


    public void removeDealer(DealershipResponse dealershipResponse){

        mDPresenter.removeAsSelectedDealer(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID),dealershipResponse.getDealerId());

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showDealersList(DealershipResponse dealershipResponse){
        SetDefaultDealerRequest setDefaultDealerRequest=new SetDefaultDealerRequest();
        setDefaultDealerRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        setDefaultDealerRequest.setDealerId(dealershipResponse.getDealerId());
        PreferenceManager.storeValue(getContext(),PreferenceManager.KEY_DEFAUKT_DEALER_ID,dealershipResponse.getDealerId());

        mDPresenter.setasdefaultDealer(setDefaultDealerRequest);
        //        popup = new Popup(this, MyDealershipsActivity.this);
//        popup.setActivity(this);
//        showPopup(btnDealer);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showPopup(View v) {
        popup.show(v);

    }

    public void addasFavouriteDealer(DealershipResponse dealershipResponse){
        PreferenceManager.storeValue(getContext(),PreferenceManager.KEY_DEFAUKT_DEALER_ID,dealershipResponse.getDealerId());
        mDPresenter.setasSelectedDealer(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID),dealershipResponse.getDealerId());

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
                                ActivityCompat.requestPermissions(MyDealershipsActivity.this,
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
                            lat=mLastLocation.getLatitude();
                            lon=mLastLocation.getLongitude();

                            //_progressBar.setVisibility(View.INVISIBLE);
                            mDPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                        } else {
                            /*if there is no last known location. Which means the device has no data for the loction currently.
                             * So we will get the current location.
                             * For this we'll implement Location Listener and override onLocationChanged*/
                            Log.i("Current Location", "No data for location found");

                            if (!mGoogleApiClient.isConnected())
                                mGoogleApiClient.connect();

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MyDealershipsActivity.this);
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
                            status.startResolutionForResult(MyDealershipsActivity.this, 1000);
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
                mDPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//                _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
//                _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MyDealershipsActivity.this);
            }
        }
    }

    /*When Location changes, this method get called. */
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //  _progressBar.setVisibility(View.INVISIBLE);
       // mPresenter.getDealerShips(0,mLastLocation.getLatitude(),mLastLocation.getLongitude(),PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));

    }

//    @Override
//    public void valueChanged(DealershipResponse dealershipResponse) {
//
//        SetDefaultDealerRequest setDefaultDealerRequest=new SetDefaultDealerRequest();
//        setDefaultDealerRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//        setDefaultDealerRequest.setDealerId(dealershipResponse.getDealerId());
//        PreferenceManager.storeValue(getContext(),PreferenceManager.KEY_DEFAUKT_DEALER_ID,dealershipResponse.getDealerId());
//
//        mDPresenter.setasdefaultDealer(setDefaultDealerRequest);
//        }

        public void changeDefaultDealer(DealershipResponse dealershipResponse){
            SetDefaultDealerRequest setDefaultDealerRequest=new SetDefaultDealerRequest();
            setDefaultDealerRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
            setDefaultDealerRequest.setDealerId(dealershipResponse.getDealerId());
            PreferenceManager.storeValue(getContext(),PreferenceManager.KEY_DEFAUKT_DEALER_ID,dealershipResponse.getDealerId());
            mDPresenter.setasdefaultDealer(setDefaultDealerRequest);
        }

    @Override
    public void onItemClick(View view, final int position, final DealershipResponse dealershipResponse) {
        String dealerName=dealershipResponse.getName();
        String message = "Do you want to select "+dealerName+" as Default Dealer";
        AlertUtil.showSaveAlert(LayoutInflater.from(getContext()).inflate(R.layout.dialog_save, null),
                message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int dealerPos=position;
                       addasFavouriteDealer(dealershipResponse);

                    }
                });

    }
}
