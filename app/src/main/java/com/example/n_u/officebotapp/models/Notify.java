package com.example.n_u.officebotapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by usman on 25-Aug-17.
 */

public class Notify {

    @SerializedName ("name")
    @Expose
    private String name;
    @SerializedName ("PushType")
    @Expose
    private String pushType;
    @SerializedName ("tag_id")
    @Expose
    private Integer tagId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "name='" + name + '\'' +
                ", pushType='" + pushType + " }";

    }

}
