package com.classtune.ndc.utils;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;


public class MyApplication extends Application {
    //DatabaseHandler databaseHandler;
    //private SQLiteDatabase mDatabase;
    private static MyApplication mInstance;
    private static Context context;
    //ApiInterface apiInterface;

    private static PendingIntent pendingIntent;
    private static Intent intent1;


    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static void setmInstance(MyApplication mInstance) {
        MyApplication.mInstance = mInstance;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //DBManager.initializeInstance();
        mInstance = this;
        context = this;
        MultiDex.install(this);
        Thread.setDefaultUncaughtExceptionHandler(new LocalFileUncaughtExceptionHandler(this,
                Thread.getDefaultUncaughtExceptionHandler()));
    }

    public void dbInitialize(){
        //DBManager.initializeInstance();
        // databaseHandler = new DatabaseHandler();
        //mDatabase = databaseHandler.getWritableDatabase();
    }

//    public Realm getRealm(){
//        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(config);
//        Realm realm = Realm.getDefaultInstance();
//
//        return realm;
//    }


//    public NetworkInterface getNetworkCallInterface(){
//        Retrofit retrofit = RetrofitApiClient.getInstance(this);
//        apiInterface = retrofit.create(ApiInterface.class);
//
//        return networkCallInterface;
//    }


//    public String getUDID() {
//
//        String udid = AppSharedPreference.getUDID();
//        if (udid.equalsIgnoreCase("")) {
//            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//            udid = tm.getDeviceId();
//
//            AppSharedPreference.setUDID(udid);
//        }
//
//        //showLog("UDID if", "Device ID : " + udid);
//
//        return udid;
//    }

    public static void sendMyNotification(final String subject, final String message, final String type, final String id) {



        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) mInstance
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(mInstance, MainActivity.class);
        notificationIntent.putExtra("subject", subject);
        notificationIntent.putExtra("message", message);
        notificationIntent.putExtra("target_type", type);
        notificationIntent.putExtra("target_id", id);
        PendingIntent pendingNotifyIntent = PendingIntent.getActivity(mInstance, 1111, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(mInstance)
                .setSmallIcon(R.drawable.ndc_logo)
                .setColor(Color.WHITE)
                .setContentTitle(subject)
                .setContentText(message)
                .setOngoing(true)
                //.setSound(sound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingNotifyIntent)
                .setVibrate(new long[]{6000, 6000});

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NDC", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            mNotifyBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, mNotifyBuilder.build());

    }

}

