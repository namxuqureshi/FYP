package com.example.n_u.officebotapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @Expose
    @SerializedName ("created_at")
    private String createdAt;
    @Expose
    @SerializedName ("email")
    private String email;
    @Expose
    @SerializedName ("name")
    private String name;
    @Expose
    @SerializedName ("password")
    private String password;
    @Expose
    @SerializedName ("phone")
    private String phone;
    @Expose
    @SerializedName ("remember_token")
    private Object rememberToken;
    @Expose
    @SerializedName ("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName ("id")
    private Integer userId;
    @Expose
    @SerializedName ("image_src")
    private String image_src;

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String paramString) {
        this.createdAt = paramString;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String paramString) {
        this.email = paramString;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String paramString) {
        this.password = paramString;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String paramInteger) {
        this.phone = paramInteger;
    }

    public Object getRememberToken() {
        return this.rememberToken;
    }

    public void setRememberToken(Object paramObject) {
        this.rememberToken = paramObject;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String paramString) {
        this.updatedAt = paramString;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setId(Integer paramInteger) {
        this.userId = paramInteger;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
