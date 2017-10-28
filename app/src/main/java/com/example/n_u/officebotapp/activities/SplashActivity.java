package com.example.n_u.officebotapp.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.services.TokenService;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity
        extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST = 1;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
//        RelativeLayout l = (RelativeLayout) findViewById(R.id.splash_parent);
//        l.clearAnimation();
//        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash_image);
        iv.clearAnimation();
        iv.startAnimation(anim);
        if (ContextCompat.checkSelfPermission(this,
                "android.permission.READ_CONTACTS") != 0) {
            ActivityCompat.requestPermissions(this,
                    new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
                            "android.permission.RECORD_AUDIO",
                            "android.permission.NFC",
                            "android.permission.GET_ACCOUNTS",
                            "android.permission.VIBRATE"}, MY_PERMISSIONS_REQUEST);
        }
        Glide.with(this).load(R.drawable.nfc_trans)
                .asGif()
                .placeholder(R.drawable.nfc_trans)
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .into(iv);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();

    }

    //    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        if (ContextCompat.checkSelfPermission(this,
//                "android.permission.READ_CONTACTS") != 0) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
//                            "android.permission.RECORD_AUDIO",
//                            "android.permission.NFC",
//                            "android.permission.GET_ACCOUNTS",
//                            "android.permission.VIBRATE"}, MY_PERMISSIONS_REQUEST);
//        }
//    }


//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(false);
//    }
//
//    @Override
//    public void initSplash(ConfigSplash configSplash) {
//
//   /* you don't have to override every property */
//        //Customize Circular Reveal
//        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
//        configSplash.setAnimCircularRevealDuration(800); //int ms
//        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);  //or Flags.REVEAL_LEFT
//        configSplash.setRevealFlagY(Flags.REVEAL_TOP); //or Flags.REVEAL_TOP
//
////        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default
////
////        //Customize Logo
////        configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
////        configSplash.setAnimLogoSplashDuration(700); //int ms
////        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)
////
////
////        //Customize Path
////        configSplash.setPathSplash(AppLog.APP_TAG); //set path String
////        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
////        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
////        configSplash.setAnimPathStrokeDrawingDuration(800);
////        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
////        configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
////        configSplash.setAnimPathFillingDuration(3000);
////        configSplash.setPathSplashFillColor(R.color.colorPrimaryDark); //path object filling color
////
////
////        //Customize Title
////        configSplash.setTitleSplash("My Awesome App");
////        configSplash.setTitleTextColor(android.R.color.primary_text_light);
////        configSplash.setTitleTextSize(30f); //float value
////        configSplash.setAnimTitleDuration(900);
////        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
//
////        configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/
//
//    }

//    @Override
//    public void animationsFinished() {
//        if (ContextCompat.checkSelfPermission(this,
//                "android.permission.READ_CONTACTS") != 0) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
//                            "android.permission.RECORD_AUDIO",
//                            "android.permission.NFC",
//                            "android.permission.GET_ACCOUNTS",
//                            "android.permission.VIBRATE"}, MY_PERMISSIONS_REQUEST);
//        }
//        //transit to another activity here
//        //or do whatever you want
//    }

    public void onRequestPermissionsResult(int i,
                                           @NonNull String[] permissions,
                                           @NonNull int[] arrayOfInt) {
        if (i == MY_PERMISSIONS_REQUEST) {
            AppLog.logString(Arrays.toString(arrayOfInt));
            AppLog.logString(Arrays.toString(permissions));
            if ((permissions.length > 0) && (arrayOfInt[0] == 0)) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Object localObject = LoginActivity.class;
                        if (OBSession.hasPreference(getString(R.string.user_data_key),
                                getApplicationContext())) {
                            localObject = ProfileTagActivity.class;
                        }
                        localObject = new Intent(SplashActivity.this, (Class) localObject);
                        if (OBSession.hasPreference("id", getApplicationContext())) {
                            final IUsers iUsers = OfficeBotURI.retrofit().create(IUsers.class);
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("device_id", FirebaseInstanceId.getInstance().getToken());
                            AppLog.logString(FirebaseInstanceId.getInstance().getToken());
                            Call<Status> call = iUsers.deviceId(map, OBSession.getPreference("id", getApplicationContext()));
//                        OBSession.putPreference("token_device", token, getApplicationContext());
                            if (!call.isExecuted()) {
                                call.enqueue(new Callback<Status>() {
                                    @Override
                                    public void onResponse(Call<Status> call, Response<Status> response) {
                                        if (response.code() == 200) {
                                            AppLog.logString(response.body().toString());
                                            AppLog.logString("Success");
                                        } else AppLog.logString("Fail");
                                    }

                                    @Override
                                    public void onFailure(Call<Status> call, Throwable t) {
                                        AppLog.logString(t.getMessage());
                                    }
                                });
                            }
                            startService(new Intent(SplashActivity.this
                                    , TokenService.class));
                        }
                        startActivity((Intent) localObject);
                        finish();
                    }
                }, 1500);
                AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
            }
        } else {
            Toast.makeText(this, R.string.permission_mobile, Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
