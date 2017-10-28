package com.example.n_u.officebotapp.intefaces;

import com.example.n_u.officebotapp.models.Message;
import com.example.n_u.officebotapp.models.Reply;
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

public interface IReply {
    @Multipart
    @POST ("reply/submit/file/{id}")
    Call<Status> fileSend(@retrofit2.http.Part MultipartBody.Part paramPart, @Path ("id") String paramString);

    @Multipart
    @POST ("reply/submit/file/{id}")
    Call<Status> fileSend(@Part MultipartBody.Part paramPart1, @Part MultipartBody.Part paramPart2, @Path ("id") String paramString);

    @POST ("get/message/detail")
    Call<Message> getDetailReplies(@Body HashMap<String, Object> paramHashMap);

    @POST ("get/reply")
    Call<List<Reply>> getReplies(@Body HashMap<String, Object> paramHashMap);

    @POST ("reply")
    Call<Status> sendReply(@Body HashMap<String, Object> paramHashMap);
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.intefaces.IReply

 * JD-Core Version:    0.7.0.1

 */