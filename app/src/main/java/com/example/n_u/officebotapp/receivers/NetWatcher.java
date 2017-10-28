package com.example.n_u.officebotapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.google.firebase.iid.FirebaseInstanceId;


public class NetWatcher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //here, check that the network connection is available. If yes, start your service. If not, stop your service.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                //start service
//                Intent intent = new Intent(context, MyService.class);
//                context.stopService(intent);
                AppLog.logString(FirebaseInstanceId.getInstance().getToken());
                OBSession.putPreference("token_device"
                        , FirebaseInstanceId.getInstance().getToken()
                        , context.getApplicationContext());
            } else {
                //stop service
//                Bundle bundle = intent != null ? intent.getBundleExtra("bnd") : null;
//                intent = new Intent(context, OfflineNotePostService.class);
//                intent.putExtra("bnd", bundle);
//                context.startService(intent);
            }
        }
    }
}