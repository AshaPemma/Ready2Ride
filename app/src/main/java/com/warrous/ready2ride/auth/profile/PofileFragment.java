package com.warrous.ready2ride.auth.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.profile.adapter.ProfileItemsAdapter;
import com.warrous.ready2ride.auth.profile.model.ProfileResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.dealership.mydealerships.MyDealershipsActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.garage.GarageActivity;
import com.warrous.ready2ride.groups.GroupActivity;
import com.warrous.ready2ride.invitebuddies.MyBuddiesActivity;
import com.warrous.ready2ride.rideslist.RideListActivity;
import com.warrous.ready2ride.settings.SettingsActivity;
import com.warrous.ready2ride.wallet.WalletActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PofileFragment extends BaseFragment implements ProfileItemsAdapter.OnItemClickListener,ProfileContarct.View{
    @BindView(R.id.rv_profile_items)
    RecyclerView rvProfileItems;
    ProfileItemsAdapter profileItemsAdapter;
    ProfileContarct.Presenter mPresenter;
    @BindView(R.id.tv_rides_count)
    TextView tvRidesCount;
    @BindView(R.id.tv_ride_dist)
    TextView tvRidesDist;
    @BindView(R.id.tv_ride_time)
    TextView tvRideTime;
    @BindView(R.id.iv_dealership_bg)
    ImageView ivProfileCover;
    @BindView(R.id.iv_profile)
    CircleImageView ivProfile;
    @BindView(R.id.tv_fname)
    TextView tvFname;
    ArrayList<ProfileResponse> profileResponses;







    public PofileFragment() {
    }
    public static PofileFragment getInstance() {
        PofileFragment fragment = new PofileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        injectView(view);
        mPresenter=new ProfilePresenter(this);

        rvProfileItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        profileItemsAdapter=new ProfileItemsAdapter(getContext(),this,null);
        rvProfileItems.setAdapter(profileItemsAdapter);
        if(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID)>0){
            mPresenter.getProfileDetails(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
            }
            else{
            AndroidUtil.showSnackBarSafe(rvProfileItems,"No OwnerId Linked..");

        }
        return view;
    }

    @OnClick(R.id.btn_edit)
    public void onClickEdit(){
        Bundle bundle=new Bundle();
        bundle.putString("FirstName",profileResponses.get(0).getFirstName());
        bundle.putString("LastName",profileResponses.get(0).getLastName());
        bundle.putString("MobileNumber",profileResponses.get(0).getMobile());
        bundle.putBoolean("ProfileAct",true);
        bundle.putString("profilePic",profileResponses.get(0).getProfilePhoto());
        bundle.putString("coverPic",profileResponses.get(0).getBGroundImage());
        ActivityUtils.startActivity(getActivity(),DefaultProfileActivity.class,bundle);
    }
    @OnClick(R.id.iv_settings)
    public void onClickSettings(){
        ActivityUtils.startActivity(getActivity(),SettingsActivity.class,null);
    }
    @Override
    public void onItemClick(View view, int position) {

        if(position==0){
            ActivityUtils.startActivity(getActivity(),RideListActivity.class,null);
            //ActivityUtils.startActivity(this,);
        }else if(position==1){
ActivityUtils.startActivity(getActivity(),GarageActivity.class,null);
        }else if(position==2){
            ActivityUtils.startActivity(getActivity(),MyBuddiesActivity.class,null);
        }else if(position==3){
            ActivityUtils.startActivity(getActivity(),GroupActivity.class,null);
        }else if(position==4){
            ActivityUtils.startActivity(getActivity(),MyDealershipsActivity.class,null);
        }else if(position==5){
            ActivityUtils.startActivity(getActivity(),WalletActivity.class,null);
        }
    }

    @Override
    public void onGetProfileSucess(ArrayList<ProfileResponse> profileResponse) {
        if(profileResponse!=null){
            if(profileResponse.size()!=0)
                profileResponses=new ArrayList<>();
            profileResponses=profileResponse;
            tvRidesCount.setText(String.valueOf(profileResponse.get(0).getRidesCount()));
            tvRideTime.setText(String.valueOf(profileResponse.get(0).getRidesTime()));


            tvRidesDist.setText(String .valueOf(profileResponse.get(0).getRidesDistance()));
            tvFname.setText(profileResponse.get(0).getFirstName()+" "+profileResponse.get(0).getLastName());
            if(!TextUtils.isEmpty(profileResponse.get(0).getProfilePhoto())){
                GlideManager.loadImageUrl(getContext(),profileResponse.get(0).getProfilePhoto(),ivProfile);
            }
            if(!TextUtils.isEmpty(profileResponse.get(0).getBGroundImage())){
                GlideManager.loadImageUrl(getContext(),profileResponse.get(0).getBGroundImage(),ivProfileCover);
            }
            rvProfileItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            profileItemsAdapter=new ProfileItemsAdapter(getContext(),this,profileResponse.get(0));
            rvProfileItems.setAdapter(profileItemsAdapter);

        }


    }

    @Override
    public void onGetProfileCreateSucess(ArrayList<SignUpResponse> signUpResponses) {

    }
}
