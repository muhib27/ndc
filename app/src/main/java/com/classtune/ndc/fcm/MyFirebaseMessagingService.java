package com.classtune.ndc.fcm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage message) {


//        Log.d("TAG", "From: " + message.getFrom());

        // Check if message contains a data payload.
//        if (message.getData().size() > 0) {
//            Log.d("TAG", "Message data payload: " + message.getData());

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

//        }
        //sendMyNotification(message.getData().get("order_id"), message.getData().get("title"), message.getData().get("body"));
       // message.getData().get("order_id");

        //setAlarm();
        //MyApplication.sendMyNotification(message.getData().get("order_id"), message.getData().get("title"), message.getData().get("body"));

    }





}
