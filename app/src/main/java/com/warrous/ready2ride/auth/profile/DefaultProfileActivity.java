package com.warrous.ready2ride.auth.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.ImagePickerUtils;
import com.warrous.ready2ride.auth.profile.model.ProfileRequest;
import com.warrous.ready2ride.auth.profile.model.ProfileResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.navigation.HomeActivity;
import com.warrous.ready2ride.producttour.ProductTourActivity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_CAMERA;
import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_EXTERNAL_STORAGE;

public class DefaultProfileActivity extends BaseActivity implements ProfileContarct.View {

    @BindView(R.id.rl_cover_image)
    RelativeLayout rlCoverImage;
    @BindView(R.id.iv_profile_image)
    CircleImageView ivProfile;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    private static final String AWS_KEY = "AKIAJXGSAAGQW2SWIVRA";
    private static final String AWS_SECRET = "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";
    private static final String AWS_BUCKET = "ready2rideassets";
    ProgressDialog pd;


    ImagePickerUtils imagePickerUtils;
    String profileUrl, coverUrl;
    ProfileContarct.Presenter mPresenter;


    File imagePath;
    boolean coverImage = false;
    boolean profileAct = false;
    String profile="", cover="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profie);
        injectButterKnife(this);
        imagePickerUtils = new ImagePickerUtils();
        mPresenter = new ProfilePresenter(this);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading");

        Bundle extras = getIntent().getExtras();
        String firstname = null, lastnam = null, profilepic = null, coverPic = null,mobile=null;

        if (extras != null) {
            firstname = extras.getString("FirstName");
            lastnam = extras.getString("LastName");
            profileAct = extras.getBoolean("ProfileAct");
            profilepic = extras.getString("profilePic");
            coverPic = extras.getString("coverPic");
            mobile=extras.getString("MobileNumber");
            profile = profilepic;
            cover = coverPic;


            etFirstName.setText(firstname);
            etLastName.setText(lastnam);
            etMobile.setText(mobile);
            if (!TextUtils.isEmpty(profilepic)) {
                GlideManager.loadImage(this, profilepic, ivProfile);
            }
            if (!TextUtils.isEmpty(coverPic)) {
                GlideManager.loadImage(this, coverPic, ivCover);
            }


        }


    }

    @OnClick(R.id.rl_cover_image)
    public void onClickCoverImage() {
        coverImage = true;
        imagePickerUtils.showDialog(this);

    }

    @OnClick(R.id.iv_profile_image)
    public void onClickProfileImage() {
        coverImage = false;
        imagePickerUtils.showDialog(this);
    }

    @OnClick(R.id.btn_next)
    public void onClickNext() {
//        if(TextUtils.isEmpty(coverUrl)){
//            AndroidUtil.showSnackBarSafe(this,"Please select CoverImage");
//            return;
//        }
//        if(TextUtils.isEmpty(profileUrl)){
//            AndroidUtil.showSnackBarSafe(this,"Please select ProfileImage");
//            return;
//        }
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            AndroidUtil.showSnackBarSafe(this, "Please provide First Name");
            return;
        }
        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            AndroidUtil.showSnackBarSafe(this, "Please provide Last Name");
            return;
        }

        if (TextUtils.isEmpty(etMobile.getText().toString())) {
            AndroidUtil.showSnackBarSafe(this, "Please provide Mobile NUmber");
            return;
        }
        ProfileRequest profileRequest = new ProfileRequest();
        if (TextUtils.isEmpty(coverUrl)) {
            profileRequest.setBGroungImage(cover);
        } else {
            profileRequest.setBGroungImage(coverUrl);
        }
        if (TextUtils.isEmpty(profileUrl)) {
            profileRequest.setProfileImage(profile);
        } else {
            profileRequest.setProfileImage(profileUrl);
        }

        profileRequest.setFirstName(etFirstName.getText().toString());
        profileRequest.setLastName(etLastName.getText().toString());
        profileRequest.setMobile(etMobile.getText().toString());
        profileRequest.setOwnerId(PreferenceManager.getIntegerValue(this, PreferenceManager.KEY_OWNER_ID));

        mPresenter.saveprofile(profileRequest);


    }

    @OnClick(R.id.iv_back)
    public void onClickBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ImagePickerUtils.SELECT_IMAGE_ACTIVITY_REQUEST_CODE: {
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();

                imagePath = new File(AndroidUtil.getRealPathFromURI(this, uri, true));


                if (coverImage) {
                    GlideManager.loadImage(this, imagePath.toString(), ivCover);
                    pd.show();
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                uploadImageToAWS(imagePath.toString(), true);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                } else {
                    GlideManager.loadImage(this, imagePath.toString(), ivProfile);
                    pd.show();
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                uploadImageToAWS(imagePath.toString(), false);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                }
                break;
            }
            case ImagePickerUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: {
                imagePath = imagePickerUtils.getPhotoFile();

                if (imagePath != null) {
                    if (coverImage) {
                        GlideManager.loadImage(this, imagePath.toString(), ivCover);
                        pd.show();
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {

                                    uploadImageToAWS(imagePath.toString(), true);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();

                    } else {
                        GlideManager.loadImage(this, imagePath.toString(), ivProfile);
                        pd.show();
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {

                                    uploadImageToAWS(imagePath.toString(), false);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();

                    }
                }
                break;

            }
        }
    }

    private void uploadImageToAWS(String selectedImagePath, final boolean cover) {

        if (selectedImagePath == null) {
            Toast.makeText(this, "Could not find the filepath of the selected file", Toast.LENGTH_LONG).show();

// to make sure that file is not emapty or null
            return;
        }

        File file = new File(selectedImagePath);

        AmazonS3 s3Client = null;

        if (s3Client == null) {

            ClientConfiguration clientConfig = new ClientConfiguration();

            clientConfig.setProtocol(Protocol.HTTP);

            clientConfig.setMaxErrorRetry(0);

            clientConfig.setSocketTimeout(60000);

            BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_KEY, AWS_SECRET);

            s3Client = new AmazonS3Client(credentials, clientConfig);

            s3Client.setRegion(Region.getRegion(Regions.US_EAST_1));

//            String ImageURL = String.valueOf(  ((AmazonS3Client) s3Client).getUrl(
//                    AWS_BUCKET, //The S3 Bucket To Upload To
//                    file.getName()));
//            Toast.makeText(this,ImageURL,Toast.LENGTH_LONG).show();

        }

        FileInputStream stream = null;

        try {

            stream = new FileInputStream(file);

            ObjectMetadata objectMetadata = new ObjectMetadata();

            Log.d("messge", "converting to bytes");

            objectMetadata.setContentLength(file.length());

            String[] s = selectedImagePath.split("\\.");

            String extenstion = s[s.length - 1];

            Log.d("messge", "set content length : " + file.length() + "sss" + extenstion);

            String fileName = UUID.randomUUID().toString();

            PutObjectRequest putObjectRequest = new PutObjectRequest(AWS_BUCKET, "r2randroidassets/" + fileName + "." + extenstion, stream, objectMetadata)

                    .withCannedAcl(CannedAccessControlList.PublicRead);

// above line is  making the request to the aws  server for the specific place to upload the image were aws_bucket is the main folder  name and inside that is the profiles folder and there the file will be get uploaded

            PutObjectResult result = s3Client.putObject(putObjectRequest);

// this will  add the image to the specified path in the aws bucket.


            final String ImageURL = String.valueOf(((AmazonS3Client) s3Client).getResourceUrl(AWS_BUCKET, putObjectRequest.getKey()));


            runOnUiThread(new Runnable() {

                public void run() {
                    if (cover) {
                        coverUrl = ImageURL;
                    } else {
                        profileUrl = ImageURL;
                    }

                    pd.dismiss();
                }

            });


            if (result == null) {

                Log.e("RESULT", "NULL");

            } else {

                Log.e("RESULT", result.toString());

            }

        } catch (Exception e) {

            Log.d("ERRORR", " " + e.getMessage());

            e.printStackTrace();


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        imagePickerUtils.selectImageFromGallery(this);
                    }
                }
            }
        } else if (requestCode == REQUEST_CAMERA) {
            boolean hasCameraPermission = false;
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        hasCameraPermission = true;
                    }
                } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED && hasCameraPermission) {
                        imagePickerUtils.dispatchTakePhotoIntent(this);
                    }
                }
            }
        }
    }

    @Override
    public void onGetProfileSucess(ArrayList<ProfileResponse> profileResponse) {
    }

    @Override
    public void onGetProfileCreateSucess(ArrayList<SignUpResponse> signUpResponses) {
        if (profileAct) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("ProfileFrag", true);
            ActivityUtils.startActivity(this, HomeActivity.class, bundle);
        } else {
            ActivityUtils.startActivity(this, ProductTourActivity.class, null);
        }

    }
}
