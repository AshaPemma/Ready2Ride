package com.warrous.ready2ride.comman;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ACS on 21-10-15.
 */
public class SessionStore {


    public static String PROFILEPICTURE = "profilePicture";
    public static String BIKEPICTURE = "bikePicture";


    public static void saveProfilePicture(Context context, String name, String picture) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putString(PROFILEPICTURE, picture);
        editor.commit();
    }

    public static String getBikePicture(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return pref.getString(BIKEPICTURE, "");
    }

    public static void saveBikePicture(Context context, String name, String picture) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.putString(BIKEPICTURE, picture);
        editor.commit();
    }

    public static String getProfilePicture(Context context, String name) {
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return pref.getString(PROFILEPICTURE, "");
    }

    public static void clear(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}
