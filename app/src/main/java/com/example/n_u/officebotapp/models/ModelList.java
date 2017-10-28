package com.example.n_u.officebotapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModelList {
    @Expose
    @SerializedName ("friends")
    private List<Friend> friendList = new ArrayList<>();
    @Expose
    @SerializedName ("groups")
    private List<Group> groupList = new ArrayList<>();
    @Expose
    @SerializedName ("requests")
    private List<Request> requestList = new ArrayList<>();
    @Expose
    @SerializedName ("tags")
    private List<Tag> tagList = new ArrayList<>();
    @Expose
    @SerializedName ("users")
    private List<SearchUser> userList = new ArrayList<>();

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> paramList) {
        friendList = paramList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> paramList) {
        groupList = paramList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> paramList) {
        requestList = paramList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> paramList) {
        tagList = paramList;
    }

    public List<SearchUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SearchUser> paramList) {
        userList = paramList;
    }

    public String toString() {
        return "ModelList{friendList=" + friendList + ", groupList=" + groupList + ", requestList=" + requestList + ", userList=" + userList + ", tagList=" + tagList + '}';
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.ModelList

 * JD-Core Version:    0.7.0.1

 */