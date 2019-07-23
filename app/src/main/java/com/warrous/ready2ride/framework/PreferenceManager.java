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

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


public class PreferenceManager {

    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_CAMPAIGNS = "KEY_CAMPAIGNS";
    public static final String KEY_DEALER_ID = "KEY_DEALER_ID";
    public static final String KEY_DEFAUKT_DEALER_ID = "KEY_DEFAUKT_DEALER_ID";
    public static final String KEY_FNAME= "KEY_FNAME";
    public static final String KEY_LNAME = "KEY_LNAME";
    public static final String KEY_USERNAME= "KEY_USERNAME";
    public static final String KEY_OWNER_ID="KEY_ORG_ID";




    public static final String KEY_FCM_TOKEN = "KEY_FCM_TOKEN";
    public static final String KEY_LAUNCH = "LAUNCH";

    public static final String KEY_SOCIAL_LOGIN = "KEY_SOCIAL_LOGIN";

    public static final String KEY_FIRST_LOGIN = "FIRSTLOGIN";
    public static final String KEY_USER_ID = "KEY_USER_ID";

    public static final String KEY_PROFILE = "PROFILE";
    public static final String KEY_TIME_TRACK = "TIMETRACK";
    public static final String CATEGORIES = "CATEGORIES";
    public static final String USER_CATEGORIES = "USER_CATEGORIES";
    public static final String PROFILE = "PROFILE";
    public static final String SERVICE_CENTERS = "SERVICECENTERS";
    public static final String USER_REVIEWS = "USERREVIEWS";
    public static final String PRODUCT_ARTIFACTS = "PRODUCTARTIFACTS";
    public static final String KEY_PLAN = "KEY_PLAN";


    private static String SHARED_PREFRENCE_FILE = "lm_cacr_preference";

    public static void storeValue(Context context, String key, Object value) {

        if (context != null) {
            SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Set) {
                //I edit the prefs and add my string set and label it as "List"
                editor.putStringSet(key, (Set<String>) value);
            }


            editor.apply();
        }
    }

    public static String getStringValue(Context context, String key) {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static boolean getBooleanValue(Context context, String key) {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultValue);
    }

    public static int getIntegerValue(Context context, String key) {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static float getIntegerFloat(Context context, String key) {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        return prefs.getFloat(key, 0);
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences settings = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
    }

    public static long getLongValue(Context context, String key) {
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFRENCE_FILE, Context.MODE_PRIVATE);
        return prefs.getLong(key, 0);
    }


}
