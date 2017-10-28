package com.example.n_u.officebotapp.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;

public final class Progress {
    private static ProgressDialog pro;

    public static boolean IsShowing() {
        return pro.isShowing();
    }

    public static void Cancel() {
        pro.cancel();
    }

    public static void Show(Context paramContext) {
        pro = new ProgressDialog(paramContext);
        if (!pro.isShowing()) {
            if (pro.getWindow() != null) {
                pro.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL | Gravity.AXIS_SPECIFIED);
            }
            pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pro.setMessage("Loading...");
            pro.setIndeterminate(true);
            pro.setCancelable(false);
            pro.setCanceledOnTouchOutside(false);
            pro.show();
        } else
            pro.cancel();
    }

    public static void Show(Context paramContext, String paramString) {
        pro = new ProgressDialog(paramContext);
        if (!pro.isShowing()) {
            if (pro.getWindow() != null) {
                pro.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL | Gravity.AXIS_SPECIFIED);
            }
            pro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pro.setMessage(paramString);
            pro.setIndeterminate(true);
            pro.setCancelable(false);
            pro.setCanceledOnTouchOutside(false);
            android.os.Message msg = new android.os.Message();
            android.os.Bundle bundle = new android.os.Bundle();
//            bundle.putString("MSG", "Request Complete");
//            msg.setData(bundle);
//            pro.setDismissMessage(msg);
            pro.show();
        } else
            pro.cancel();
    }

    public static ProgressDialog getPro() {
        return pro;
    }

    public static void setPro(ProgressDialog paramProgressDialog) {
        pro = paramProgressDialog;
    }
}

