package com.warrous.ready2ride.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.dealership.promotions.PromotionsActivity;
import com.warrous.ready2ride.framework.Ready2RideLog;
import com.warrous.ready2ride.navigation.HomeActivity;
import com.warrous.ready2ride.wallet.EventActivity;
import com.warrous.ready2ride.wallet.WalletActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static String TAG = "NOTIFICATIN";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily
        if (remoteMessage.getData().size() > 0) {

            //handle the data message here
        }
 //       Map<String, String> data = remoteMessage.getData();
//        if (remoteMessage.getData()!=null){
//            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                Log.d(TAG, "key, " + key + " value " + value);
//            }}
        Ready2RideLog.getInstance().error(TAG, "Notification received");
        //getting the title and the body
        if(remoteMessage.getNotification()!=null){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
          //  Map<String, String> type=remoteMessage.getData();
            sendNotification(title,body);
          //  sendMyNotification(title,body);
        }


        //then here we can use the title and body to build a notification
    }

    private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this, PromotionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                       .setSmallIcon(R.drawable.app_icon_android)
                        .setContentText(messageBody)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setChannelId(channelId)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        //int importance = NotificationManager.IMPORTANCE_MAX;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendMyNotification(String message,String title) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel=new NotificationChannel("my_notification","n_channel",NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.app_icon_android)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon_android))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.parseColor("#3F5996"));
        //.setProgress(100,50,false);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
