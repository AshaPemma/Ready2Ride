package com.warrous.ready2ride.framework;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class ActivityUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void pushFragment(@NonNull Activity activity,
                                    @NonNull int container,@NonNull Fragment fragment){
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        transaction.replace(container,fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    public static void clearBackStack(@NonNull Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++)
            fm.popBackStack();
    }
    public static void popBackStack(@NonNull Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        fm.popBackStack();
    }

    public static void startActivity(Activity context, Class<?> targetActivity, Bundle bundle){
        Intent intent = new Intent(context,targetActivity);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    public static void startActivityFinish(Activity context, Class<?> targetActivity, Bundle bundle){
        Intent intent = new Intent(context,targetActivity);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        context.finish();
    }
    public static void startActivityForResult(Activity context, Class<?> targetActivity,int requestCode, Bundle bundle){
        Intent intent = new Intent(context,targetActivity);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent,requestCode);
    }


}
