package com.warrous.ready2ride.navigation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.createbike.CreateBikeActivity;
import com.warrous.ready2ride.framework.ActivityUtils;

import butterknife.OnClick;

public class BikeFragment extends BaseFragment {

    public BikeFragment() {
    }
    public static BikeFragment getInstance() {
        BikeFragment fragment = new BikeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_bike, container, false);
        injectView(view);
        return view;
    }
    @OnClick(R.id.btn_create_bike)
    public void onClickButtonBike(){
        ActivityUtils.startActivity(getAppActivity(),CreateBikeActivity.class,null);
    }
}
