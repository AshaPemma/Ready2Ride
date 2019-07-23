package com.warrous.ready2ride.dealership;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.dealership.adapter.DotsAdapter;
import com.warrous.ready2ride.dealership.adapter.PromotionsEventsAdapter;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.promotions.PromotionsActivity;
import com.warrous.ready2ride.dealership.requestservice.RequestServiceActivty;
import com.warrous.ready2ride.dealership.sendmessage.SendMessageActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.GlideManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DealerShipFragment extends BaseFragment implements DealerShipContract.View{

    @BindView(R.id.rv_promotion_events)
    RecyclerView rvPromotions;


    @BindView(R.id.tv_dealer_name)
    TextView tvDealerName;
    @BindView(R.id.tv_adress_desc)
    TextView tvAddress;
    PromotionsEventsAdapter promotionsEventsAdapter;
    @BindView(R.id.tv_phone_desc)
    TextView tvPhoneNum;

    @BindView(R.id.tv_website_desc)
    TextView tvWebsite;

    @BindView(R.id.rv_dots)
    RecyclerView rvDots;
    @BindView(R.id.iv_dealership_bg)
    ImageView ivDealerBackground;


    DealershipResponse dealershipResponse;
    DealerShipContract.Presenter mPresenter;
    LinearLayoutManager memberLinearLayoutManager;
    @BindView(R.id.tv_no_prom)
    TextView tvNoProm;
    int size,position;
    DotsAdapter dotsAdapter;
    ArrayList<EventsPromotionsResponse> eventsList=new ArrayList<>();



    public DealerShipFragment() {
    }
    public static DealerShipFragment getInstance(DealershipResponse dealershipResponse,int size) {
        DealerShipFragment fragment = new DealerShipFragment();
        fragment.dealershipResponse=dealershipResponse;
        fragment.size=size;
        //fragment.position=position;
        return fragment;
    }

    @OnClick(R.id.tv_see)
    public void onClickSee(){
        Intent intent=new Intent(getActivity(),PromotionsActivity.class);
        intent.putExtra("dealerId",dealershipResponse.getDealerId());
        intent.putExtra("eventsList",eventsList);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dealership, container, false);
        injectView(view);

        memberLinearLayoutManager=new LinearLayoutManager(getContext());

        rvPromotions.setLayoutManager(memberLinearLayoutManager);
        rvDots.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        //   rvAlerts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        promotionsEventsAdapter=new PromotionsEventsAdapter(getContext(),null,null);
        dotsAdapter=new DotsAdapter(getContext(),null,size,dealershipResponse.getPosition());
        rvDots.setAdapter(dotsAdapter);
//        if(dealershipResponse.getPosition()>4){
//            rvDots.scrollToPosition(dealershipResponse.getPosition());
//        }


        tvDealerName.setText(dealershipResponse.getName());
        tvAddress.setText(dealershipResponse.getCity()+"\n"+dealershipResponse.getState()+" "+dealershipResponse.getZip());
        tvWebsite.setText(dealershipResponse.getWebSite());
        String number = dealershipResponse.getPhone().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        tvPhoneNum.setText(number);
      //  tvPhoneNum.setText(dealershipResponse.getPhone());
        if(!TextUtils.isEmpty(dealershipResponse.getBackgroundImage())){
            GlideManager.loadImageUrl(getContext(),dealershipResponse.getBackgroundImage(),ivDealerBackground);
        }else{
            ivDealerBackground.setBackgroundResource(R.drawable.ic_dealership_background);
        }
        mPresenter=new DealershipPresenter(this);
        mPresenter.getEventList(dealershipResponse.getDealerId());
//        rvPromotions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        promotionsEventsAdapter=new PromotionsEventsAdapter(getContext(),null);
//        rvPromotions.setAdapter(promotionsEventsAdapter);
        return view;
    }
    @OnClick(R.id.btn_request_service)
    public void onClickRequest(){
        ActivityUtils.startActivity(getActivity(),RequestServiceActivty.class,null);
    }

    @OnClick(R.id.btn_send_message)
    public void onClickSendMessage(){
        ActivityUtils.startActivity(getActivity(),SendMessageActivity.class,null);
    }



    @Override
    public void onDealershipSucess(ArrayList<DealershipResponse> segmentResponses) {

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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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


    @Override
    public void onEventsSucess(ArrayList<EventsPromotionsResponse> eventsPromotionsResponses) {

        eventsList=eventsPromotionsResponses;


            if(eventsPromotionsResponses.size()==0){
                rvPromotions.setVisibility(View.GONE);
                tvNoProm.setVisibility(View.VISIBLE);
            }else {
                rvPromotions.setVisibility(View.VISIBLE);
                tvNoProm.setVisibility(View.GONE);
                memberLinearLayoutManager = new LinearLayoutManager(getContext());
                rvPromotions.setLayoutManager(memberLinearLayoutManager);
                promotionsEventsAdapter = new PromotionsEventsAdapter(getContext(), null, eventsPromotionsResponses);
                rvPromotions.setAdapter(promotionsEventsAdapter);
            }

    }

    @Override
    public void ongetSelectedDealerssucess(ArrayList<DealershipResponse> dealershipResponsesss) {

    }

    @Override
    public void onSavePromotionSUcess(ArrayList<SignUpResponse> signUpResponses) {

    }
}
