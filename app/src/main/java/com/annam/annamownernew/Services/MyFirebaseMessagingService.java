package com.annam.annamownernew.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.annam.annamownernew.R;

import java.util.Map;

/**
 * Created by intellyelabs on 24/05/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        String Heading = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Map<String, String> data = remoteMessage.getData();
        Log.e("Firebase notification ", " KEY1");

//        if (!data.get("key").equals("")) {
//            ClickActionHelper.startActivity(data.get("click_action"), null, this);

        try {

//                Class cls = Class.forName(data.get("key"));
            Class cls = Class.forName("com.annam.annamownernew.Owner_Home");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplication());
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle(Heading);
            mBuilder.setContentText(message);
            Intent notificationIntent = null;
            notificationIntent = new Intent(this, cls);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            mBuilder.setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, mBuilder.build());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//
//        } else {
//
//            Log.e("Error ", "NO KEY");
//        }
    }
}