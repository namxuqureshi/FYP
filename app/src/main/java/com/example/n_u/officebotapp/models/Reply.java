package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

@Table (name = "Replies")
public class Reply extends Model {
    @Expose
    @Column (name = "audio_src")
    @SerializedName ("audio_src")
    private String audio_src;
    @Expose
    @Column (name = "created_at")
    @SerializedName ("created_at")
    private String created_at;
    @Expose
    @Column (name = "data_src")
    @SerializedName ("data_src")
    private String data_src;
    @Expose
    @Column (name = "message_id")
    @SerializedName ("message_id")
    private int message_id;
    @Expose
    @Column (name = "reply")
    @SerializedName ("reply")
    private String reply;
    @Expose
    @Column (name = "reply_id")
    @SerializedName ("id")
    private int id;
    @Expose
    @Column (name = "updated_at")
    @SerializedName ("updated_at")
    private String updated_at;
    @Expose
    @Column (name = "user_id")
    @SerializedName ("user_id")
    private int user_id;
    @Expose
    @Column (name = "time")
    @SerializedName ("time")
    private String time;

    public Reply() {
        super();
    }

    public Reply(Reply other) {
        this.audio_src = other.audio_src;
        this.created_at = other.created_at;
        this.data_src = other.data_src;
        this.message_id = other.message_id;
        this.reply = other.reply;
        this.id = other.id;
        this.updated_at = other.updated_at;
        this.user_id = other.user_id;
        this.time = other.time;
    }

    public static Collection<? extends Reply> getAll() {
        return new Select().from(Reply.class).orderBy("id ASC").execute();
    }

    public static Collection<? extends Reply> getAll(int id) {
        return new Select().from(Reply.class).where("message_id = ?", id).orderBy("id ASC").execute();
    }

    public String getAudio_src() {
        return this.audio_src;
    }

    public void setAudio_src(String paramString) {
        this.audio_src = paramString;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String paramString) {
        this.created_at = paramString;
    }

    public String getData_src() {
        return this.data_src;
    }

    public void setData_src(String paramString) {
        this.data_src = paramString;
    }

    public int getMessage_id() {
        return this.message_id;
    }

    public void setMessage_id(int paramInt) {
        this.message_id = paramInt;
    }

    public String getReply() {
        return this.reply;
    }

    public void setReply(String paramString) {
        this.reply = paramString;
    }

    public int getReplyId() {
        return this.id;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String paramString) {
        this.updated_at = paramString;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int paramInt) {
        this.user_id = paramInt;
    }

    public String toString() {
        return "Reply{id=" + this.id + ", user_id=" + this.user_id + ", message_id=" + this.message_id + ", reply='" + this.reply + '\'' + ", created_at='" + this.created_at + '\'' + ", updated_at='" + this.updated_at + '\'' + ", audio_src='" + this.audio_src + '\'' + ", data_src='" + this.data_src + '\'' + '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Reply

 * JD-Core Version:    0.7.0.1

 */