package com.rajatv.surajv.roshank.sac.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rajatv.surajv.roshank.sac.BottomNavigationActivity;
import com.rajatv.surajv.roshank.sac.Feeds.Result.Results;
import com.rajatv.surajv.roshank.sac.MainActivity;
import com.rajatv.surajv.roshank.sac.R;

public class FCMNotificationService extends FirebaseMessagingService {

    private static final String TAG = "FCMNotificationService";
    private String publishActivity = "";

     @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {/*
        super.onMessageReceived(remoteMessage);*/
        String notificationTitle = "", notificationBody = "";
        String dataTitle = "", dataMessage = "";

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            dataTitle = remoteMessage.getData().get("title");
            dataMessage = remoteMessage.getData().get("message");
        }

        sendNotification(publishActivity,notificationTitle, notificationBody, dataTitle, dataMessage);
    }

    private void sendNotification(String publishActivity, String notificationTitle, String notificationBody, String dataTitle, String dataMessage) {
        Intent intent = new Intent(this, BottomNavigationActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this,"SAC 2.0")
                .setSmallIcon(R.mipmap.sac_launch)
                .setContentTitle(notificationTitle)
                .setContentText(dataTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(dataMessage))
                .setVibrate(new long[]{100})
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());
    }
}