package com.warrous.ready2ride.navigation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.finddealer.FindDealerActivity;
import com.warrous.ready2ride.framework.ActivityUtils;

import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    public HomeFragment() {
    }
    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        injectView(view);
        return view;
    }
    @OnClick(R.id.btn_link_my_dealer)
    public void onLinkDealer(){
        ActivityUtils.startActivity(getAppActivity(),FindDealerActivity.class,null);
    }
}
