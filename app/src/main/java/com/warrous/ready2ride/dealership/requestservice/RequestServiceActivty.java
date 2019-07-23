package com.warrous.ready2ride.dealership.requestservice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.dealership.adapter.info_request_service_adapter;
import com.warrous.ready2ride.dealership.model.RequestServiceRequest;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class RequestServiceActivty extends BaseActivity  implements RequestServiceContract.View{

    @BindView(R.id.spiner_service_type)
    Spinner spServiceType;
    @BindView(R.id.spiner_bike)
    Spinner spBike;
    @BindView(R.id.spiner_available_time)
    Spinner spTime;


    @BindView(R.id.et_pdate)
    EditText etDate;
    static Calendar calendar;



    RequestServiceContract.Presenter mPresenter;
    ArrayList<ServiceTypeResponse> serviceTypeList;
    ArrayList<DefaultBikeDetailsResponse> bikesList;


    int spinerchild_layout;
    ArrayList<String> serviceName;
    ArrayList<String> availableTime;

    private static String twoDigit(int val) {
        if (val < 10)
            return "0" + val;
        else
            return "" + val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_request_service);
        injectButterKnife(this);
        mPresenter=new RequestServicePresenter(this);
        spinerchild_layout = R.layout.raw_bikemodel_text;
        calendar=Calendar.getInstance();
        bindSpinnerAdapter();
        bindAdapter();


    }
    @OnClick(R.id.et_pdate)
    public void onClickDate(){
        openDatePicker(etDate);
    }
    private void bindAdapter() {
        bikesList = new ArrayList<>();



        DefaultBikeDetailsResponse bikeobj=new DefaultBikeDetailsResponse();
        bikeobj.setCycleId(-1);
        bikeobj.setCycleName("Select Bike");
        bikesList.add(bikeobj);
        spBike.setAdapter(new info_request_service_adapter(this,spinerchild_layout,bikesList));

    }
    public void bindSpinnerAdapter(){
        serviceTypeList=new ArrayList<>();
        serviceName=new ArrayList<>();
        availableTime=new ArrayList<>();

        ServiceTypeResponse serviceTypeResponse = new ServiceTypeResponse();
        serviceTypeResponse.setServiceCategoryId(-1);
        serviceTypeResponse.setServiceCategoryName("Service Type");
        serviceName.add(serviceTypeResponse.getServiceCategoryName());
        availableTime.add("Select Available Time");


        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,serviceName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spServiceType.setAdapter(arrayAdapter);
        spServiceType.setSelection(0);

        ArrayAdapter arrayAdapterT = new ArrayAdapter<>(this, R.layout.spinner_item,availableTime);
        arrayAdapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTime.setAdapter(arrayAdapterT);
        spTime.setSelection(0);

        mPresenter.getServiceTypes(0);
        mPresenter.getCycleDetails(0,PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));


    }
    @OnClick(R.id.tv_cancel)
    public void onClickCcancel(){
        finish();

    }
    @OnClick(R.id.btn_appointment)
    public void onClickAppointment(){
        String date=etDate.getText().toString();

        if(spBike.getSelectedItemPosition()==0){
            AndroidUtil.showSnackBarSafe(etDate,"Please select Bike");
            return;
        }
        if(spServiceType.getSelectedItemPosition()==0){
            AndroidUtil.showSnackBarSafe(etDate,"Please select ServiceType");
            return;
        }
        if(TextUtils.isEmpty(date)){
            AndroidUtil.showSnackBarSafe(etDate,"Please select Date");
            return;
        }
        RequestServiceRequest requestServiceRequest=new RequestServiceRequest();
        requestServiceRequest.setCycleServiceId(0);
        requestServiceRequest.setCycleId(bikesList.get(spBike.getSelectedItemPosition()).getCycleId());
        requestServiceRequest.setDealerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_DEFAUKT_DEALER_ID));
        requestServiceRequest.setDate(date);
        requestServiceRequest.setServiceCategoryId(serviceTypeList.get(spServiceType.getSelectedItemPosition()-1).getServiceCategoryId());
        requestServiceRequest.setTime("2:10");

        mPresenter.saveAppointment(requestServiceRequest);

    }


    @Override
    public void onServiceTypeResponseSucess(ArrayList<ServiceTypeResponse> segmentResponses) {
        serviceTypeList=new ArrayList<>();
        serviceTypeList=segmentResponses;
        serviceName=new ArrayList<>();
        ServiceTypeResponse serviceTypeResponse = new ServiceTypeResponse();
        serviceTypeResponse.setServiceCategoryId(-1);
        serviceTypeResponse.setServiceCategoryName("Service Type");
        serviceName.add(serviceTypeResponse.getServiceCategoryName());

        for (int i=0;i<serviceTypeList.size();i++){
            serviceName.add(serviceTypeList.get(i).getServiceCategoryName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,serviceName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spServiceType.setAdapter(arrayAdapter);
        spServiceType.setSelection(0);
    }

    public void bindBikeAdapter(ArrayList<DefaultBikeDetailsResponse> brandLists){
        spBike.setAdapter(new info_request_service_adapter(this,spinerchild_layout,bikesList)) ;
    }
    @Override
    public void onCycleDetailsSucess(ArrayList<DefaultBikeDetailsResponse> segmentResponses) {

        if(segmentResponses.size()!=0){
            for(int i=0;i<segmentResponses.size();i++){
                 DefaultBikeDetailsResponse brand= new DefaultBikeDetailsResponse();
                brand=segmentResponses.get(i);
                bikesList.add(brand);
            }
            bindBikeAdapter(bikesList);
        }
    }

    @Override
    public void onSaveAppointmentsucess(ArrayList<SignUpResponse> signUpResponses) {
        finish();
    }

    private void openDatePicker(EditText editText) {
        (new DatePickerFragment(editText)).show(getFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        EditText edittext;


       // int yearN=Integer.parseInt(yearName);


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
          //  Calendar minCal = new GregorianCalendar(yearN+1, 1, 1);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                    year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
           // datePickerDialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            calendar.set(year, month, day);
            edittext.setText(twoDigit(month + 1) + "/" + twoDigit(day) + "/" + String
                    .valueOf(year));
        }
    }

}
