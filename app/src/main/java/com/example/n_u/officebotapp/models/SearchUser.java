package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

@Table (name = "SearchUsers")
public class SearchUser extends Model {
    private boolean checkBox = false;
    @Expose
    @Column (name = "created_at")
    @SerializedName ("created_at")
    private String created_at;
    @Expose
    @Column (name = "email")
    @SerializedName ("email")
    private String email;
    @Expose
    @Column (name = "friends")
    @SerializedName ("friends")
    private boolean friends;
    @Expose
    @Column (name = "name")
    @SerializedName ("name")
    private String name;
    private boolean pNone;
    private boolean pRead;
    private boolean pReadWrite;
    @Expose
    @Column (name = "phone")
    @SerializedName ("phone")
    private String phone;
    @Expose
    @Column (name = "request")
    @SerializedName ("request")
    private boolean request;
    @Expose
    @Column (name = "search_id")
    @SerializedName ("id")
    private int id;
    @Expose
    @Column (name = "updated_at")
    @SerializedName ("updated_at")
    private String updated_at;
    @Expose
    @Column (name = "image_src")
    @SerializedName ("image_src")
    private String image_src;

    public SearchUser() {
        super();
    }

    public SearchUser(SearchUser other) {
        this.checkBox = other.checkBox;
        this.created_at = other.created_at;
        this.email = other.email;
        this.friends = other.friends;
        this.name = other.name;
        this.pNone = other.pNone;
        this.pRead = other.pRead;
        this.pReadWrite = other.pReadWrite;
        this.phone = other.phone;
        this.request = other.request;
        this.id = other.id;
        this.updated_at = other.updated_at;
        this.image_src = other.image_src;
    }

    public static String getNameUserOther(int id) {
        SearchUser user = new Select().from(SearchUser.class).where("search_id = ?", id).executeSingle();
        if (user != null)
            return user.getName();
        else return "Unknown";
    }

    public static SearchUser getUserOther(int id) {
        SearchUser user = new Select().from(SearchUser.class).where("search_id = ?", id).executeSingle();
        if (user != null)
            return user;
        else return null;
    }

    public static Collection<? extends SearchUser> getAll() {
        return new Select().from(SearchUser.class).orderBy("name ASC").execute();
    }

    public static SearchUser imagePath(int id) {
        SearchUser user = new Select().from(SearchUser.class).where("search_id = ?", id).executeSingle();
        if (user != null)
            return user;
        else
            return null;
    }

    public static SearchUser imagePath(String name) {
        SearchUser user = new Select().from(SearchUser.class).where("name = ?", name).executeSingle();
        if (user != null)
            return user;
        else
            return null;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String s) {
        this.created_at = s;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String s) {
        this.email = s;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String paramInteger) {
        this.phone = paramInteger;
    }

    public int getSearch_user_id() {
        return this.id;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String s) {
        this.updated_at = s;
    }

    public boolean isCheckBox() {
        return this.checkBox;
    }

    public void setCheckBox(boolean paramBoolean) {
        this.checkBox = paramBoolean;
    }

    public boolean isFriends() {
        return this.friends;
    }

    public void setFriends(boolean paramBoolean) {
        this.friends = paramBoolean;
    }

    public boolean isRequest() {
        return this.request;
    }

    public void setRequest(boolean paramBoolean) {
        this.request = paramBoolean;
    }

    public boolean ispNone() {
        return this.pNone;
    }

    public void setpNone(boolean paramBoolean) {
        this.pNone = paramBoolean;
    }

    public boolean ispRead() {
        return this.pRead;
    }

    public void setpRead(boolean paramBoolean) {
        this.pRead = paramBoolean;
    }

    public boolean ispReadWrite() {
        return this.pReadWrite;
    }

    public void setpReadWrite(boolean paramBoolean) {
        this.pReadWrite = paramBoolean;
    }

    public void setId(Integer paramInteger) {
        this.id = paramInteger;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
