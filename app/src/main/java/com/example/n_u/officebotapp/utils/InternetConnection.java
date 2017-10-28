package com.example.n_u.officebotapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class InternetConnection {
    public static boolean checkConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isConnected())) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) new URL(OfficeBotURI.getBaseSite()).openConnection();
                        connection.setConnectTimeout(5000);
                        connection.connect();
                        if (connection.getResponseCode() == 200) {
                            Log.wtf("Connection", "Success !");
                            AppLog.logString("Success !");

                        } else {
                            AppLog.logString("Fail !");
                        }

                    } catch (MalformedURLException localMalformedURLException) {
                        AppLog.logString(localMalformedURLException.getLocalizedMessage());

                    } catch (IOException localIOException) {
                        AppLog.logString(localIOException.getMessage());
                    }
                }
            }).start();
            return true;
        } else {
            return true;
        }
    }
}
