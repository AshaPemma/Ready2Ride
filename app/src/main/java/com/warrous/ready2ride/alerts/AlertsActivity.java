package com.warrous.ready2ride.alerts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.alerts.adapter.AlertsAdapter;
import com.warrous.ready2ride.alerts.createalerts.CreateNewAlertActivity;
import com.warrous.ready2ride.alerts.createalerts.CreateNewAlertEdit;
import com.warrous.ready2ride.alerts.model.AlertListResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.dealership.requestservice.RequestServiceActivty;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.PreferenceManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AlertsActivity extends BaseActivity implements AlertsContract.View,AlertsAdapter.OnItemClickListener{
    @BindView(R.id.rv_alerts)
    RecyclerView rvAlerts;

    AlertsAdapter alertsAdapter;
    ArrayList<AlertListResponse> alertsList;
    AlertsContract.Presenter mpresenter;
    LinearLayoutManager memberLinearLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        injectButterKnife(this);
        alertsList=new ArrayList<>();


        memberLinearLayoutManager=new LinearLayoutManager(this);

        rvAlerts.setLayoutManager(memberLinearLayoutManager);
     //   rvAlerts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        alertsAdapter=new AlertsAdapter(this,this,null);
        mpresenter=new ALertsPresenter(this);
        mpresenter.getAlertsList(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
//        rvAlerts.setAdapter(alertsAdapter);

    }
    @OnClick(R.id.btn_new_alert)
    public void onClickCreateAlert(){
        ActivityUtils.startActivityFinish(this,CreateNewAlertActivity.class,null);
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

    public void onClickSchedule(){
        ActivityUtils.startActivity(this,RequestServiceActivty.class,null);

    }




    @Override
    public void onGetAlertsSucess(ArrayList<AlertListResponse> alertListResponses) {

        if (alertListResponses.size() != 0) {
            memberLinearLayoutManager = new LinearLayoutManager(this);
            rvAlerts.setLayoutManager(memberLinearLayoutManager);
            alertsAdapter = new AlertsAdapter(this, this,alertListResponses);
            rvAlerts.setAdapter(alertsAdapter);
        }
    }


    @Override
    public void onItemClick(View view, int position, AlertListResponse alertResponse) {

        Intent intent=new Intent(this,CreateNewAlertEdit.class);
        intent.putExtra("alertResponse",alertResponse);
        startActivity(intent);
        finish();
    }
}
