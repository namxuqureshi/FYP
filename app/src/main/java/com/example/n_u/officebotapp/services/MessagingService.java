package com.example.n_u.officebotapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.activities.ReplyActivity;
import com.example.n_u.officebotapp.activities.RequestsActivity;
import com.example.n_u.officebotapp.activities.SplashActivity;
import com.example.n_u.officebotapp.activities.TagActivity;
import com.example.n_u.officebotapp.activities.TagMainActivity;
import com.example.n_u.officebotapp.utils.AppLog;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.app.NotificationCompat.DEFAULT_LIGHTS;
import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

public class MessagingService extends FirebaseMessagingService {


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        AppLog.logString("From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            AppLog.logString("Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            AppLog.logString("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(remoteMessage.getData());
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        AppLog.logString("Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(java.util.Map<String, String> messageBody) {
        AppLog.logString("sendNotification: " + messageBody.get("name"));

        try {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setDefaults(DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                    .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                    .setTicker("New Notification");
//            Notify n = OfficeBotURI.gson.fromJson(messageBody, Notify.class);


//            JsonParser jp = new JsonParser();
//            JsonElement je = jp.parse(messageBody);
//            com.google.gson.JsonObject jb = je.getAsJsonObject();
//            AppLog.logString(jb.toString());
            JSONObject jsonArray = new JSONObject(messageBody);
            AppLog.logString(jsonArray.toString());
            Intent intent;
            if (jsonArray.getString("PushType") != null) {
                if (jsonArray.getString("PushType").equals("request")) {
                    intent = new Intent(this, RequestsActivity.class);
                    notificationBuilder.setContentTitle(jsonArray.getString("name"));
                    notificationBuilder.setContentText("Send you a request!");

                } else if (jsonArray.getString("PushType").equals("message")) {
                    intent = new Intent(this, TagMainActivity.class);
                    intent.putExtra(getString(R.string.tag_id_key), Integer.parseInt(jsonArray.getString("tag_id")));
                    notificationBuilder.setContentTitle(jsonArray.getString("name"));
                    notificationBuilder.setContentText("Post a Note on your Tag!");
                } else if (jsonArray.getString("PushType").equals("reply")) {
                    intent = new Intent(this, ReplyActivity.class);
                    intent.putExtra(getString(R.string.tag_id_key), jsonArray.getString("tag_id"));
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.MSG_ID_BUNDLE), jsonArray.getString("msg_id"));
                    bundle.putString(getString(R.string.MSG_CONTENT_BUNDLE_KEY), jsonArray.getString("msg_content"));
                    bundle.putString(getString(R.string.MSG_TIME_BUNDLE_KEY), jsonArray.getString("msg_createdAt"));
                    bundle.putString(getString(R.string.MSG_OWNER_BUNDLE_KEY), jsonArray.getString("msg_owner"));
                    intent.putExtra(getString(R.string.MSG_KEY), bundle);
                    notificationBuilder.setContentTitle(jsonArray.getString("name"));
                    notificationBuilder.setContentText("Comment on your Notes!");
                } else {
                    intent = new Intent(this, SplashActivity.class);
                    notificationBuilder.setContentTitle("OfficeBot");
                    notificationBuilder.setContentText("Notification");
                }
            } else {
                intent = new Intent(this, TagActivity.class);
                notificationBuilder.setContentTitle("OfficeBot");
                notificationBuilder.setContentText("Notification");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);


            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } catch (NullPointerException | JSONException e) {
//            AppLog.toastShortString("Notification Received Butt Exception Error"
//                    , getApplicationContext());
            AppLog.logString(e.getMessage());
        }
    }
}