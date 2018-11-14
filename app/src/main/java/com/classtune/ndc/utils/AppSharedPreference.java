package com.classtune.ndc.utils;

import android.content.SharedPreferences;

/**
 * Created by RR on 13-Nov-18.
 */

public class AppSharedPreference {
    public static final String keyModelTestPrefs = "modelTestPrefs";

    private static final String keyIsFirstTime = "isFirstTime";
    private static final String keyUserName = "username";
    private static final String keyUserPassword = "userpassword";
    private static final String keyUDID = "udid";

    private static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences(keyModelTestPrefs, 0);
    }

    public static boolean getUsingFirstTime() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getBoolean(keyIsFirstTime, true);
    }

    public static void setUsingFirstTime(boolean isFirstTime) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(keyIsFirstTime, isFirstTime);
        editor.apply();
    }

    public static void savePrefBoolean(String key, boolean value){
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static boolean getPrefBoolean(String key){
        final SharedPreferences pref = getSharedPreferences();
        return pref.getBoolean(key, false);
    }

    public static void setUserNameAndPassword(String userId, String password) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(keyUserName, userId);
        editor.putString(keyUserPassword, password);
        editor.apply();
    }
    public static void setUDID(String udid){
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(keyUDID, udid);
        editor.apply();
    }

    public static String getUDID() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUDID, "");
    }

    public static String getUserName() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUserName, "");
    }
    public static String getUserPassword() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUserPassword, "");
    }


}
