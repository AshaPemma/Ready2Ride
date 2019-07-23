package com.warrous.ready2ride.bike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.alerts.AlertsActivity;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.bike.adapter.RideLogAdapter;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.bike.model.RideLogResponse;
import com.warrous.ready2ride.dealership.adapter.DotsAdapter;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.ridelog.RideLogListActivity;
import com.warrous.ready2ride.rideslist.RideListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.Body;

public class DefaultBikeFragment extends BaseFragment implements DefaultBikeContract.View {
    @BindView(R.id.rl_ride_log)
    RecyclerView rlRideLogs;
    RideLogAdapter rideLogAdapter;

    @BindView(R.id.tv_make_des)
    TextView tvMakeDes;
    @BindView(R.id.tv_model_desc)
            TextView tvModelDesc;
    @BindView(R.id.tv_year_des)
            TextView tvYearDesc;
    @BindView(R.id.tv_dealer_name)
            TextView tvBikeName;

    @BindView(R.id.tv_rides_des)
            TextView tvRidesdesc;
    @BindView(R.id.tv_no_prom)
            TextView tvNoRides;
    int position;
    @BindView(R.id.tv_see)
            TextView tvSee;

    @BindView(R.id.rv_dots)
    RecyclerView rvDots;
    @BindView(R.id.tv_m_ridden)
    TextView tvMilesRidden;

    @BindView(R.id.iv_dealership_bg)
    SimpleDraweeView sdBikeImage;

    DotsAdapter dotsAdapter;
    int size;
    DefaultBikeContract.Presenter mPresenter;
    ArrayList<RideList> rideList=new ArrayList<>();





    DefaultBikeDetailsResponse defaultBikeDetailsResponse;

    public DefaultBikeFragment() {
    }
    public static DefaultBikeFragment getInstance(DefaultBikeDetailsResponse defaultBikeDetailsResponse,int size,int pos) {
        DefaultBikeFragment fragment = new DefaultBikeFragment();
        fragment.defaultBikeDetailsResponse=defaultBikeDetailsResponse;
        fragment.position=pos;
        fragment.size=size;
        return fragment;
    }

    @OnClick(R.id.tv_see)
    public void onClickSee(){
        Intent intent=new Intent(getActivity(),RideListActivity.class);
        intent.putExtra("ridesResponse",rideList);
        startActivity(intent);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bike, container, false);
        injectView(view);

        mPresenter=new DefaultBikePresenter(this);

        rideLogAdapter = new RideLogAdapter(getContext(), null, null);

        if(defaultBikeDetailsResponse!=null) {
            tvBikeName.setText(defaultBikeDetailsResponse.getCycleName());
            tvMakeDes.setText(defaultBikeDetailsResponse.getBrandName());
            tvModelDesc.setText(defaultBikeDetailsResponse.getModelName());
            tvMilesRidden.setText(String.valueOf(defaultBikeDetailsResponse.getOdometerReading()));
            if(defaultBikeDetailsResponse.getPhotoPath()!=null){
                GlideManager.loadImageUrl(getContext(),defaultBikeDetailsResponse.getPhotoPath(),sdBikeImage);
                }
            if(!defaultBikeDetailsResponse.getRideList().isEmpty()){
                tvRidesdesc.setText(Integer.toString(defaultBikeDetailsResponse.getRideList().get(0).getTotalRides())+" Rides");
                } else{
                tvRidesdesc.setText("0 Rides");
            }


            rvDots.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

            //   rvAlerts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            dotsAdapter=new DotsAdapter(getContext(),null,size,defaultBikeDetailsResponse.getPosition());
            rvDots.setAdapter(dotsAdapter);
           tvYearDesc.setText(defaultBikeDetailsResponse.getYear());
         //   tvYearDesc.setText(Double.toString(defaultBikeDetailsResponse.getYear()));

        }
        if(defaultBikeDetailsResponse!=null) {
//            if (!defaultBikeDetailsResponse.getRideList().isEmpty()) {
//                if (defaultBikeDetailsResponse.getRideList().size() == 0) {
//                    tvNoRides.setVisibility(View.VISIBLE);
//                    rlRideLogs.setVisibility(View.GONE);
//                    tvSee.setVisibility(View.GONE);
//                } else {
//                    tvNoRides.setVisibility(View.GONE);
//                    rlRideLogs.setVisibility(View.VISIBLE);
//                    tvSee.setVisibility(View.VISIBLE);
//                    rlRideLogs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                    rideLogAdapter = new RideLogAdapter(getContext(), null, defaultBikeDetailsResponse.getRideList());
//                    rlRideLogs.setAdapter(rideLogAdapter);
//                }
//            } else {
//                tvNoRides.setVisibility(View.VISIBLE);
//                rlRideLogs.setVisibility(View.GONE);
//            }

            mPresenter.getRideDetails(defaultBikeDetailsResponse.getCycleId(),PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));

        }

        return view;
    }

    @OnClick(R.id.btn_manage_alerts)
    public void onClickManage(){
        ActivityUtils.startActivity(getActivity(),AlertsActivity.class,null);
    }

    @Override
    public void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses) {

    }

    @Override
    public void onGetRideLogResponse(ArrayList<RideList> segmentResponses) {
            if (segmentResponses.size()== 0) {
                tvNoRides.setVisibility(View.VISIBLE);
                rlRideLogs.setVisibility(View.GONE);
                tvSee.setVisibility(View.GONE);
            } else {
                tvNoRides.setVisibility(View.GONE);
                rlRideLogs.setVisibility(View.VISIBLE);
                tvSee.setVisibility(View.VISIBLE);
                rideList=segmentResponses;
                rlRideLogs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                rideLogAdapter = new RideLogAdapter(getContext(), null, segmentResponses);
                rlRideLogs.setAdapter(rideLogAdapter);

        }
    }


}
