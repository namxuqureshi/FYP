package com.example.n_u.officebotapp.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IStatus;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.utils.StringConverter;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.app.AlertDialog.Builder;
import static com.example.n_u.officebotapp.activities.TagActivity.RESULT_SYNC;

public class NFCActivity
        extends Activity {
    public static final float FLOAT_ROTATE = 20.0F;
    public static final String[][] STRINGS = new String[0][];
    private final HashMap<String, Object> body = new HashMap<>();
    private final IStatus request = (IStatus) OfficeBotURI.retrofit().create(IStatus.class);
    private Context context = this;
    private boolean flag = true;
    private CircleImageView imgInner = null;
    private CircleImageView imgOuter = null;
    private NfcAdapter mNfcAdapter = null;
    private ImageView nfcFeed;
//    private ViewPropertyAnimator tmpInner = null;
    private ViewPropertyAnimator tmpOuter = null;


    public static void setupForegroundDispatch(Activity activity, NfcAdapter nfcAdapter) {
        Object localObject = new Intent(activity.getApplicationContext(), activity.getClass());
        ((Intent) localObject).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        localObject = PendingIntent.getActivity(activity.getApplicationContext(), 0, (Intent) localObject, 0);
        IntentFilter[] arrayOfIntentFilter = new IntentFilter[3];
        arrayOfIntentFilter[0] = new IntentFilter();
        arrayOfIntentFilter[0].addAction("android.nfc.action.NDEF_DISCOVERED");
        arrayOfIntentFilter[0].addCategory("android.intent.category.SELECTED_ALTERNATIVE");
        arrayOfIntentFilter[1] = new IntentFilter();
        arrayOfIntentFilter[1].addAction("android.nfc.action.TAG_DISCOVERED");
        arrayOfIntentFilter[1].addCategory("android.intent.category.SELECTED_ALTERNATIVE");
        arrayOfIntentFilter[2] = new IntentFilter();
        arrayOfIntentFilter[2].addAction("android.nfc.action.TECH_DISCOVERED");
        arrayOfIntentFilter[2].addCategory("android.intent.category.SELECTED_ALTERNATIVE");
        nfcAdapter.enableForegroundDispatch(activity, (PendingIntent) localObject, arrayOfIntentFilter, STRINGS);
    }

    public static void stopForegroundDispatch(Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundDispatch(activity);
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        setContentView(R.layout.activity_nfc);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        imgOuter = ((CircleImageView) findViewById(R.id.animate));
//        imgInner = ((CircleImageView) findViewById(R.id.animate2));
        nfcFeed = (ImageView) findViewById(R.id.nfc_feed);
        animate();
        ((TextView) findViewById(R.id.text_title_in_bar)).setText(getString(R.string.nfc_activity));
        handleIntent(getIntent());
        findViewById(R.id.backBtnImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        this.flag = true;
        super.onStart();
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        setupForegroundDispatch(this, this.mNfcAdapter);
        super.onResume();
        com.bumptech.glide.Glide.with(this)
                .load(R.drawable.nfc_anim_four)
                .asGif()
                .placeholder(R.drawable.nfc_anim_four)
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(nfcFeed);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, this.mNfcAdapter);
        super.onPause();
        flag = false;
    }

    private void animate() {
//        this.tmpOuter = this.imgOuter.animate();
//        this.tmpInner = this.imgInner.animate();
        final Thread localThread = new Thread(new Runnable() {
//            float i = FLOAT_ROTATE;
//            float i2 = -FLOAT_ROTATE;

            public void run() {
                while (flag) {
//                    tmpOuter.rotation(this.i);
//                    tmpInner.rotation(this.i2);
                    try {
//                        this.i += FLOAT_ROTATE;
//                        this.i2 -= FLOAT_ROTATE;
                        Thread.sleep(500L);
                        AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                    } catch (InterruptedException localInterruptedException) {
                        AppLog.logString(localInterruptedException.getMessage());
                    }
                }
            }
        });
        if (this.mNfcAdapter.isEnabled()) {
//            this.tmpOuter.start();
            localThread.start();
        }
    }

    private void handleIntent(final Intent intent) {
        if (OBSession.hasPreference("user", getApplicationContext())) {
            Object action = intent.getAction();
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
                AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
                final String ssn = StringConverter.
                        bytesToHex(((Tag) intent.
                                getParcelableExtra(NfcAdapter.EXTRA_TAG)).
                                getId());
                body.put(getString(R.string.ssn_key), ssn);
                body.put(getString(R.string.user_id_key), Integer.parseInt(OBSession.getPreference(getString(R.string.id_key), this.context)));
                AppLog.logString(body.toString());
                retrofit2.Call<Status> call = request.verifyTag(this.body);
                Progress.Show(context, getString(R.string.scan) + "ning..");
                flag = false;
                if (InternetConnection.checkConnection(this)) {
                    call.enqueue(new Callback<Status>() {
                        public void onFailure(Call<Status> call,
                                              Throwable t) {
                            AppLog.toastString("Net Not Available"
                                    , getApplicationContext());
                            AppLog.logString(t.getMessage());
                            Progress.Cancel();
                            finish();
                        }

                        @TargetApi (Build.VERSION_CODES.KITKAT)
                        public void onResponse(Call<Status> call,
                                               final Response<Status> response) {
                            AppLog.logString("onResponse: " + response.code() + " " + "onResponse: " + response.message());
                            Intent i;
                            AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
                            if (response.code() == 200) {
                                if (!response.body().isExist()) {
                                    i = new Intent(context, AddNewTagActivity.class);
                                    setResult(RESULT_SYNC);
                                    i.putExtra(getString(R.string.tag_id_key), ssn);
                                    startActivity(i);
                                    Progress.Cancel();
                                    finish();
                                } else if ((response.body().isExist()) && (response.body().isOwner())) {
                                    Progress.Cancel();
                                    i = new Intent(context, TagMainActivity.class);
                                    i.putExtra(getString(R.string.tag_id_key), response.body().getTagId());
                                    startActivity(i);
                                    finish();
                                } else if (response.body().getPermission() != null) {
                                    if (Objects.equals(response.body().getPermission(), "n")) {
                                        Progress.Cancel();
                                        if (response.body().getUserId() > 1) {
                                            Builder builder = new Builder(context);
                                            builder.setMessage(R.string.send_reqeu);
                                            builder.setPositiveButton(R.string.send, new OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    HashMap<String, Object> localHashMap = new HashMap<>();
                                                    IStatus localIStatus = OfficeBotURI.retrofit().create(IStatus.class);
                                                    localHashMap.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.user_id_key), context));
                                                    localHashMap.put(getString(R.string.user2_id_key), response.body().getUserId());
                                                    localIStatus.getSendRequestStatus(localHashMap).enqueue(new Callback<Status>() {
                                                        public void onFailure(Call<Status> call,
                                                                              Throwable throwable) {
                                                            Toast.makeText(context,
                                                                    throwable.getMessage(),
                                                                    Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }

                                                        public void onResponse(Call<Status> call, Response<Status> response) {
                                                            if (response.code() == 200) {
                                                                if (response.body().isRequest()) {
                                                                    Toast.makeText(context, "Request Send Successfully", Toast.LENGTH_LONG).show();

                                                                } else
                                                                    Toast.makeText(context, "Request Send UnSuccessfully", Toast.LENGTH_SHORT).show();

                                                            } else
                                                                Toast.makeText(context, "Server Offline" + response.code(), Toast.LENGTH_LONG).show();

                                                            finish();
                                                        }
                                                    });
                                                    dialog.cancel();
                                                    AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
                                                    AppLog.toastShortString(getString(R.string.request_send)
                                                            , getApplicationContext());
                                                }
                                            });
                                            builder.setNegativeButton(getString(R.string.no), new OnClickListener() {
                                                public void onClick(DialogInterface dialogInterface, int which) {
                                                    dialogInterface.cancel();
                                                    AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
                                                    finish();
                                                }
                                            });
                                            builder.create().show();
                                        }
                                        AppLog.toastString(getString(R.string.private_tag)
                                                , getApplicationContext());
                                    } else {
                                        Progress.Cancel();
                                        i = new Intent(context, ScanTagActivity.class);
                                        AppLog.logString(response.body().getTagId());
                                        i.putExtra(getString(R.string.tag_id_key), response.body().getTagId());
                                        i.putExtra(getString(R.string.permission_key), response.body().getPermission());
                                        startActivity(i);
                                        finish();
                                    }
                                } else {
                                    Progress.Cancel();
                                    i = new Intent(context, ScanTagActivity.class);
                                    i.putExtra(getString(R.string.tag_id_key), response.body().getTagId());
                                    i.putExtra(getString(R.string.permission_key), response.body().getPermission());
                                    AppLog.logString(response.body().getTagId());
                                    Progress.Cancel();
                                    startActivity(i);
                                    finish();

                                }
                            } else {
                                AppLog.toastString("" + response.code() + response.message()
                                        , getApplicationContext());
                                finish();
                            }
                            Progress.Cancel();
                        }
                    });
                } else {
                    AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE
                            , getApplicationContext());
                    finish();
                }
            } else {
                AppLog.logString("false system tag");
//                AppLog.toastShortString("Tag UnSupported", getApplicationContext());
//                flag = false;
            }
        } else {
            AppLog.toastString("Please Login First!!", getApplicationContext());
            startActivity(new android.content.Intent(this, SplashActivity.class));
            finish();
        }
    }

    public void goBack(View paramView) {
        this.flag = false;
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        this.flag = false;
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();
    }
}
