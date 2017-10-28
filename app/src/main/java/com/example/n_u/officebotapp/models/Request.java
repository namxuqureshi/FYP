package com.example.n_u.officebotapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {
    @Expose
    @SerializedName ("name")
    private String name;
    @Expose
    @SerializedName ("id")
    private int requestId;
    @Expose
    @SerializedName ("fromUser_id")
    private int requestUserId;
    @Expose
    @SerializedName ("created_at")
    private String created_at;
    @Expose
    @SerializedName ("updated_at")
    private String updated_at;

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void setRequestId(int paramInt) {
        this.requestId = paramInt;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(int requestUserId) {
        this.requestUserId = requestUserId;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Request

 * JD-Core Version:    0.7.0.1

 */