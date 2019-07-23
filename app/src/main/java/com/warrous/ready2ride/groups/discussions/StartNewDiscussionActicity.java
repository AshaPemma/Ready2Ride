package com.warrous.ready2ride.groups.discussions;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.groups.discussions.model.StartDiscussionRequest;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_CAMERA;
import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_EXTERNAL_STORAGE;

public class StartNewDiscussionActicity extends BaseActivity implements StartDiscussionContract.View{

    @BindView(R.id.et_email)
    EditText etDiscussionTitle;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.iv_upload_image)
    ImageView ivUploadImage;

    StartDiscussionRequest startDiscussionRequest;
    int groupId;
    StartDiscussionContract.Presenter mPresenter;

    FeaturedGroupsResponse featuredGroupsResponse;

    ImagePickerUtils imagePickerUtils;
    String profileUrl;
    ProgressDialog pd;
    File imagePath;
    String grpName;
    @BindView(R.id.tv_to)
    TextView tvTo;


    private static final String AWS_KEY = "AKIAJXGSAAGQW2SWIVRA";
    private static final String AWS_SECRET = "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";
    private static final String AWS_BUCKET = "ready2rideassets";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_conversation);
        injectButterKnife(this);

        imagePickerUtils = new ImagePickerUtils();
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        mPresenter=new StartDiscussionPresenter(this);
        featuredGroupsResponse=(FeaturedGroupsResponse) getIntent().getSerializableExtra("featuredGroup");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            groupId = extras.getInt("GroupId");
            grpName=extras.getString("groupName");
        }
        tvTo.setText("To : "+grpName);
    }

    @OnClick(R.id.btn_send)
    public void onClickSend(){

        startDiscussionRequest= new StartDiscussionRequest();
        if(TextUtils.isEmpty(etDiscussionTitle.getText().toString())){
            AndroidUtil.showSnackBarSafe(etDiscussionTitle,"Please enter Discussion Title");
            return;
        }
        if(TextUtils.isEmpty(etMessage.getText().toString())){
            AndroidUtil.showSnackBarSafe(etDiscussionTitle,"Please enter Message");
            return;
        }

        startDiscussionRequest.setDiscussionTittle(etDiscussionTitle.getText().toString());
        startDiscussionRequest.setDiscussionDescription(etMessage.getText().toString());
        startDiscussionRequest.setDiscussionId(0);
        startDiscussionRequest.setGroupId(groupId);
        startDiscussionRequest.setImage(profileUrl);
        startDiscussionRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        mPresenter.createGroup(startDiscussionRequest);


    }
    @OnClick(R.id.ic_back)
    public void onClickBack(){
        onLoadActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onLoadActivity();

    }
    public void onLoadActivity(){
        Bundle bundle=new Bundle();
        bundle.putInt("GroupId",groupId);
        ActivityUtils.startActivityFinish(this,DiscussionsActivity.class,bundle);
    }

    @Override
    public void onStartDiscussionSucess(ArrayList<SignUpResponse> signUpResponses) {
        Bundle bundle=new Bundle();
        bundle.putInt("GroupId",groupId);
        ActivityUtils.startActivityFinish(this,DiscussionsActivity.class,bundle);
    }

    @Override
    public void onErrorStartDiscussion(String message) {
        AndroidUtil.showSnackBarSafe(this,message);
        finish();
    }

    @Override
    public void onSTartbyAdmin(String message) {
        AndroidUtil.showSnackBarSafe(this,message);
        onLoadActivity();
    }

    @OnClick(R.id.iv_gallery)
    public void onClickGallery(){
        ivUploadImage.setVisibility(View.VISIBLE);
        imagePickerUtils.showDialog(this);
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



                GlideManager.loadImage(this, imagePath.toString(), ivUploadImage);
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


                break;
            }
            case ImagePickerUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: {
                imagePath = imagePickerUtils.getPhotoFile();

                if (imagePath != null) {

                    GlideManager.loadImage(this, imagePath.toString(), ivUploadImage);
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

                    profileUrl = ImageURL;


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
}
