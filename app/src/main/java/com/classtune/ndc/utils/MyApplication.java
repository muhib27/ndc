package com.classtune.ndc.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;


public class MyApplication extends Application {
    //DatabaseHandler databaseHandler;
    //private SQLiteDatabase mDatabase;
    private static MyApplication mInstance;
    private static Context context;
    //ApiInterface apiInterface;

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


    public String getUDID() {

        String udid = AppSharedPreference.getUDID();
        if (udid.equalsIgnoreCase("")) {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            udid = tm.getDeviceId();

            AppSharedPreference.setUDID(udid);
        }

        //showLog("UDID if", "Device ID : " + udid);

        return udid;
    }
}
