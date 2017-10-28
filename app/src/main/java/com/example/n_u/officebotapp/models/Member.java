package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table (name = "Members")
public class Member extends Model {

    @Expose
    @Column (name = "group_id")
    @SerializedName ("group_id")
    private int mGroupId;

    @Expose
    @Column (name = "member_id")
    @SerializedName ("id")
    private int mMemberId;

    @Expose
    @Column (name = "name")
    @SerializedName ("name")
    private String mName;

    @Expose
    @Column (name = "user_id")
    @SerializedName ("user_id")
    private int mUserId;

    public Member() {
        super();
    }

    public Member(Member other) {
        this.mGroupId = other.mGroupId;
        this.mMemberId = other.mMemberId;
        this.mName = other.mName;
        this.mUserId = other.mUserId;
    }

    public static List<Member> getAll(int id) {
        return new Select().from(Member.class).where("group_id = ?", id).orderBy("name ASC").execute();
    }

    public int getGroupId() {
        return mGroupId;
    }

    public int getmMemberId() {
        return mMemberId;
    }

    public void setmMemberId(int mMemberId) {
        this.mMemberId = mMemberId;
    }

    public String getName() {
        return mName;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setmGroupId(int mGroupId) {
        this.mGroupId = mGroupId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

}
