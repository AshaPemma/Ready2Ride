package com.warrous.ready2ride.createbike;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.ImagePickerUtils;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.createbike.adapter.Info_brand_adapter;
import com.warrous.ready2ride.createbike.adapter.Info_model_adapter;
import com.warrous.ready2ride.createbike.adapter.Info_year_adapter;
import com.warrous.ready2ride.createbike.model.Brand;
import com.warrous.ready2ride.createbike.model.CreateBikeRequest;
import com.warrous.ready2ride.createbike.model.Model;
import com.warrous.ready2ride.createbike.model.Year;
import com.warrous.ready2ride.dealership.DefaultDelaerShipActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.GlideManager;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.framework.Ready2RideLog;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_CAMERA;
import static com.warrous.ready2ride.Util.PermissionUtils.REQUEST_EXTERNAL_STORAGE;

public class CreateBikeActivity extends BaseActivity implements CreateBikeContract.View{

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.spiner_year)
    Spinner spMake;
    @BindView(R.id.spiner_make)
    Spinner spYear;
    @BindView(R.id.spiner_model)
    Spinner spModel;
    @BindView(R.id.et_bik_name)
    EditText etBikeName;
    @BindView(R.id.et_vin)
    EditText etVin;
    @BindView(R.id.et_pdate)
    EditText etPdate;
    @BindView(R.id.et_odometr_reading)
    EditText etOdoMeterTReading;
    @BindView(R.id.iv_bike)
    SimpleDraweeView sdBike;

    ProgressDialog pd;


    ImagePickerUtils imagePickerUtils;
    String imageUrl;

    File imagePath;

    private static final String AWS_KEY = "AKIAJXGSAAGQW2SWIVRA";
    private static final String AWS_SECRET = "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";
    private static final String AWS_BUCKET = "ready2rideassets";



    private ArrayList<Brand> brandList;
    private ArrayList<Model> modelList;
    private ArrayList<Year> yearList;
    int spinerchild_layout;
    CreateBikeContract.Presenter mPresenter;

    static Calendar calendar;
    static String yearName=null;

    private static String twoDigit(int val) {
        if (val < 10)
            return "0" + val;
        else
            return "" + val;
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bike);
        injectButterKnife(this);
        spinerchild_layout = R.layout.raw_bikemodel_text;
        tvHeader.setText("Create Bike");
        calendar = Calendar.getInstance();
        mPresenter=new CreateBikePresenter(this);
        imagePickerUtils = new ImagePickerUtils();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading");

        bindAdapter();
         mPresenter.ongetBrands(0);

         spMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position>0){
                     String brandName=spMake.getSelectedItem().toString();
                     int brandId=getBrandid(brandName);
                     mPresenter.onGetYears(brandId);
                 }

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position>0){
                     String yearsName=spYear.getSelectedItem().toString();
                     yearName=yearList.get(position).getYearValue();

                    // int brandYearId=getbrandYearid(yearName);
                     int brandYearId=yearList.get(position-1).getBrandYearId();

                     mPresenter.onGetModels(brandYearId);
                 }

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

       // new AttemptGetBrandList().execute();

    }

    @OnClick(R.id.iv_bike)
    public void onClickBike(){
        imagePickerUtils.showDialog(this);
    }



    private void uploadImageToAWS(String selectedImagePath) {

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


            final String ImageURL = String.valueOf( ((AmazonS3Client) s3Client).getResourceUrl(AWS_BUCKET,putObjectRequest.getKey()));


            runOnUiThread(new Runnable() {

                public void run() {

                   // selectButton.setText("success");
//                    Toast.makeText(CreateBikeActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CreateBikeActivity.this,ImageURL,Toast.LENGTH_LONG).show();
                    imageUrl=ImageURL;

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

//            Log.e("ERROR",e.getMessage());

        }

    }


    private void bindAdapter() {
        brandList = new ArrayList<>();
        modelList = new ArrayList<>();
        yearList=new ArrayList<>();


        Year yearobj=new Year();
        yearobj.setYearId(-1);
        yearobj.setYearValue("Year");
        yearList.add(yearobj);


        Model model=new Model();
        model.setBrandId(-1);
        model.setModelName("Model");
        modelList.add(model);

        Brand brand=new Brand();
        brand.setBrandId(-1);
        brand.setBrandName("Make");
        brandList.add(brand);

        spYear.setAdapter(new Info_year_adapter(this,spinerchild_layout,yearList));
        spModel.setAdapter(new Info_model_adapter(this,spinerchild_layout,modelList));
        spMake.setAdapter(new Info_brand_adapter(this,spinerchild_layout,brandList));
    }



    public void bindBrandAdapter(ArrayList<Brand> brandLists){
        spMake.setAdapter(new Info_brand_adapter(this,spinerchild_layout,brandLists));
        }
    public void bindYearAdapter(ArrayList<Year> yearLsist){
        spYear.setAdapter(new Info_year_adapter(this,spinerchild_layout,yearLsist));
    }

    public void bindModelAdapter(ArrayList<Model> modelsList){
        spModel.setAdapter(new Info_model_adapter(this,spinerchild_layout,modelList));
    }

    @OnClick(R.id.et_pdate)
    public void onClickPDate(){
        if(!TextUtils.isEmpty(yearName)) {
            openDatePicker(etPdate);
        }else{
            AndroidUtil.showSnackBarSafe(etBikeName,"Please Select year before selecting Purchase date.");
            return;
        }

    }
    private void openDatePicker(EditText editText) {
        (new DatePickerFragment(editText)).show(getFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        EditText edittext;


        int yearN=Integer.parseInt(yearName);


        public DatePickerFragment() {
        }

        public DatePickerFragment(EditText editText) {
            this.edittext = editText;
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Calendar minCal = new GregorianCalendar(yearN+1, 1, 1);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                    year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            calendar.set(year, month, day);
            edittext.setText(twoDigit(month + 1) + "/" + twoDigit(day) + "/" + String
                    .valueOf(year));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }


        //  Picasso.with(ProfileActivity.this).load(data.getData()).centerCrop().fit().into((ImageView) findViewById(R.id.iv_profile));

        switch (requestCode) {
            case ImagePickerUtils.SELECT_IMAGE_ACTIVITY_REQUEST_CODE: {
                if (data == null) {
                    return;
                }
                Uri uri = data.getData();

                imagePath = new File(AndroidUtil.getRealPathFromURI(this, uri, true));
//ivUpload.setBackgroundResource(R.drawable.round_corner);
                GlideManager.loadImage(this,imagePath.toString(),sdBike);


                pd.show();
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {

                            uploadImageToAWS(imagePath.toString());


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

                if(imagePath!=null){
                    //ivUpload.setBackgroundResource(R.drawable.round_corner);
                    GlideManager.loadImage(this,imagePath.toString(),sdBike);
                    pd.show();
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                uploadImageToAWS(imagePath.toString());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                }
//                FrescoManager.getInstance(this).displayImageFromFile(ivUpload, imagePath);
//                FrescoManager.getInstance(this).setCircleImage(ivUpload, 000);
                break;

            }
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
    @OnClick(R.id.btn_next)
    public void onClickNext(){


        String bikeName=etBikeName.getText().toString();
        String VIn=etVin.getText().toString();
        String etdate=etPdate.getText().toString();
        String etReading=etOdoMeterTReading.getText().toString();



       // String brandName=spMake.getSelectedItem().toString();



       // String yearName=spYear.getSelectedItem().toString();
//        if(yearList.get(spYear.getSelectedItemPosition()).getYearId()>0){
//
//        }
//       if(modelList.get(spModel.getSelectedItemPosition()).getModelId()>0){
//
//       }
//       if(brandList.get(spMake.getSelectedItemPosition()-1).getBrandId()>0){
//
//       }

//        if(TextUtils.isEmpty(imageUrl)){
//            AndroidUtil.showSnackBarSafe(etPdate,"Please upload Image");
//            return;
//        }

//        if(TextUtils.isEmpty(bikeName)){
//            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
//            return;
//        }
//        if(TextUtils.isEmpty(VIn)){
//            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
//            return;
//        }
//        if (TextUtils.isEmpty(etdate)) {
//
//            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
//            return;
//        }
//        if(TextUtils.isEmpty(etReading)){
//            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
//            return;
//        }
        if(spYear.getSelectedItemPosition()==0){
            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
            return;
        }
        if(spMake.getSelectedItemPosition()==0){
            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
            return;
        }
        if(spModel.getSelectedItemPosition()==0){
            AndroidUtil.showSnackBarSafe(etPdate,"Please fill all fields");
            return;
        }

        int brandId=brandList.get(spMake.getSelectedItemPosition()).getBrandId();
        int yearId=yearList.get(spYear.getSelectedItemPosition()).getYearId();
        String yearName=yearList.get(spYear.getSelectedItemPosition()).getYearValue();
        int modelId=modelList.get(spModel.getSelectedItemPosition()).getModelId();

        CreateBikeRequest createBikeRequest=new CreateBikeRequest();

        createBikeRequest.setMake(brandId);
        createBikeRequest.setModel(modelId);
        createBikeRequest.setYear(yearName);
        createBikeRequest.setName(bikeName);
        createBikeRequest.setVIN(VIn);
        createBikeRequest.setPurchaseDate(etdate);
        createBikeRequest.setImage(imageUrl);
        createBikeRequest.setCycleId(0);
        if(TextUtils.isEmpty(etReading)){
            createBikeRequest.setOdometerReading(0);
        }
        else{
            createBikeRequest.setOdometerReading(Integer.parseInt(etReading));
        }


        createBikeRequest.setOwnerId(Integer.toString(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID)));
        mPresenter.createBike(createBikeRequest);








    }
    public int getBrandid(String brandName){
        int brandId = -1;
        for(int i=0;i<brandList.size();i++){
            if(brandList.get(i).getBrandName().equalsIgnoreCase(brandName)){
                brandId=brandList.get(i).getBrandId();
            }
        }
        return brandId;
    }
    public int getbrandYearid(String yearName){
        int brandYearId = 0;
        for(int i=0;i<yearList.size();i++){
            if(yearList.get(i).getYearValue().equalsIgnoreCase(yearName)){
                brandYearId=yearList.get(i).getBrandYearId();
                Ready2RideLog.getInstance().info("Year",yearName);
                Ready2RideLog.getInstance().info("YearId",""+brandYearId);
            }
        }
        return brandYearId;
    }

    public int getYearid(String yearName){
        int YearId = 0;
        for(int i=0;i<yearList.size();i++){
            if(yearList.get(i).getYearValue().equalsIgnoreCase(yearName)){
                YearId=yearList.get(i).getYearId();
            }
        }
        return YearId;
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
    public void onsucessBrands(ArrayList<Brand> brandsList) {
        if(brandsList.size()!=0){
            for(int i=0;i<brandsList.size();i++){
                Brand brand= new Brand();
                brand=brandsList.get(i);
                brandList.add(brand);
            }
            bindBrandAdapter(brandList);
        }

    }

    @Override
    public void onSucessYears(ArrayList<Year> yearsList) {
        yearList.clear();
        Year yearobj=new Year();
        yearobj.setYearId(-1);
        yearobj.setYearValue("Year");
        yearList.add(yearobj);
        if(yearsList.size()!=0){
            for(int i=0;i<yearsList.size();i++){
                Year year= new Year();
                year=yearsList.get(i);
                yearList.add(year);
            }
            bindYearAdapter(yearList);
        }

    }

    @Override
    public void onSUceesModels(ArrayList<Model> modelsList) {

        modelList.clear();
        Model modelobj=new Model();
        modelobj.setModelId(-1);
        modelobj.setModelName("Model");
        modelList.add(modelobj);
        if(modelsList.size()!=0){
            for(int i=0;i<modelsList.size();i++){
                Model year= new Model();
                year=modelsList.get(i);
                modelList.add(year);
            }
            bindModelAdapter(modelsList);
        }

    }

    @Override
    public void onSucessCreateBike(ArrayList<SignUpResponse> signUpResponses) {
        ActivityUtils.startActivity(this,DefaultDelaerShipActivity.class,null);
        finish();
    }


}

