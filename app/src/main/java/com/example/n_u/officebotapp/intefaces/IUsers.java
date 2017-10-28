package com.example.n_u.officebotapp.intefaces;

import com.example.n_u.officebotapp.models.Login;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.models.User;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IUsers {
    @POST ("forgot/password")
    Call<Status> forgetPasswordRequestCode(@Body HashMap<String, Object> paramHashMap);

    @POST ("auth/token")
    Call<Status> forgetPasswordSendRequestCode(@Body HashMap<String, Object> paramHashMap);

    @GET ("users")
    Call<User> getUser();

    @POST ("user/login")
    Call<Login> loginUser(@Body HashMap<String, Object> paramHashMap);

    @POST ("reset/password")
    Call<Status> resetPasswordRequest(@Body HashMap<String, Object> map);

    @Multipart
    @POST ("user/image/{id}")
    Call<Status> setUserImage(@Part MultipartBody.Part part
            , @Path ("id") String s);

    @PUT ("user/{id}")
    Call<Status> changeUserDetail(@Body HashMap<String, Object> paramHashMap
            , @Path ("id") String s);

    @POST ("add/device/{id}")
    Call<Status> deviceId(@Body HashMap<String, Object> map, @Path ("id") String s);

}
