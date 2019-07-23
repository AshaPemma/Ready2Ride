package com.warrous.ready2ride.framework;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.warrous.ready2ride.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Utility {
//    public static List<DashBoardTopBarPannel> getTopBarPanelElements(int value)
//    {
//        List<DashBoardTopBarPannel> list = new ArrayList<>();
//        switch (value)
//        {
//
//            case 0:
//                // delivery vehicle
//                list.add(new DashBoardTopBarPannel("Sent Emails","",true));
//                list.add(new DashBoardTopBarPannel("Scheduled Emails","",false));
//                list.add(new DashBoardTopBarPannel("Draft Emails","",false));
//                break;
//
//            case 1:
//                // delivery vehicle
//                list.add(new DashBoardTopBarPannel("Segment Lists","",true));
//                list.add(new DashBoardTopBarPannel("Active Consumers","",false));
//                break;
//        }
//
//        return list;
//    }
    public static ProgressDialog generateProgressDialog(Context activity)
    {
        try {

            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            return progressDialog;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeProgressDialog(ProgressDialog progressDialog)
    {
        try
        {
            if(progressDialog!=null&&progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void showToast(Context c, String t, String s, boolean duration) {
        if (c == null) return;
        Toast tst = Toast.makeText(c, t, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        tst.setText(s);
        tst.show();
    }

    public static void showToast(Context c, String t, String s, boolean duration, String response) {
        if (c == null) return;
        Toast tst = Toast.makeText(c, t, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("error")) {
                tst.setText(jsonObject.getString("error").toString());
            }
            else if(jsonObject.has("message")){
                tst.setText(jsonObject.getString("message").toString());
            }
            else {
                tst.setText(s);
            }
            tst.show();
        } catch (Exception e) {
            Log.d("[Exception]", e.toString());
        }
    }

}