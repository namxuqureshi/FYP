package com.example.n_u.officebotapp.services;

import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by n_u on 5/4/17.
 */

public class TokenService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        AppLog.logString(FirebaseInstanceId.getInstance().getToken());
        sendSeverToken(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendSeverToken(String token) {
//        String token = FirebaseInstanceId.getInstance().getToken()
        final IUsers iUsers = OfficeBotURI.retrofit().create(IUsers.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("device_id", FirebaseInstanceId.getInstance().getToken());
        Call<Status> call = iUsers.deviceId(map, OBSession.getPreference("id", getApplicationContext()));
        OBSession.putPreference("token_device", token, getApplicationContext());
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                AppLog.logString(response.body().toString());
                if (response.code() == 200) {
                    AppLog.logString("Seccuess");
                } else AppLog.logString("Fail");
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                AppLog.logString(t.getMessage());
            }
        });
    }

}
