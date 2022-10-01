package com.Infotech;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.UUID;

public class Utils {

    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static void setLogin(Context context, boolean isLoggedIn) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("com.ACIsLoggedIn", isLoggedIn);
        editor.commit();
    }

    public static boolean isLoggedIn(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("com.ACIsLoggedIn", false);
    }
    public static void adjustFontScale(Configuration configuration, Activity activity, float scale) {
        configuration.fontScale = scale;
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        activity.getResources().updateConfiguration(configuration, metrics);
    }

    public static void setUserCredential(Context context, String user_uid) {
        Log.e("EMAIL","EMAI2"+user_uid);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DEMOUUID", user_uid);
        editor.commit();
    }
    public static void getUserLang(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String UUID = prefs.getString("DEMOUUID", "");
        Settings.UUID = UUID;
    }

    public static String returnUniqueId(){

        return UUID.randomUUID().toString();
    }


}
