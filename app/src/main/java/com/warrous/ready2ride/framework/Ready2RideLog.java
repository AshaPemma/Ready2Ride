package com.warrous.ready2ride.framework;

import android.util.Log;

public class Ready2RideLog {
    private static final String TAG = Ready2RideLog.class.getSimpleName();
    private static final int LEVEL_ERROR = 0;
    private static final int LEVEL_INFO = 1;
    private static final int LEVEL_DEBUG = 2;
    private static Ready2RideLog ourInstance = new Ready2RideLog();
    private int logLevel = LEVEL_ERROR;

    private Ready2RideLog() {
    }

    public static Ready2RideLog getInstance() {
        return ourInstance;
    }

    public void info(String msg) {
        if (logLevel >= LEVEL_INFO) {
            Log.i(TAG, ""+msg);
        }
    }

    public void debug(String msg) {
        if (logLevel >= LEVEL_DEBUG) {
            Log.d(TAG, ""+msg);
        }
    }

    public void error(String msg) {
        if (logLevel >= LEVEL_ERROR) {
            Log.e(TAG, ""+msg);
        }
    }

    public void info(String tag, String msg) {
        if (logLevel >= LEVEL_INFO) {
            Log.i(tag, ""+msg);
        }
    }

    public void debug(String tag, String msg) {
        if (logLevel >= LEVEL_DEBUG) {
            Log.d(tag, ""+msg);
        }
    }

    public void error(String tag, String msg) {
        if (logLevel >= LEVEL_ERROR) {
            Log.e(tag, ""+msg);
        }
    }
}
