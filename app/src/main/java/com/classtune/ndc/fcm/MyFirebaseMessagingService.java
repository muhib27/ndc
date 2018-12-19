package com.classtune.ndc.fcm;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.classtune.ndc.utils.MyApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage message) {


        Log.d("TAG", "From: " + message.getData());

        // Check if message contains a data payload.
//        if (message.getData().size() > 0) {
//            Log.d("TAG", "Message data payload: " + message.getData());
//
//        }
        //sendMyNotification(message.getData().get("order_id"), message.getData().get("title"), message.getData().get("body"));
       // message.getData().get("order_id");

        //setAlarm();
        if(message.getData().get("subject")!=null)
        MyApplication.sendMyNotification(message.getData().get("subject"),message.getData().get("message"), message.getData().get("target_type"), message.getData().get("target_id"));

    }


}
