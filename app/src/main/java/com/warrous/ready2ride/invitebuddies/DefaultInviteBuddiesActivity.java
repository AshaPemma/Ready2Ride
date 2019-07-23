package com.warrous.ready2ride.invitebuddies;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.PermissionUtils;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.popup.DataManager;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.framework.Ready2RideLog;
import com.warrous.ready2ride.groups.DetailedGroupActivity;
import com.warrous.ready2ride.groups.DetailedGroupActivityDash;
import com.warrous.ready2ride.invitebuddies.adapter.FeaturedGroupsAdapter;
import com.warrous.ready2ride.invitebuddies.adapter.FeaturedRidesAdapter;
import com.warrous.ready2ride.invitebuddies.adapter.InviteBuddiesAdapter;
import com.warrous.ready2ride.invitebuddies.adapter.PeopleuMayknowAdapter;
import com.warrous.ready2ride.invitebuddies.model.AppUsers;
import com.warrous.ready2ride.invitebuddies.model.BuddiesKnown;
import com.warrous.ready2ride.invitebuddies.model.BuddiesList;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequest;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequests;
import com.warrous.ready2ride.invitebuddies.model.ContactModel;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesResponse;
import com.warrous.ready2ride.trackrides.TrackRideActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_CONTACTS;

public class DefaultInviteBuddiesActivity extends BaseActivity implements DefaultInviteBuddiesContract.View,PeopleuMayknowAdapter.OnItemClickListener,FeaturedGroupsAdapter.OnItemClickListener{


    @BindView(R.id.rv_featured_groups)
    RecyclerView rvFeaturedGroups;
    @BindView(R.id.rv_featured_riders)
    RecyclerView rvFeaturedRides;
    @BindView(R.id.rv_people_u_know)
    RecyclerView rvPeopleUKnow;
    @BindView(R.id.tv_no_featured_groups)
    TextView tvNoFeaturedGroups;
    @BindView(R.id.tv_no_featured_riders)
    TextView tvNoFeaturedRides;

    @BindView(R.id.et_searchn)
    EditText etSearch;




    PeopleuMayknowAdapter peopleuMayknowAdapter;
    FeaturedGroupsAdapter featuredGroupsAdapter;
    FeaturedRidesAdapter featuredRidesAdapter;


    DefaultInviteBuddiesContract.Presenter mPresenter;

    LinearLayoutManager memberLinearLayoutManagerG,memberLinearLayoutManagerR;
    ArrayList<ContactModel> contactModels;
    ArrayList<FeaturedGroupsResponse> featuredGroupsResponses;
    ArrayList<AppUsers> appUsers;
    ArrayList<ContactModel> onlyAppUsers;
    ArrayList<BuddiesKnown> buddiesList;
    String lists[];
    String buddiesknList[];
   // ProgressDialog pd;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_invite_buddies);
        injectButterKnife(this);

        mPresenter=new DefaultInvitebuddiesPresenter(this);

         mPresenter.getFeaturedGroups(0);
         mPresenter.getFeaturedRiders(0);
         mPresenter.getAppUsers();

//        pd = new ProgressDialog(this);
//        pd.setMessage("Uploading");





      //  rvFeaturedGroups.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        memberLinearLayoutManagerG=new LinearLayoutManager(getContext());
        featuredGroupsAdapter=new FeaturedGroupsAdapter(this,this,null);
       // rvFeaturedGroups.setAdapter(featuredGroupsAdapter);

       // rvFeaturedRides.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        memberLinearLayoutManagerR=new LinearLayoutManager(getContext());
        featuredRidesAdapter=new FeaturedRidesAdapter(this,null,null);
       // rvFeaturedRides.setAdapter(featuredRidesAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(etSearch.getText().toString());
            }
        });
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<ContactModel> filterdNames = new ArrayList<>();
        ArrayList<FeaturedGroupsResponse> filterdResponse=new ArrayList<>();

        //looping through existing elements
        for (int i=0;i<contactModels.size();i++) {
            //if the existing elements contains the search input
            if (contactModels.get(i).getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(contactModels.get(i));
            }
        }

        //calling a method of the adapter class and passing the filtered list
        peopleuMayknowAdapter.filterList(filterdNames);
        if (featuredGroupsResponses != null) {
            for(int j=0;j<featuredGroupsResponses.size();j++){
                if (featuredGroupsResponses.get(j).getName().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdResponse.add(featuredGroupsResponses.get(j));
                }
            }
            featuredGroupsAdapter.filterList(filterdResponse);

        }

    }

    @OnClick(R.id.btn_invite_buddies)
    public void onClicInvideBuddies(){

        Intent intent=new Intent(this,InviteBuddiesActivity.class);
        //intent.putExtra("contactList",contactModels);
        startActivity(intent);
      //  ActivityUtils.startActivity(this,InviteBuddiesActivity.class,null);
    }
    @OnClick(R.id.tv_skip)
    public void onSkip(){
        ActivityUtils.startActivity(this,TrackRideActivity.class,null);
    }
    public ArrayList<ContactModel> getContacts(Context ctx) {
        ArrayList<ContactModel> list = new ArrayList<>();
//        if(DataManager.getInstance().getContactModels()!=null){
//            list.addAll(DataManager.getInstance().getContactModels());
//        }

        //pd.show();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        ContactModel info = new ContactModel();
                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        list.add(info);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();

        }
        return list;
    }
    @OnClick(R.id.iv_back)
    public void onBack(){
        finish();
    }
     @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACTS) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.READ_CONTACTS)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                     // getContacts(this);
                      contactModels=getContacts(this);
                      showLoader();
                        rvPeopleUKnow.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                        peopleuMayknowAdapter=new PeopleuMayknowAdapter(this,this,contactModels);
                        rvPeopleUKnow.setAdapter(peopleuMayknowAdapter);
                        dismissLoader();
                    }
                }
            }
        }
    }

    public void onBuddyRequest(BuddyRequests buddyRequests){

        mPresenter.sendBuddyRequest(buddyRequests);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getContacts(){


        mPresenter.getBuddies(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));



    }

    public void BuddiesList(){
        onlyAppUsers=new ArrayList<>();

        if(contactModels==null){
            if (!PermissionUtils.verifyContactsPermissions(this)) {
                return;
            }else{
                contactModels=getContacts(this);
            }
        }
        for(int i=0;i<contactModels.size();i++){
            String mobile = null;
            if(lists!=null){
                if(!TextUtils.isEmpty(contactModels.get(i).getMobileNumber())){
                     mobile=contactModels.get(i).getMobileNumber().trim().replaceAll("\\s+", "");
                }


                Ready2RideLog.getInstance().info("Mob",mobile);
                if(Arrays.asList(lists).contains(mobile)) {

                    contactModels.get(i).setOwnerId(getOwnerId(contactModels.get(i).getMobileNumber()));
                    onlyAppUsers.add(contactModels.get(i));
                }
            }

        }
        isBuddy();

    }

    public int getOwnerId(String Mobile){
        int owner = 0;
        for(int i=0;i<buddiesList.size();i++){
            if(buddiesList.get(i).getMobileNumber().equalsIgnoreCase(Mobile)){
                owner=buddiesList.get(i).getOwnerId();
                break;
            }

        }
        return owner;
    }




    public void isBuddy(){

        for(int i=0;i<onlyAppUsers.size();i++){
            String mobile = null;
            if(buddiesknList!=null){
                if(!TextUtils.isEmpty(onlyAppUsers.get(i).getMobileNumber())) {
                     mobile = onlyAppUsers.get(i).getMobileNumber().trim().replaceAll("\\s+", "");
                }
                Ready2RideLog.getInstance().info("Mob",mobile);
                if(Arrays.asList(buddiesknList).contains(mobile)) {
                  onlyAppUsers.get(i).setBuddy(isBuddytrue(onlyAppUsers.get(i).getMobileNumber()));
                }else{
                    onlyAppUsers.get(i).setBuddy(false);
                }
            }

        }




        rvPeopleUKnow.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        peopleuMayknowAdapter=new PeopleuMayknowAdapter(this,this,onlyAppUsers);
        rvPeopleUKnow.setAdapter(peopleuMayknowAdapter);
    }

    public boolean isBuddytrue(String Mobile){
        boolean isBuddytrue = false;
        for(int i=0;i<buddiesList.size();i++){
            if(buddiesList.get(i).getMobileNumber().equalsIgnoreCase(Mobile)){
                isBuddytrue=buddiesList.get(i).isBuddy();
                break;
            }

        }
        return isBuddytrue;
    }

    @Override
    public void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses) {
        featuredGroupsResponses=new ArrayList<>();
        featuredGroupsResponses=segmentResponses;


        if(segmentResponses.size()!=0){
            rvFeaturedGroups.setVisibility(View.VISIBLE);
            tvNoFeaturedGroups.setVisibility(View.GONE);
            memberLinearLayoutManagerG = new LinearLayoutManager(getContext());
            rvFeaturedGroups.setLayoutManager(memberLinearLayoutManagerG);
            featuredGroupsAdapter = new FeaturedGroupsAdapter(this, this, segmentResponses);
            rvFeaturedGroups.setAdapter(featuredGroupsAdapter);
        }else{
            rvFeaturedGroups.setVisibility(View.GONE);
            tvNoFeaturedGroups.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onFeaturedRidersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses) {

        if(featuredRidersResponses.size()!=0){
            tvNoFeaturedRides.setVisibility(View.GONE);
            rvFeaturedRides.setVisibility(View.VISIBLE);
            memberLinearLayoutManagerR = new LinearLayoutManager(getContext());
            rvFeaturedRides.setLayoutManager(memberLinearLayoutManagerR);
            featuredRidesAdapter = new FeaturedRidesAdapter(this, null, featuredRidersResponses);
            rvFeaturedRides.setAdapter(featuredRidesAdapter);
        }else
        {
            tvNoFeaturedRides.setVisibility(View.VISIBLE);
            rvFeaturedRides.setVisibility(View.GONE);
        }
    }

    @Override
    public void onInviteBuddiesSucess(ArrayList<SignUpResponse> signUpResponses) {

        AndroidUtil.showSnackBarSafe(rvFeaturedGroups,"Buddy Invited Sucessfully");

    }

    @Override
    public void ongetInvideBudddiesSucess(ArrayList<BuddyListResponse> inviteBuddiesResponses) {

    }

    @Override
    public void onGetAppUsersSucess(ArrayList<AppUsers> appUsersList) {

        appUsers=new ArrayList<>();
        appUsers=appUsersList;
        lists=new String[appUsers.size()];

        if(appUsers!=null){
            if(appUsers.size()>0){
                for(int i=0;i<appUsers.size();i++){
//                    if(!TextUtils.isEmpty(appUsers.get(i).getMobileNumber())){
                    lists[i]=appUsers.get(i).getMobileNumber();
                    // }

                }
            }
        }

        getContacts();

    }

    @Override
    public void onGetBuddiesKnown(ArrayList<BuddiesKnown> buddiesKnownsList) {

        buddiesList=buddiesKnownsList;

        buddiesknList=new String[buddiesList.size()];

        if(appUsers!=null){
            if(buddiesList.size()>0){
                for(int i=0;i<buddiesList.size();i++){
//                    if(!TextUtils.isEmpty(appUsers.get(i).getMobileNumber())){
                    buddiesknList[i]=buddiesList.get(i).getMobileNumber();
                    // }

                }
            }
        }

        BuddiesList();
    }

    @Override
    public void onSendBudyRequest(ArrayList<SignUpResponse> signUpResponses) {
        AndroidUtil.showSnackBarSafe(rvFeaturedGroups,"Buddy Request sent Sucessfully");
    }


    @Override
    public void onItemClick(View view, int position,InviteBuddiesRequest inviteBuddiesRequest) {

//        InviteBuddiesRequest inviteBuddiesRequest=new InviteBuddiesRequest();
//        ArrayList<BuddiesList> namesList=new ArrayList();
//        BuddiesList buddiesList=new BuddiesList();
//        buddiesList.setEmail("ashuasha51@gmail.com");
//        buddiesList.setFName("Pemma");
//        buddiesList.setLName("Asha");
//        buddiesList.setMobile("9177781648");
//        buddiesList.setOwnerId(PreferenceManager00.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
//        buddiesList.setSenderId(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
//        namesList.add(buddiesList);
//        inviteBuddiesRequest.setBuddyList(namesList);
      //  mPresenter.inviteBuddies(inviteBuddiesRequest);
    }

    @Override
    public void onItemClickG(View view, int position, FeaturedGroupsResponse featuredGroupsResponse) {
        Intent intent=new Intent(this,DetailedGroupActivityDash.class);
        intent.putExtra("featuredGroup",featuredGroupsResponse);
        startActivity(intent);
    }
}
