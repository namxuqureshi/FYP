package com.example.n_u.officebotapp.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static android.content.Context.VIBRATOR_SERVICE;

public final class AppLog {
    public static final String PLEASE_WAIT = "Please Wait Already Downloading!";
    public static final int FILE_LENGTH = 3000000;
    public static final int AUDIO_LENGTH = 300000;
    public static final String INTERNET_NOT_AVAILABLE = "Internet Not Available";
    public static final String NET_NOT_AVAILABLE = "Net Not Available";
    public static final String PATH_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecorder" + "/";
    public static final int RC_SIGN_IN = 9001;
    public static final String SERVER_LOG = "Server Offline";
    public static final int INTENSITY_LOW = 30;
    public static final int INTENSITY_MIDDLE = 40;
    public static final int INTENSITY_HIGH = 60;
    private static final String TAG = "OfficeBot";
    public static final OnErrorListener ERROR_LISTENER = new OnErrorListener() {
        public void onError(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Error: " + what + ", " + extra);
        }
    };
    public static final OnInfoListener INFO_LISTENER = new OnInfoListener() {
        public void onInfo(MediaRecorder mr, int what, int extra) {
            AppLog.logString("Warning: " + what + ", " + extra);
        }
    };
    private static PrettyTime prettyTime = new PrettyTime();
    private static Vibrator mVibrate;

    public static void setVibrate(Context context, int intensity) {
//        mVibrate=new
        mVibrate = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (mVibrate.hasVibrator()) {
            if (OBSession.hasPreference("OffVibrate", context)) {
                mVibrate.vibrate(0);
            } else
                mVibrate.vibrate(intensity);
        }
    }

    public static void offVibrate() {
        mVibrate.cancel();
    }

    public static void defaultFileOpener(String s, Context context) {
        File localFile = new File(s);
        StringBuilder string = new StringBuilder(s);
        String str = string.substring(string.indexOf(".") + 1);
        Log.e("TAG", "onClick: " + localFile);
        MimeTypeMap localMimeTypeMap = MimeTypeMap.getSingleton();
        Intent i = new Intent("android.intent.action.VIEW");
        str = localMimeTypeMap.getMimeTypeFromExtension(str);
        i.setDataAndType(Uri.fromFile(localFile), str);
//        i.setFlags(Intent.FLAG_);
        try {
            context.startActivity(Intent.createChooser(i, "Choose"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext()
                    , "No handler for this type of file." + e.getMessage()
                    , Toast.LENGTH_LONG).show();
        }
    }

    public static String getDateAndTime(long l) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(l));
    }

    public static String getPretty(String s, Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            Date dt = formatter.parse(s);
            return prettyTime.format(formatter.parse(s));
        } catch (ParseException e) {
            AppLog.logString(e.getMessage());
            AppLog.toastShortString(e.getMessage(), context);
        }
        return null;
    }

    public static String getDateTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public static String getFilename() {
        Object object = Environment.getExternalStorageDirectory().getPath();
        logString((String) object);
        object = new File((String) object, "AudioRecorder");
        if (!((File) object).exists()) {
            ((File) object).mkdirs();
        }
        return ((File) object).getAbsolutePath() + "/" + "temp" + ".mp3";
    }

    public static String getFileTempName() {
        Object object = Environment.getExternalStorageDirectory().getPath();
        logString((String) object);
        object = new File((String) object, "AudioTempRecorder");
        if (!((File) object).exists()) {
            ((File) object).mkdirs();
        }
        String st = UUID.randomUUID().toString();
        return ((File) object).getAbsolutePath() + "/" + st + ".mp3";
    }

    public static boolean isFilePresent(String s) {
        File f = new File(PATH_FILE + s);
        logString(f.getAbsolutePath());
        return f.exists();
    }

    public static int logString(String s) {
        return Log.e(TAG, s);
    }

    public static void toastString(String s, Context context) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    public static void toastShortString(String s, Context context) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static int logString(int code) {
        return Log.e(TAG, String.valueOf(code));
    }
}
