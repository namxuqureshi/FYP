package com.example.n_u.officebotapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.intefaces.IFriends;
import com.example.n_u.officebotapp.intefaces.IGroups;
import com.example.n_u.officebotapp.intefaces.IRequests;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Friend;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.Member;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListService extends Service {
    public UserListService() {
    }

    private void sendSeverToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        final IUsers iUsers = OfficeBotURI.retrofit().create(IUsers.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("device_id", FirebaseInstanceId.getInstance().getToken());
        Call<com.example.n_u.officebotapp.models.Status> call = iUsers.deviceId(map, OBSession.getPreference("id", getApplicationContext()));
        OBSession.putPreference("token_device", token, getApplicationContext());
        call.enqueue(new Callback<com.example.n_u.officebotapp.models.Status>() {
            @Override
            public void onResponse(Call<com.example.n_u.officebotapp.models.Status> call, Response<com.example.n_u.officebotapp.models.Status> response) {
                AppLog.logString(response.body().toString());
                if (response.code() == 200) {
                    AppLog.logString("Seccuess");
                } else AppLog.logString("Fail");
            }

            @Override
            public void onFailure(Call<com.example.n_u.officebotapp.models.Status> call, Throwable t) {
                AppLog.logString(t.getMessage());
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        final HashMap<String, Object> body = new HashMap<>();
        final IRequests user = OfficeBotURI.retrofit().create(IRequests.class);
        final IFriends request = OfficeBotURI.retrofit().create(IFriends.class);
        final IGroups groupListRequest = OfficeBotURI.retrofit().create(IGroups.class);
        body.put(getString(R.string.name_key), "");
        body.put(getString(R.string.user_id_key)
                , OBSession.getPreference(getString(R.string.id_key)
                        , getApplicationContext()));
        user.getSearchUserList(body).enqueue(new Callback<ModelList>() {
            public void onFailure(Call<ModelList> callback
                    , Throwable t) {
                AppLog.toastShortString(t.getLocalizedMessage()
                        , getApplicationContext());
            }

            public void onResponse(Call<ModelList> callback
                    , Response<ModelList> response) {
                if (response.code() == 200) {
                    sendSeverToken();
                    new Delete().from(SearchUser.class).execute();
                    ActiveAndroid.beginTransaction();
                    try {
                        for (SearchUser temp : response.body().getUserList()) {
                            SearchUser item = new SearchUser(temp);
                            item.save();
                        }
                        ActiveAndroid.setTransactionSuccessful();
                    } finally {
                        ActiveAndroid.endTransaction();
                    }
                } else {
                    AppLog.toastShortString(response.code() + response.message()
                            , getApplicationContext());
                }
                request.getFriendList(body).enqueue(new Callback<ModelList>() {
                    public void onFailure(Call<ModelList> callback, Throwable t) {
                        AppLog.toastShortString(t.getMessage(), getApplicationContext());
                    }

                    public void onResponse(Call<ModelList> callback,
                                           retrofit2.Response<ModelList> response) {

                        if (response.code() == 200 && response.body() != null) {
                            new Delete().from(Friend.class).execute();
                            ActiveAndroid.beginTransaction();
                            try {
                                for (Friend temp : response.body().getFriendList()) {
                                    Friend item = new Friend(temp);
                                    AppLog.logString(item.toString());
                                    item.save();
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            } finally {
                                ActiveAndroid.endTransaction();
                            }

                        } else {
                            AppLog.toastShortString(response.code() + response.message()
                                    , getApplicationContext());
                        }
                        groupListRequest.getGroupList(body).enqueue(new Callback<ModelList>() {
                            @Override
                            public void onFailure(Call<ModelList> callback
                                    , Throwable t) {
                                AppLog.toastShortString(t.getMessage(), getApplication());

                            }

                            @Override
                            public void onResponse(Call<ModelList> callback
                                    , Response<ModelList> response) {
                                if (response.code() == 200) {
                                    new Delete().from(Member.class).execute();
                                    new Delete().from(Group.class).execute();
                                    ActiveAndroid.beginTransaction();
                                    try {
                                        for (Group temp : response.body().getGroupList()) {
                                            Group item = new Group(temp);
                                            for (Member mem : temp.getMember()) {
                                                Member m = new Member(mem);
                                                m.save();
                                            }
                                            AppLog.logString(item.getMember().toString());
                                            item.save();
                                        }
                                        ActiveAndroid.setTransactionSuccessful();
                                    } finally {
                                        ActiveAndroid.endTransaction();
                                    }

                                } else {
                                    AppLog.toastShortString(response.code() + response.message()
                                            , getApplicationContext());
                                }

                                stopSelf(startId);
                            }
                        });

                    }
                });

            }
        });
        return START_STICKY;
    }
}
