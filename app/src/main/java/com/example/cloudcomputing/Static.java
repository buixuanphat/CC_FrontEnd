package com.example.cloudcomputing;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class Static {

    public static final String baseUrl ="http://192.168.1.17:8000/";
    public static void restartApp(Activity activity) {
        Intent intent = activity.getPackageManager()
                .getLaunchIntentForPackage(activity.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
        activity.finishAffinity();
    }

}
