package com.warrous.ready2ride.framework;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class AndroidUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * @param activity
     */
    public static void hideKeyBoard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (view == null) {

            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            } else {
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView()
                        .getWindowToken(), 0);
            }
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * @param activity
     */
    public static void hideKeyBoard(Activity activity) {
        hideKeyBoard(activity, null);
    }

    public static void showKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (editText != null) {
            imm.showSoftInput(editText, 0);
        }
    }

    /**
     * Converts dpi into pixels.
     *
     * @param context
     * @param dp
     * @return integer value
     */
    public static int convertDpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp / (metrics.densityDpi / 160f));
    }

    public static int getDisplayWidth(Context context) {
        if (context != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels;
        }
        return 0;
    }

    // DeviceDimensionsHelper.getDisplayHeight(context) => (display height in pixels)
    public static int getDisplayHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, String msg, String hexaColorCode) {
        if (view == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        if (hexaColorCode != null) {
            snackBar.getView().setBackgroundColor(Color.parseColor(hexaColorCode));
        }
        snackBar.show();
    }

    public static void showSnackBar(View view, String msg, String hexaColorCode, Snackbar.Callback callback) {
        if (view == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        if (hexaColorCode != null) {
            snackBar.getView().setBackgroundColor(Color.parseColor(hexaColorCode));
        }
        snackBar.show();
        if (callback != null)
            snackBar.addCallback(callback);
    }


    public static void showSnackBar(View view, String msg) {
        showSnackBar(view, msg, null, null);
    }

    public static void showSnackBarSafe(final View view, final String msg) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showSnackBar(view, msg, null);
            }
        });
    }

    public static void showSnackBarLoader(final View view, final String msg) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showSnackBar(view, msg, null);

            }
        });
    }


    public static void showSnackBarSafe(final Activity activity, final String msg) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showSnackBar(activity.getWindow().getDecorView().findViewById(android.R.id.content), msg, null);
            }
        });
    }

    public static void showSnackBarWithAction(View view, String msg, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction(actionText, listener).show();
    }


    public static String getRealPathFromURI(Activity activity, Uri contentURI) {
        String result;
        String[] column = new String[2];
        if (true) {
            column[0] = MediaStore.Images.Media.DATA;
            column[1] = MediaStore.Images.Media.SIZE;
        } else {
            column[0] = MediaStore.Video.VideoColumns.DATA;
            column[1] = MediaStore.Video.VideoColumns.SIZE;
        }
        Cursor cursor = activity.getContentResolver().query(contentURI, column, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            result = cursor.getString(0);
            cursor.close();
        }
        return result;
    }

    public static String getRealPathFromURI(Activity activity, Uri contentURI, boolean isImage) {
        String result;
        String[] column = new String[2];
        if (isImage) {
            column[0] = MediaStore.Images.Media.DATA;
            column[1] = MediaStore.Images.Media.SIZE;
        } else {
            column[0] = MediaStore.Video.VideoColumns.DATA;
            column[1] = MediaStore.Video.VideoColumns.SIZE;
        }

        Cursor cursor = activity.getContentResolver().query(contentURI, column, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            result = cursor.getString(0);
            cursor.close();
        }
        return result;
    }

    /**
     * Converts pixels into dip.
     *
     * @param context
     * @param px
     * @return
     */
    public int convertPixelToDP(Context context, int px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (px * (metrics.densityDpi / 160f));
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

}
