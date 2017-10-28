package com.example.n_u.officebotapp.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class OfficeBotURI {
            private static final String IP = "https://officebot-ahmedbutt2015.c9users.io";
    //    https://officebot-ahmedbutt2015.c9users.io/
//    private static final String IP = "http://192.168.43.210:5000";
    private static final String BASE_SITE = IP;
    private static final String BASE_URI = IP + "/api/";
    private static final String FACEBOOK_LOGIN = IP + "/api/facebook/login";
    private static final String FILE_URL_PRE_FIX = IP + "/storage/audio/";
    private static final Gson GAIN_JSON = new GsonBuilder().create();
    private static final String REGISTER = IP + "/api/user/register";

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public static Gson gson;

    public static String getBaseSite() {
        return BASE_SITE;
    }

    private static String getBaseUri() {
        return BASE_URI;
    }

    public static String getFacebookLogin() {
        return FACEBOOK_LOGIN;
    }

    public static String getFileUrlPreFix() {
        return FILE_URL_PRE_FIX;
    }

    public static Gson getGainJson() {
        return GAIN_JSON;
    }

    public static String getRegister() {
        return REGISTER;
    }

    @NonNull
    public static Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseUri())
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create()))
                .build();
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.util.OfficeBotURI

 * JD-Core Version:    0.7.0.1

 */