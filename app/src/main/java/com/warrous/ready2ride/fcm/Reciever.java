package com.warrous.ready2ride.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null
                && intent.getAction().equals("My Call")) {
            Intent startIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            context.startActivity(startIntent);
        }
    }
}
