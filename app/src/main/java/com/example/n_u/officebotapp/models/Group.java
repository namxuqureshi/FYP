package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

@Table (name = "Groups")
public class Group extends Model {
    private boolean checkBox = false;
    @Expose
    @Column (name = "group_id")
    @SerializedName ("id")
    private int groupId;
    @Expose
    @SerializedName ("member")
    private List<Member> member;
    @Expose
    @Column (name = "name")
    @SerializedName ("name")
    private String name;
    private boolean pNone;
    private boolean pRead;
    private boolean pReadWrite;
    @Expose
    @Column (name = "user_id")
    @SerializedName ("user_id")
    private int user_id;

    public Group() {
        super();
    }

    public Group(Group other) {
        this.checkBox = other.checkBox;
        this.groupId = other.groupId;
        this.member = other.member;
        this.name = other.name;
        this.pNone = other.pNone;
        this.pRead = other.pRead;
        this.pReadWrite = other.pReadWrite;
        this.user_id = other.user_id;
    }

    public Group(String paramString, int paramInt1, int paramInt2) {
        this.name = paramString;
        this.user_id = paramInt1;
        this.groupId = paramInt2;
    }

    public static Collection<? extends Group> getAll() {
        return new Select().from(Group.class).orderBy("name ASC").execute();
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int paramInt) {
        this.groupId = paramInt;
    }

    public List<Member> getMember() {
        return this.member;
    }

    public void setMember(List<Member> paramList) {
        this.member = paramList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int paramInt) {
        this.user_id = paramInt;
    }

    public boolean isCheckBox() {
        return this.checkBox;
    }

    public void setCheckBox(boolean paramBoolean) {
        this.checkBox = paramBoolean;
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

    public String toString() {
        return "Group{groupId=" + this.groupId + ", user_id=" + this.user_id + ", name='" + this.name + '\'' + ", member=" + this.member + '}';
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Group

 * JD-Core Version:    0.7.0.1

 */