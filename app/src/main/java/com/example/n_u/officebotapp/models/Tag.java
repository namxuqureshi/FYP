package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

@Table (name = "Tags")
public class Tag
        extends Model {
    @Column (name = "created_at")
    @Expose
    private String created_at;
    @Column (name = "tag_id")
    @Expose
    private int id;
    @Column (name = "Name")
    @Expose
    private String name;
    @Column (name = "Ssn")
    @Expose
    private String ssn;
    @Column (name = "updated_at")
    @Expose
    private String updated_at;
    @Column (name = "user_id")
    @Expose
    private int user_id;
    @Column (name = "message_count")
    @Expose
    private String message_count;
    @Column (name = "time")
    @Expose
    private String time;
    @Column (name = "image_src")
    @Expose
    private String image_src;


    public Tag() {
        super();
    }

    public Tag(Tag paramTag) {
        id = paramTag.id;
        name = paramTag.name;
        user_id = paramTag.user_id;
        ssn = paramTag.ssn;
        created_at = paramTag.created_at;
        updated_at = paramTag.updated_at;
        message_count = paramTag.message_count;
        time = paramTag.time;
        image_src = paramTag.getImage_src();
    }

    public static List<Tag> getAll(int id) {
        return new Select().from(Tag.class).where("user_id = ?", id).orderBy("Name ASC").execute();
    }

    public static List<Tag> getAll(int id, boolean b) {
        return new Select().from(Tag.class).where("user_id != ?", id).orderBy("Name ASC").execute();
    }

    public static void setTagTotalMsg(int id, int total) {
        Tag t = new Select().from(Tag.class).where("tag_id = ?", id).executeSingle();
        t.setMessage_count(String.valueOf(total));
        t.save();
    }

    public static void setTagName(int id, String total) {
        Tag t = new Select().from(Tag.class).where("tag_id = ?", id).executeSingle();
        t.setName(total);
        t.save();
    }

    public static int getUserOwnerId(int id) {
        Tag t = new Select().from(Tag.class).where("tag_id = ?", id).orderBy("Name ASC").executeSingle();
        if (t != null)
            return t.getUser_id();
        else return 0;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String paramString) {
        created_at = paramString;
    }

    public String getName() {
        return name;
    }

    public void setName(String paramString) {
        name = paramString;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String paramString) {
        ssn = paramString;
    }

    public int getTagId() {
        return id;
    }

    public void setTagId(int paramInt) {
        id = paramInt;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String paramString) {
        updated_at = paramString;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int paramInt) {
        user_id = paramInt;
    }

    public String toString() {
        return "Tag{tagId=" + id + ", name='" + name + '\'' + ", user_id=" + user_id + ", ssn='" + ssn + '\'' + ", created_at='" + created_at + '\'' + ", updated_at='" + updated_at + '\'' + '}';
    }

    public String getMessage_count() {
        return message_count;
    }

    public void setMessage_count(String message_count) {
        this.message_count = message_count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
