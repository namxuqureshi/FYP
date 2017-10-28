package com.example.n_u.officebotapp.intefaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface IFileDownload {
    @GET
    @Streaming
    Call<ResponseBody> fileUrlLink(@Url String paramString);
}