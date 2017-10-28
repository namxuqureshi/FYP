package com.example.n_u.officebotapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {
    @Expose
    @SerializedName ("add")
    private boolean add;
    @Expose
    @SerializedName ("block")
    private boolean block;
    @Expose
    @SerializedName ("delete")
    private boolean delete;
    @Expose
    @SerializedName ("error")
    private String error;
    @Expose
    @SerializedName ("exist")
    private boolean exist;
    @Expose
    @SerializedName ("friends")
    private boolean friends;
    @Expose
    @SerializedName ("group")
    private boolean group;
    @Expose
    @SerializedName ("members")
    private boolean members;
    @Expose
    @SerializedName ("message")
    private String message;
    @Expose
    @SerializedName ("message_id")
    private String message_id;
    @Expose
    @SerializedName ("owner")
    private boolean owner;
    @Expose
    @SerializedName ("permission_set")
    private boolean per_set;
    @Expose
    @SerializedName ("permission")
    private String permission;
    @Expose
    @SerializedName ("reply")
    private boolean reply;
    @Expose
    @SerializedName ("reply_id")
    private int replyId;
    @Expose
    @SerializedName ("request")
    private boolean request;
    @Expose
    @SerializedName ("result")
    private String result;
    @Expose
    @SerializedName ("status")
    private String status;
    @Expose
    @SerializedName ("tag_id")
    private int tagId;
    @Expose
    @SerializedName ("user2_id")
    private int userId;
    @Expose
    @SerializedName ("Tag")
    private String tagResponse;


    public String getError() {
        return this.error;
    }

    public void setError(String paramString) {
        this.error = paramString;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String paramString) {
        this.message = paramString;
    }

    public String getMessage_id() {
        return this.message_id;
    }

    public void setMessage_id(String paramString) {
        this.message_id = paramString;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String paramString) {
        this.permission = paramString;
    }

    public int getReplyId() {
        return this.replyId;
    }

    public void setReplyId(int paramInt) {
        this.replyId = paramInt;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String paramString) {
        this.result = paramString;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String paramString) {
        this.status = paramString;
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int paramInt) {
        this.tagId = paramInt;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int paramInt) {
        this.userId = paramInt;
    }

    public boolean isAdd() {
        return this.add;
    }

    public void setAdd(boolean paramBoolean) {
        this.add = paramBoolean;
    }

    public boolean isBlock() {
        return this.block;
    }

    public void setBlock(boolean paramBoolean) {
        this.block = paramBoolean;
    }

    public boolean isDelete() {
        return this.delete;
    }

    public void setDelete(boolean paramBoolean) {
        this.delete = paramBoolean;
    }

    public boolean isExist() {
        return this.exist;
    }

    public void setExist(boolean paramBoolean) {
        this.exist = paramBoolean;
    }

    public boolean isFriends() {
        return this.friends;
    }

    public void setFriends(boolean paramBoolean) {
        this.friends = paramBoolean;
    }

    public boolean isGroup() {
        return this.group;
    }

    public void setGroup(boolean paramBoolean) {
        this.group = paramBoolean;
    }

    public boolean isMembers() {
        return this.members;
    }

    public void setMembers(boolean paramBoolean) {
        this.members = paramBoolean;
    }

    public boolean isOwner() {
        return this.owner;
    }

    public void setOwner(boolean paramBoolean) {
        this.owner = paramBoolean;
    }

    public boolean isPer_set() {
        return this.per_set;
    }

    public void setPer_set(boolean paramBoolean) {
        this.per_set = paramBoolean;
    }

    public boolean isReply() {
        return this.reply;
    }

    public void setReply(boolean paramBoolean) {
        this.reply = paramBoolean;
    }

    public boolean isRequest() {
        return this.request;
    }

    public void setRequest(boolean paramBoolean) {
        this.request = paramBoolean;
    }

    public String toString() {
        return "Status{per_set=" + this.per_set + ", userId=" + this.userId + ", tagId=" + this.tagId + ", replyId=" + this.replyId + ", group=" + this.group + ", reply=" + this.reply + ", delete=" + this.delete + ", members=" + this.members + ", friends=" + this.friends + ", block=" + this.block + ", request=" + this.request + ", exist=" + this.exist + ", owner=" + this.owner + ", add=" + this.add + ", status='" + this.status + '\'' + ", error='" + this.error + '\'' + ", result='" + this.result + '\'' + ", message='" + this.message + '\'' + ", permission='" + this.permission + '\'' + ", message_id='" + this.message_id + '\'' + '}';
    }

    public String getTagResponse() {
        return tagResponse;
    }

    public void setTagResponse(String tagResponse) {
        this.tagResponse = tagResponse;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Status

 * JD-Core Version:    0.7.0.1

 */