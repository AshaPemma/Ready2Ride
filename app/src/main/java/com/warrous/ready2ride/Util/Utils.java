package com.warrous.ready2ride.Util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;




@SuppressLint({"InflateParams", "DefaultLocale"})
@SuppressWarnings("deprecation")
public class Utils {
    public static Toast toast;
    public static AlertDialog.Builder alertDialog;

    public static void setToast(Context context, String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    

}
