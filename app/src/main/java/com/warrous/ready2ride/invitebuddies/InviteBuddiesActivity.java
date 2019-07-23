package com.warrous.ready2ride.invitebuddies;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.PermissionUtils;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.dealership.popup.DataManager;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.Ready2RideLog;
import com.warrous.ready2ride.invitebuddies.adapter.InviteBuddiesAdapter;
import com.warrous.ready2ride.invitebuddies.model.AppUsers;
import com.warrous.ready2ride.invitebuddies.model.BuddiesKnown;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;
import com.warrous.ready2ride.invitebuddies.model.ContactModel;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteBuddiesActivity extends BaseActivity implements InviteBuddiesAdapter.OnItemClickListener,DefaultInviteBuddiesContract.View{
    @BindView(R.id.rv_buddies)
    RecyclerView rvBuddies;
    InviteBuddiesAdapter inviteBuddiesAdapter;
    ArrayList<ContactModel> contactModels;
    DefaultInviteBuddiesContract.Presenter mPresenetr;
    ArrayList<AppUsers> appUsers;
    String lists[];
    @BindView(R.id.et_search)
    EditText etSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_buddies);
        injectButterKnife(this);

        mPresenetr=new DefaultInvitebuddiesPresenter(this);
       // contactModels = (ArrayList<ContactModel>) getIntent().getSerializableExtra("contactList");

        mPresenetr.getAppUsers();

//        pd = new ProgressDialog(this);
//        pd.setMessage("Uploading");



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

        //looping through existing elements
        for (int i=0;i<contactModels.size();i++) {
            //if the existing elements contains the search input
            if (contactModels.get(i).getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(contactModels.get(i));
            }
        }

        //calling a method of the adapter class and passing the filtered list
        inviteBuddiesAdapter.filterList(filterdNames);

    }
    public void getContacts(){


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
                    contactModels.remove(i);
                }
            }

        }
        rvBuddies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        inviteBuddiesAdapter=new InviteBuddiesAdapter(this,this,contactModels);
        rvBuddies.setAdapter(inviteBuddiesAdapter);
        //pd.dismiss();

    }

    public ArrayList<ContactModel> getContacts(Context ctx) {
        //pd.show();
        ArrayList<ContactModel> list = new ArrayList<>();
//        if(DataManager.getInstance().getContactModels()!=null){
//            list.addAll(DataManager.getInstance().getContactModels());
//        }

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
    public void onClickBack(){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onItemClick(View view, int position, InviteBuddiesRequest inviteBuddiesRequest) {

        mPresenetr.inviteBuddies(inviteBuddiesRequest);
    }

    @Override
    public void onFeaturedGroupSucess(ArrayList<FeaturedGroupsResponse> segmentResponses) {

    }

    @Override
    public void onFeaturedRidersSucess(ArrayList<FeaturedRidersResponse> featuredRidersResponses) {

    }

    @Override
    public void onInviteBuddiesSucess(ArrayList<SignUpResponse> signUpResponses) {
        AndroidUtil.showSnackBarSafe(rvBuddies,"Buddy Invited Sucessfully");
    }

    @Override
    public void ongetInvideBudddiesSucess(ArrayList<BuddyListResponse> inviteBuddiesResponses) {

    }

    @Override
    public void onGetAppUsersSucess(ArrayList<AppUsers> appUsersList) {
       // pd.show();
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

    }

    @Override
    public void onSendBudyRequest(ArrayList<SignUpResponse> signUpResponses) {

    }
}
