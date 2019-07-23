package com.warrous.ready2ride.setasdealership;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

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
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.discover.DiscoverActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SetasDealerShipActivity extends BaseActivity implements OnMapReadyCallback, SetasDealershipContract.View {


    private GoogleMap gmap;
    DealershipResponse dealershipResponse;

    @BindView(R.id.tv_dealer_name)
    TextView tvDealerName;
    @BindView(R.id.tv_adress_desc)
    TextView tvAddress;

    @BindView(R.id.tv_phone_desc)
    TextView tvPhoneNum;

    @BindView(R.id.tv_website_desc)
    TextView tvWebsite;
    SupportMapFragment mapFragment;
    SetasDealershipContract.Presenter mPresenter;
    SetDefaultDealerRequest setDefaultDealerRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_as_dealership);
        injectButterKnife(this);
        mPresenter = new SetasDealerShipPresenter(this);
        setDefaultDealerRequest = new SetDefaultDealerRequest();

        dealershipResponse = (DealershipResponse) getIntent().getSerializableExtra("dealerShip");
        tvDealerName.setText(dealershipResponse.getName());
        tvAddress.setText(dealershipResponse.getCity() + "\n" + dealershipResponse.getState() + " " + dealershipResponse.getZip());
        tvWebsite.setText(dealershipResponse.getWebSite());
        String number = dealershipResponse.getPhone().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        tvPhoneNum.setText(number);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mv_map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(dealershipResponse.getDealerLatitude()), Double.parseDouble(dealershipResponse.getDealerLongitude())))
                .title(dealershipResponse.getName()).icon(icon));
        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(Double.parseDouble(dealershipResponse.getDealerLatitude()), Double.parseDouble(dealershipResponse.getDealerLongitude())), new LatLng(Double.parseDouble(dealershipResponse.getDealerLatitude()),Double.parseDouble(dealershipResponse.getDealerLongitude())));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 17.0f));
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(dealershipResponse.getDealerLatitude()), Double.parseDouble(dealershipResponse.getDealerLongitude())), 10));
    }

    @OnClick(R.id.tv_website_desc)
    public void onClickWebsite() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(dealershipResponse.getWebSite()));
        startActivity(intent);
    }

    @OnClick(R.id.tv_phone_desc)
    public void onClickPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dealershipResponse.getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    @OnClick(R.id.btn_set_as_dealer)
    public void onClickSetasDealer(){
       setDefaultDealerRequest.setDealerId(dealershipResponse.getDealerId());
       PreferenceManager.storeValue(getContext(),PreferenceManager.KEY_DEFAUKT_DEALER_ID,dealershipResponse.getDealerId());
       setDefaultDealerRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
       mPresenter.setAsDealerShip(setDefaultDealerRequest);
    }

    @Override
    public void setDealerSucess(ArrayList<SignUpResponse> signUpResponses) {
        ActivityUtils.startActivity(this,DiscoverActivity.class,null);
    }
}
