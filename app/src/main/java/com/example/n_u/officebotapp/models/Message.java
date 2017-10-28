package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

@Table (name = "Messages")
public class Message extends Model {
    @Expose
    @Column (name = "audio_src")
    @SerializedName ("audio_src")
    private String audio_src;
    @Expose
    @Column (name = "case_type")
    @SerializedName ("case_type")
    private int case_type;
    @Expose
    @Column (name = "content")
    @SerializedName ("content")
    private String content;
    @Expose
    @Column (name = "created_at")
    @SerializedName ("created_at")
    private String created_at;
    @Expose
    @Column (name = "data_src")
    @SerializedName ("data_src")
    private String data_src;
    @Expose
    @Column (name = "friends_only")
    @SerializedName ("friends_only")
    private int friends_only;
    @Expose
    @Column (name = "message_id")
    private int id;
    @Expose
    @Column (name = "public")
    @SerializedName ("public")
    private int mpublic;
    @Expose
//    @Column(name = "replies")
    @SerializedName ("replies")
    private List<Reply> replies;
    @Expose
    @Column (name = "seen_by")
    @SerializedName ("seen_by")
    private int seen_by;
    @Expose
    @Column (name = "tag_id")
    @SerializedName ("tag_id")
    private int tag_id;
    @Expose
    @Column (name = "timeout")
    @SerializedName ("timeout")
    private String timeout;
    @Expose
    @Column (name = "type")
    @SerializedName ("type")
    private String type;
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
    @Expose
    @Column (name = "reply_count")
    @SerializedName ("reply_count")
    private String reply_count;


    public Message() {
        super();
    }

    public Message(Message other) {
        this.audio_src = other.audio_src;
        this.case_type = other.case_type;
        this.content = other.content;
        this.created_at = other.created_at;
        this.data_src = other.data_src;
        this.friends_only = other.friends_only;
        this.id = other.id;
        this.mpublic = other.mpublic;
        this.replies = other.replies;
        this.seen_by = other.seen_by;
        this.tag_id = other.tag_id;
        this.timeout = other.timeout;
        this.type = other.type;
        this.updated_at = other.updated_at;
        this.user_id = other.user_id;
        this.time = other.time;
        this.reply_count = other.reply_count;
    }

    public static Collection<? extends Message> getAll(int id, String s) {
        return new Select().
                from(Message.class).
                where("user_id = ?", Integer.parseInt(s)).
                where("tag_id = ?", id).
                orderBy("id DESC").execute();
    }

    public static Collection<? extends Message> getAll() {
        return new Select().
                from(Message.class).
                orderBy("id DESC").execute();
    }

    public static Collection<? extends Message> getAll(int id, int user_id) {
        return new Select().
                from(Message.class).
                where("user_id != ?", user_id).
                where("tag_id = ?", id).
                orderBy("id DESC").execute();
    }

    public static Collection<? extends Message> getAllOtherTag(int id, int user_id) {
        return new Select().
                from(Message.class).
                where("user_id = ?", user_id).
                where("tag_id = ?", id).
                orderBy("id DESC").execute();
    }

    public String getAudio_src() {
        return this.audio_src;
    }

    public void setAudio_src(String paramString) {
        this.audio_src = paramString;
    }

    public int getCase_type() {
        return this.case_type;
    }

    public void setCase_type(int paramInt) {
        this.case_type = paramInt;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String paramString) {
        this.content = paramString;
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

    public int getFriends_only() {
        return this.friends_only;
    }

    public void setFriends_only(int paramInt) {
        this.friends_only = paramInt;
    }

    public int getMsgId() {
        return id;
    }

    public void setMsgId(int i) {
        id = i;
    }

    public int getMpublic() {
        return this.mpublic;
    }

    public void setMpublic(int paramInt) {
        this.mpublic = paramInt;
    }

    public List<Reply> getReplies() {
        return this.replies;
    }

    public void setReplies(List<Reply> paramList) {
        this.replies = paramList;
    }

    public int getSeen_by() {
        return this.seen_by;
    }

    public void setSeen_by(int paramInt) {
        this.seen_by = paramInt;
    }

    public int getTag_id() {
        return this.tag_id;
    }

    public void setTag_id(int paramInt) {
        this.tag_id = paramInt;
    }

    public String getTimeout() {
        return this.timeout;
    }

    public void setTimeout(String s) {
        this.timeout = s;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String paramString) {
        this.type = paramString;
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
        return "Message{id=" + this.id + ", content='" + this.content + '\'' + ", user_id=" + this.user_id + ", tag_id=" + this.tag_id + ", mpublic=" + this.mpublic + ", type='" + this.type + '\'' + ", audio_src='" + this.audio_src + '\'' + ", data_src='" + this.data_src + '\'' + ", timeout='" + this.timeout + '\'' + ", created_at='" + this.created_at + '\'' + ", updated_at='" + this.updated_at + '\'' + ", case_type=" + this.case_type + ", friends_only=" + this.friends_only + ", seen_by=" + this.seen_by + ", replies=" + this.replies + '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Message

 * JD-Core Version:    0.7.0.1

 */