package com.warrous.ready2ride.alerts.createalerts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.alerts.AlertsActivity;
import com.warrous.ready2ride.alerts.createalerts.model.CreateAlertRequest;
import com.warrous.ready2ride.alerts.model.AlertListResponse;
import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;


    public class CreateNewAlertEdit extends BaseActivity implements CreateNewAlertContract.View{

        @BindView(R.id.spiner_model)
        Spinner spServiceType;

        @BindView(R.id.spiner_months)
        Spinner spMonths;

        @BindView(R.id.spiner_week)
        Spinner spWeek;

        @BindView(R.id.rg_time)
        RadioGroup rgTime;

        @BindView(R.id.tv_miles)
        TextView tvMiles;
        @BindView(R.id.tv_miles1)
        TextView tvMiles1;
        @BindView(R.id.et_miles)
        EditText etMiles;
        @BindView(R.id.et_pdate)
        EditText etPdate;

        @BindView(R.id.et_num)
        EditText etNum;
        @BindView(R.id.et_numm)
        EditText etNumm;
        @BindView(R.id.tv_every)
        TextView tvEvery;
        @BindView(R.id.tv_everym)
        TextView tvEverym;

        @BindView(R.id.rl_months)
        RelativeLayout rlMonths;
        @BindView(R.id.rl_week)
        RelativeLayout rlWeek;
        @BindView(R.id.rl_weekem)
        RelativeLayout rlWeekm;
        @BindView(R.id.tv_reminder)
        TextView tvReminder;
        @BindView(R.id.tv_reminderm)
        TextView tvReminderm;
        @BindView(R.id.rb_time)
        RadioButton rbTime;
        @BindView(R.id.rb_mileage)
        RadioButton rbMiles;
        @BindView(R.id.spiner_weekm)
        Spinner spWeekm;
        static Calendar calendar;
        private static String twoDigit(int val) {
            if (val < 10)
                return "0" + val;
            else
                return "" + val;
        }

        int servicecatPos,reminderPos,serviceCatId;
        String lastServiced,every,reminder;
        String weekArray[],weekArraym[];








        CreateNewAlertContract.Presenter mPresenter;
        ArrayList<ServiceTypeResponse> serviceTypeList;
        ArrayList<String> serviceName;
        CreateAlertRequest createAlertRequest;
        String alertBy;
        AlertListResponse alertListResponse;




        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_new_alert);
            injectButterKnife(this);
            mPresenter= new CreateNewAlertPresenter(this);

            alertListResponse= (AlertListResponse) getIntent().getSerializableExtra("alertResponse");

            serviceCatId=alertListResponse.getServiceCategoryId();
            lastServiced=alertListResponse.getLastServiced();
            every=alertListResponse.getEvery();
            reminder=alertListResponse.getRemainder();






            ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.reminder));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spWeek.setAdapter(arrayAdapter);
           // spWeek.setSelection(0);

            weekArray=getResources().getStringArray(R.array.reminder);
            ArrayAdapter arrayAdapterMonths = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.months));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMonths.setAdapter(arrayAdapterMonths);
           // spMonths.setSelection(0);



//            rgTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                    switch (checkedId)
//                    {
//                        case R.id.rb_time:
//                            alertBy="Time";
//                            rlMonths.setVisibility(View.VISIBLE);
//                            tvReminder.setVisibility(View.VISIBLE);
//                            tvReminderm.setVisibility(View.GONE);
//                            rlWeek.setVisibility(View.VISIBLE);
//                            rlWeekm.setVisibility(View.GONE);
//                            etPdate.setVisibility(View.VISIBLE);
//                            tvMiles.setVisibility(View.GONE);
//                            tvMiles1.setVisibility(View.GONE);
//                            etMiles.setVisibility(View.GONE);
//                            etNum.setVisibility(View.VISIBLE);
//                            etNumm.setVisibility(View.GONE);
//                            tvEvery.setVisibility(View.VISIBLE);
//                            tvEverym.setVisibility(View.GONE);
//                            break;
//
//                        case R.id.rb_mileage:
//                            alertBy="Miles";
//                            rlMonths.setVisibility(View.GONE);
//                            rlWeek.setVisibility(View.GONE);
//                            rlWeekm.setVisibility(View.VISIBLE);
//                            etPdate.setVisibility(View.GONE);
//                            tvMiles.setVisibility(View.VISIBLE);
//                            tvMiles1.setVisibility(View.VISIBLE);
//                            etMiles.setVisibility(View.VISIBLE);
//                            etNum.setVisibility(View.GONE);
//                            etNumm.setVisibility(View.VISIBLE);
//                            tvEvery.setVisibility(View.GONE);
//                            tvReminderm.setVisibility(View.VISIBLE);
//                            tvReminder.setVisibility(View.GONE);
//                            tvEverym.setVisibility(View.VISIBLE);
//                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.reminder_miles));
//                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            spWeekm.setAdapter(arrayAdapter);
//                            spWeekm.setSelection(0);
//                            break;
//                    }
//                }
//            });

            bindSpinnerAdapter();

        }
        public void setValues(){
            if(alertListResponse.getAlertBy().equalsIgnoreCase("Time")){
                rbTime.setChecked(true);
                rbMiles.setEnabled(false);
                alertBy="Time";
                rlMonths.setVisibility(View.VISIBLE);
                tvReminder.setVisibility(View.VISIBLE);
                rlWeek.setVisibility(View.VISIBLE);
                etPdate.setVisibility(View.VISIBLE);
                etNum.setVisibility(View.VISIBLE);
                tvEvery.setVisibility(View.VISIBLE);

                etNum.setText(every);
                etPdate.setText(lastServiced);
                spServiceType.setSelection(serviceCatId-1);
                if(weekArray.length!=0){
                    for(int i=0;i<weekArray.length;i++){
                        String name=weekArray[i];
                        if(weekArray[i].contains(reminder)){
                            spWeek.setSelection(i);
                            return;

                        }
                    }
                }



                rlWeekm.setVisibility(View.GONE);
                tvMiles.setVisibility(View.GONE);
                tvMiles1.setVisibility(View.GONE);
                etMiles.setVisibility(View.GONE);
                etNumm.setVisibility(View.GONE);
                tvEverym.setVisibility(View.GONE);
                tvReminderm.setVisibility(View.GONE);
            }else{
                rbMiles.setChecked(true);
                rbTime.setEnabled(false);
                alertBy="Miles";
                rlMonths.setVisibility(View.GONE);
                rlWeek.setVisibility(View.GONE);
                etNum.setVisibility(View.GONE);
                etPdate.setVisibility(View.GONE);
                tvEvery.setVisibility(View.GONE);
                tvReminder.setVisibility(View.GONE);

                tvMiles.setVisibility(View.VISIBLE);
                tvMiles1.setVisibility(View.VISIBLE);
                etMiles.setVisibility(View.VISIBLE);
                etNumm.setVisibility(View.VISIBLE);
                rlWeekm.setVisibility(View.VISIBLE);
                tvReminderm.setVisibility(View.VISIBLE);
                tvEverym.setVisibility(View.VISIBLE);

                etNumm.setText(every);
                etMiles.setText(lastServiced);
                spServiceType.setSelection(serviceCatId-1);

                ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.reminder_miles));
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spWeekm.setAdapter(arrayAdapter);
                weekArraym=getResources().getStringArray(R.array.reminder_miles);

                if(weekArraym.length!=0){
                    for(int i=0;i<weekArraym.length;i++){
                        if(weekArraym[i].contains(reminder)){
                            spWeekm.setSelection(i);
                            return;

                        }
                    }
                }



            }
        }

        public void bindSpinnerAdapter(){
            serviceTypeList=new ArrayList<>();
            serviceName=new ArrayList<>();

            ServiceTypeResponse serviceTypeResponse = new ServiceTypeResponse();
            serviceTypeResponse.setServiceCategoryId(-1);
            serviceTypeResponse.setServiceCategoryName("Service Type");
            serviceName.add(serviceTypeResponse.getServiceCategoryName());

            ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,serviceName);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spServiceType.setAdapter(arrayAdapter);
            spServiceType.setSelection(0);

            mPresenter.getServiceTypes(0);



        }
        @OnClick(R.id.et_pdate)
        public void onClickDate(){
            openDatePicker(etPdate);
        }
        @OnClick(R.id.tv_cancel)
        public void onClickCancel(){
            toActivity();
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            toActivity();
        }

        public void toActivity(){
            ActivityUtils.startActivity(this,AlertsActivity.class,null);
            finish();
        }

        @Override
        public void onServiceTypeResponseSucess(ArrayList<ServiceTypeResponse> segmentResponses) {
            serviceTypeList=new ArrayList<>();
            serviceTypeList=segmentResponses;
            serviceName=new ArrayList<>();
            for (int i=0;i<serviceTypeList.size();i++){
                serviceName.add(serviceTypeList.get(i).getServiceCategoryName());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,serviceName);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spServiceType.setAdapter(arrayAdapter);
            setValues();
           // spServiceType.setSelection(0);
        }

        @Override
        public void onCreateALertSucess(ArrayList<SignUpResponse> signUpResponses) {
            ActivityUtils.startActivityFinish(this,AlertsActivity.class,null);


        }

        @OnClick(R.id.btn_save)
        public void onClickSave(){

            createAlertRequest=new CreateAlertRequest();
            createAlertRequest.setAlertBy(alertBy);
            createAlertRequest.setAlertId(alertListResponse.getAlertId());
            createAlertRequest.setOwnerId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));

            createAlertRequest.setServiceCategoryId(serviceTypeList.get(spServiceType.getSelectedItemPosition()).getServiceCategoryId());

            if(alertBy.equalsIgnoreCase("Miles")){
                if(TextUtils.isEmpty(etNumm.getText().toString())){
                    AndroidUtil.showSnackBarSafe(this,"Please provide Months");
                    return;

                }
                if(TextUtils.isEmpty(etMiles.getText().toString())){
                    AndroidUtil.showSnackBarSafe(this,"Please provide Miles");
                    return;

                }
                createAlertRequest.setEvery(etNumm.getText().toString());
                createAlertRequest.setRemainder(spWeekm.getSelectedItem().toString());
                createAlertRequest.setLastServiced(etMiles.getText().toString());

            }else
            {
                if(TextUtils.isEmpty(etNum.getText().toString())){
                    AndroidUtil.showSnackBarSafe(this,"Please provide Months");
                    return;

                }
                if(TextUtils.isEmpty(etPdate.getText().toString())){
                    AndroidUtil.showSnackBarSafe(this,"Please provide Date");
                    return;

                }
                createAlertRequest.setEvery(etNum.getText().toString());
                createAlertRequest.setLastServiced(etPdate.getText().toString());
                createAlertRequest.setRemainder(spWeek.getSelectedItem().toString());

            }
            mPresenter.createAlert(createAlertRequest);

        }

        private void openDatePicker(EditText editText) {
            (new com.warrous.ready2ride.alerts.createalerts.CreateNewAlertActivity.DatePickerFragment(editText)).show(getFragmentManager(), "datePicker");
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
                Calendar minCal = new GregorianCalendar(1, 1, 1);

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
    }


