package com.warrous.ready2ride.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.warrous.ready2ride.framework.PreferenceManager;


/**
 * Util class for checking and asking required permissions
 */

public class PermissionUtils {


    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_CONTACTS = 2;
    public static final int REQUEST_LOCATIONS = 5;

    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String[] PERMISSIONS_CONTACTS = {
            Manifest.permission.READ_CONTACTS
    };

    private static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (!showRationale && !isFirstTimeAskingPermission(activity)) {
//                promptSettings(activity,false);
//            } else {
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
//                firstTimeAskingStoragePermission(activity);
//            }
        }
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean verifyStoragePermissions(Fragment fragment) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (!showRationale && !isFirstTimeAskingPermission(fragment.getActivity())) {
//                promptSettings(fragment.getActivity(),false);
//            } else {
                fragment.requestPermissions(
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
//                firstTimeAskingStoragePermission(fragment.getActivity());
//            }
        }
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean verifyCameraStoragePermissions(Fragment fragment) {
        int cameraPermission = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED
                || storagePermission != PackageManager.PERMISSION_GRANTED) {

            boolean showStorageRationale = ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
            boolean showCameraRationale = ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(),Manifest.permission.CAMERA);

//            if(!showCameraRationale && !isFirstTimeAskingPermission(fragment.getActivity()) && cameraPermission != PackageManager.PERMISSION_GRANTED) {
//                promptSettings(fragment.getActivity(), true);
//            } else if(!showStorageRationale && !isFirstTimeAskingStoragePermission(fragment.getActivity()) && storagePermission != PackageManager.PERMISSION_GRANTED) {
//                promptSettings(fragment.getActivity(), false);
//            } else {
                fragment.requestPermissions(
                        PERMISSIONS_CAMERA,
                        REQUEST_CAMERA
                );
//                firstTimeAskingPermission(fragment.getActivity());
//                firstTimeAskingStoragePermission(fragment.getActivity());
//            }
        }
        return (cameraPermission == PackageManager.PERMISSION_GRANTED
                && storagePermission == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean verifyCameraStoragePermissions(Activity activity) {
        int cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED
                || storagePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    PERMISSIONS_CAMERA,
                    REQUEST_CAMERA
            );
        }
        return (cameraPermission == PackageManager.PERMISSION_GRANTED
                && storagePermission == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasCameraPermissions(Activity activity) {
        int cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (cameraPermission == PackageManager.PERMISSION_GRANTED &&
                storagePermission == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean verifyContactsPermissions(Activity activity) {
        int contactsPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        if (contactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    PERMISSIONS_CONTACTS,
                    REQUEST_CONTACTS
            );
        }
        return (contactsPermission == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean verifyLocationPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION,
                    REQUEST_LOCATIONS
            );
//            boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION);
//            if (!showRationale) {
////                promptSettings(activity,activity.getString(R.string.permission_locations));
//            } else {
//
////                firstTimeAskingLocationPermission(activity);
//            }
        }
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private static void promptSettings(final Activity activity, boolean isCamera) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        Resources resources = activity.getResources();
////        builder.setTitle(resources.getString(R.string.settings_title));
//        builder.setMessage(resources.getString(!isCamera ? R.string.photos_permission : R.string.camera_permission));
//        builder.setNegativeButton(resources.getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                goToSettings(activity);
//            }
//        });
//        builder.setCancelable(false);
//        builder.setPositiveButton(resources.getString(R.string.dismiss), null);
//        builder.show();
    }

    private static void goToSettings(Activity activity) {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(myAppSettings);
    }
//
//    private static void firstTimeAskingPermission(Context context){
//        PreferenceManager.storeValue(context,PreferenceManager.PERMISSIONS, false);
//    }
//    private static void firstTimeAskingStoragePermission(Context context){
//        PreferenceManager.storeValue(context,PreferenceManager.STORAGE_PERMISSIONS, false);
//    }
//
//    private static boolean isFirstTimeAskingPermission(Context context){
//        return PreferenceManager.getBooleanValue(context,PreferenceManager.PERMISSIONS,true);
//    }
//
//    private static boolean isFirstTimeAskingStoragePermission(Context context){
//        return PreferenceManager.getBooleanValue(context,PreferenceManager.STORAGE_PERMISSIONS,true);
//    }

    public static boolean isUserLogedIn(Context context) {
        boolean userLogedInStatus = true;
        String token = PreferenceManager.getStringValue(context, PreferenceManager.KEY_TOKEN);
        if (token == null || token.isEmpty()) {
            userLogedInStatus = false;
        }
        return userLogedInStatus;
    }
}
