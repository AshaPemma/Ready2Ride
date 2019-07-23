/*
 * Copyright [2017] [LetsMobility Software Pvt Ltd]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.warrous.ready2ride.framework;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.ValidationUtil;
import com.warrous.ready2ride.rides.RideSummaryActivity;


/**
 * Created by sivaprasadg on 7/21/17.
 */

public final class AlertUtil {


    private static AlertDialog createDialog(Context context, String title, String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (title != null) {
            // Setting Dialog Title
            alertDialog.setTitle(title);
        }

        if (msg != null) {
            // Setting Dialog Message
            alertDialog.setMessage(msg);
        }

        return alertDialog;
    }

    public static void showSimpleAlert(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = createDialog(context, title, message);
        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", listener);

        // Showing Alert Message
        alertDialog.show();
    }

    public static void showCustomAlert(View customView, final View.OnClickListener listener) {
      //  showCustomAlert(customView, null, listener);
    }



    public static void showSaveAlert(View customView, String message, final View.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(customView.getContext());
        builder.setView(customView);
        final AlertDialog alertDialog = builder.create();
        TextView textHead;
        textHead=customView.findViewById(R.id.tv_desc);
        textHead.setText(message);
        customView.findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                listener.onClick(v);
            }
        });

        customView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    public static void showSaveRideDialog(final Activity context, final OnSaveRideClick passwordDialogClick) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.dialog_save_ride, null, false);

        dialogBuilder.setView(customView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText etName=customView.findViewById(R.id.et_ride_name);

//        final TextInputLayoutWrapper tilPwd = customView.findViewById(R.id.til_pwd);
//        final TextInputLayoutWrapper tilRenterPwd = customView.findViewById(R.id.til_reenter_pwd);
//        final TextInputLayoutWrapper tilOldPwd = customView.findViewById(R.id.til_old_pwd);
//        final TextView tilError = customView.findViewById(R.id.tv_server_error);

        customView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              AndroidUtil.hideKeyBoard(context, etName);
               String name = etName.getText().toString();
                    if (ValidationUtil.isEmpty(name)) {
AndroidUtil.showSnackBarSafe(etName,"Please provide RideName");
return;
                    }


                passwordDialogClick.onClick(name, alertDialog);            }

        });
        customView.findViewById(R.id.iv_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.hideKeyBoard(context);
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }


    public static void showRideSavedDialog(final Activity context, final OnRideSavedClick passwordDialogClick) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.dialog_ride_saved, null, false);

        dialogBuilder.setView(customView);
        final AlertDialog alertDialog = dialogBuilder.create();

//        final TextInputLayoutWrapper tilPwd = customView.findViewById(R.id.til_pwd);
//        final TextInputLayoutWrapper tilRenterPwd = customView.findViewById(R.id.til_reenter_pwd);
//        final TextInputLayoutWrapper tilOldPwd = customView.findViewById(R.id.til_old_pwd);
//        final TextView tilError = customView.findViewById(R.id.tv_server_error);

        customView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AndroidUtil.hideKeyBoard(context, tilPwd);
//                String pwd = tilPwd.getText().toString();
//                String rePwd = tilRenterPwd.getText().toString();
//                String oldPwd = tilOldPwd.getText().toString();
//                if ((oldPwd.isEmpty() || oldPwd.contains("  ")) || (pwd.isEmpty() || pwd.contains("  ")) || (rePwd.isEmpty() || rePwd.contains("  "))) {
//
//                    if (ValidationUtil.isEmpty(oldPwd)) {
//                        tilError.setText("");
//                        tilOldPwd.setError("Please enter Current Password");
//                    }
//                    if (ValidationUtil.isEmpty(pwd)) {
//                        tilPwd.setError("Please enter Password");
//                        tilError.setText("");
//                    }
//                    if (ValidationUtil.isEmpty(rePwd)) {
//                        tilRenterPwd.setError("Please enter Confirm Password");
//                        tilError.setText("");
//                    }
//
//                    return;
//                }
////
//                if (!ValidationUtil.isValidPassword(oldPwd)) {
//                    tilOldPwd.setError(context.getText(R.string.on_valid_password_error));
//                    tilError.setText("");
//                    return;
//                }
//
//                if (!ValidationUtil.isValidPassword(pwd)) {
//                    tilPwd.setError(context.getText(R.string.on_valid_password_error));
//                    tilError.setText("");
//                    return;
//                }
//                if (!ValidationUtil.isValidPassword(rePwd)) {
//                    tilRenterPwd.setError(context.getText(R.string.on_valid_password_error));
//                    tilError.setText("");
//                    return;
//                }
//                if (!pwd.equalsIgnoreCase(rePwd)) {
//                    tilError.setText("");
//                    tilRenterPwd.setError("Password and Confirm Password should match");
//                    return;
//                }
                passwordDialogClick.onClick(alertDialog);


            }
        });
//        customView.findViewById(R.id.iv_cross).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AndroidUtil.hideKeyBoard(context);
//                alertDialog.dismiss();
//            }
//        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void showOdometerUpdateDialog(final RideSummaryActivity context,String odometer,final OnOdometerUpdtaeClick passwordDialogClick) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.dialog_update_odometer, null, false);

        dialogBuilder.setView(customView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText etOdometerreading=customView.findViewById(R.id.et_ride_name);

//        final TextInputLayoutWrapper tilPwd = customView.findViewById(R.id.til_pwd);
//        final TextInputLayoutWrapper tilRenterPwd = customView.findViewById(R.id.til_reenter_pwd);
//        final TextInputLayoutWrapper tilOldPwd = customView.findViewById(R.id.til_old_pwd);
//        final TextView tilError = customView.findViewById(R.id.tv_server_error);
       // String number = String.valueOf(num);
        String newOR=String.format("%.1f",Double.parseDouble(odometer));
        etOdometerreading.setText(newOR);

        customView.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AndroidUtil.hideKeyBoard(context, etOdometerreading);
                String name = etOdometerreading.getText().toString();
                if (ValidationUtil.isEmpty(name)) {
                    AndroidUtil.showSnackBarSafe(etOdometerreading,"Please provide Odometer Reading");
                    return;
                }
//                AndroidUtil.hideKeyBoard(context, tilPwd);
//                String pwd = tilPwd.getText().toString();
//                String rePwd = tilRenterPwd.getText().toString();
//                String oldPwd = tilOldPwd.getText().toString();
//                if ((oldPwd.isEmpty() || oldPwd.contains("  ")) || (pwd.isEmpty() || pwd.contains("  ")) || (rePwd.isEmpty() || rePwd.contains("  "))) {
//
//                    if (ValidationUtil.isEmpty(oldPwd)) {
//                        tilError.setText("");
//                        tilOldPwd.setError("Please enter Current Password");
//                    }
//                    if (ValidationUtil.isEmpty(pwd)) {
//                        tilPwd.setError("Please enter Password");
//                        tilError.setText("");
//                    }
//                    if (ValidationUtil.isEmpty(rePwd)) {
//                        tilRenterPwd.setError("Please enter Confirm Password");
//                        tilError.setText("");
//                    }
//
//                    return;
//                }
////
//                if (!ValidationUtil.isValidPassword(oldPwd)) {
//                    tilOldPwd.setError(context.getText(R.string.on_valid_password_error));
//                    tilError.setText("");
//                    return;
//                }
//
//                if (!ValidationUtil.isValidPassword(pwd)) {
//                    tilPwd.setError(context.getText(R.string.on_valid_password_error));
//                    tilError.setText("");
//                    return;
//                }
//                if (!ValidationUtil.isValidPassword(rePwd)) {
//                    tilRenterPwd.setError(context.getText(R.string.on_valid_password_error));
//                    tilError.setText("");
//                    return;
//                }
//                if (!pwd.equalsIgnoreCase(rePwd)) {
//                    tilError.setText("");
//                    tilRenterPwd.setError("Password and Confirm Password should match");
//                    return;
//                }
                passwordDialogClick.onClick(name,alertDialog);


            }
        });
        customView.findViewById(R.id.iv_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.hideKeyBoard(context);
                alertDialog.dismiss();
            }
        });

        customView.findViewById(R.id.tv_dont_ask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.showDialogOdometer(alertDialog);
              //  dontAskAgainClick.onClick(alertDialog);
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public static void showOdometerOptDialog(final Activity context) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.dialog_odometer_opted, null, false);

        dialogBuilder.setView(customView);
        final AlertDialog alertDialog = dialogBuilder.create();

        customView.findViewById(R.id.iv_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.hideKeyBoard(context);
                alertDialog.dismiss();
                context.finish();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public interface OnSaveRideClick {
        void onClick(String saveRide, AlertDialog dialog);
    }

    public interface OnRideSavedClick {
        void onClick(AlertDialog dialog);
    }

    public interface OnOdometerUpdtaeClick {
        void onClick(String odometerReading, AlertDialog dialog);
    }

//    public interface OnPasswordDialogClick {
//        void onClick(String pwd, String rePwd, String oldPwd, TextInputLayoutWrapper Pwd, TextView tvError, AlertDialog dialog);
//    }


}
