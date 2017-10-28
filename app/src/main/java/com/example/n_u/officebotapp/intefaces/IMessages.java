package com.example.n_u.officebotapp.intefaces;

import com.example.n_u.officebotapp.models.Message;
import com.example.n_u.officebotapp.models.Status;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IMessages {
    @POST ("delete/message")
    Call<Status> deleteMessage(@Body HashMap<String, Object> paramHashMap);

    @POST ("get/tag/messages")
    Call<List<Message>> getMessages(@Body HashMap<String, Object> paramHashMap);

    @POST ("get/tag/received/messages")
    Call<List<Message>> otherMessages(@Body HashMap<String, Object> paramHashMap);

    @POST ("new/message")
    Call<Status> sentMessage(@Body HashMap<String, Object> paramHashMap);

    @Multipart
    @POST ("submit/file/{id}")
    Call<Status> sentMessageFile(@Part MultipartBody.Part part
            , @Path ("id") String s);

    @Multipart
    @POST ("submit/file/{id}")
    Call<Status> sentMessageFile(@Part MultipartBody.Part part
            , @Part MultipartBody.Part part1, @Path ("id") String s);
}
