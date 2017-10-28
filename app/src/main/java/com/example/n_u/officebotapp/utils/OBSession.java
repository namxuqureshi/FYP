package com.example.n_u.officebotapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class OBSession {
    public static void deletePreference(Context paramContext) {
        PreferenceManager.getDefaultSharedPreferences(paramContext).edit().clear().apply();
    }

    public static String getPreference(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
    }

    public static int getPreference(String key, Context context, boolean b) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0);
    }

    public static boolean hasPreference(String key, Context paramContext) {
        return getPreference(key, paramContext) != null;
    }

    public static boolean hasIntPreference(String key, Context paramContext) {
        return getPreference(key, paramContext, true) > 0;
    }

    public static void putPreference(String key, String value, Context context) {
        SharedPreferences.Editor se = PreferenceManager.getDefaultSharedPreferences(context).edit();
        se.putString(key, value);
        se.apply();
    }

    public static void putPreference(String key, int value, Context context) {
        SharedPreferences.Editor se = PreferenceManager.getDefaultSharedPreferences(context).edit();
        se.putInt(key, value);
        se.apply();
    }

    public static void delPreference(String key, Context context) {
        SharedPreferences.Editor se = PreferenceManager.getDefaultSharedPreferences(context).edit();
        se.remove(key);
        se.apply();
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.util.OfficeBotSession

 * JD-Core Version:    0.7.0.1

 */