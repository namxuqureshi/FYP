package com.example.n_u.officebotapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.intefaces.IMessages;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OfflineNotePostService extends IntentService {
    private static final String ACTION_FOO = "com.example.n_u.officebotapp.services.action.FOO";
    private static final String ACTION_BAZ = "com.example.n_u.officebotapp.services.action.BAZ";
    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.n_u.officebotapp.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.n_u.officebotapp.services.extra.PARAM2";
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    Context context = this;
    private MultipartBody.Part bodyFile = null, bodyAudio = null;

    public OfflineNotePostService() {
        super("OfflineNotePostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            AppLog.logString(intent.toString());
//            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent
            , int flags
            , final int startId) {
        Bundle bundle = intent != null ? intent.getBundleExtra("bnd") : null;

        final HashMap<String, Object> sendArray;
        sendArray = (HashMap<String, Object>) bundle.getSerializable(getString(R.string.EXTRA_ARRAY));
        String audio = (String) (sendArray != null ? sendArray.get("audioFile") : null);
        String fileOther = (String) (sendArray != null ? sendArray.get("otherFile") : null);
        RequestBody body;
        File file;
        if (audio != null) {
            file = new File(audio);
            body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            bodyAudio = MultipartBody.Part.createFormData(getString(R.string.audio_key), file.getName(), body);
        }
        if (fileOther != null) {
            file = new File(fileOther);
            body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            bodyFile = MultipartBody.Part.createFormData(getString(R.string.data_key)
                    , file.getName()
                    , body);
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!InternetConnection.checkConnection(context)) {
                    try {
                        Thread.sleep(20000);
                        AppLog.logString("service running");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(OfficeBotURI.getBaseSite()).openConnection();
                    connection.setConnectTimeout(5000);
                    connection.connect();
                    while (connection.getResponseCode() != 200) {
                        connection.connect();
                        Thread.sleep(20000);
                    }
                } catch (MalformedURLException localMalformedURLException) {
                    AppLog.logString(localMalformedURLException.getLocalizedMessage());

                } catch (InterruptedException | IOException e1) {
                    AppLog.logString(e1.getMessage());
                }
                final IMessages sendMsg = OfficeBotURI.retrofit().create(IMessages.class);
                Call<Status> call = sendMsg.sentMessage(sendArray);
                call.enqueue(new Callback<Status>() {
                    public void onFailure(Call<Status> call, Throwable t) {
                        AppLog.toastString(t.getMessage() + "\n" + "Note Failed", getApplicationContext());
                        AppLog.logString(t.getMessage());
                    }

                    public void onResponse(Call<Status> call, Response<Status> response) {
                        AppLog.logString(response.code() + "");
                        if (response.code() == 200) {
                            Log.e("TAG", "onResponse: " + response.body().getMessage_id());
                            if ((bodyFile != null) || (bodyAudio != null)) {
                                if ((bodyAudio != null) && (bodyFile == null)) {
                                    call = sendMsg.sentMessageFile(bodyAudio, String.valueOf(response.body().getMessage_id()));
                                } else if ((bodyAudio == null) && (bodyFile != null)) {
                                    call = sendMsg.sentMessageFile(bodyFile, String.valueOf(response.body().getMessage_id()));
                                } else {
                                    call = sendMsg.sentMessageFile(bodyAudio, bodyFile, String.valueOf(response.body().getMessage_id()));
                                }
                                call.enqueue(new Callback<Status>() {
                                    public void onFailure(Call<Status> call
                                            , Throwable t) {
                                        AppLog.toastString(t.getMessage(), getApplicationContext());
                                        AppLog.logString(t.getMessage());
                                    }

                                    public void onResponse(Call<Status> call
                                            , Response<Status> response) {
                                        if (response.code() == 200) {
                                            AppLog.toastString(getString(R.string.note_send), getApplicationContext());
                                        } else {
                                            AppLog.toastString(getString(R.string.note_send) + "file not send slow net"
                                                    , getApplicationContext());
                                        }

                                    }

                                });

                            } else
                                AppLog.toastString(getString(R.string.note_send), getApplicationContext());
                        } else {
                            AppLog.toastString("Server Offline\n" + response.code(), getApplicationContext());
                            try {
                                call.execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                AppLog.logString("service end");
                stopSelf(startId);
            }
        });
        t.start();
        return START_STICKY;
    }

}
