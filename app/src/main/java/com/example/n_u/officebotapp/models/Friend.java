package com.example.n_u.officebotapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.Collection;

@Table (name = "Friends")
public class Friend
        extends Model {
    private boolean checkBox = false;
    @Column (name = "friend_id")
    @Expose
    private int id;
    @Column (name = "name")
    @Expose
    private String name;

    public Friend() {
        super();
    }

    public Friend(Friend friend) {
        name = friend.name;
        id = friend.id;
        checkBox = friend.checkBox;
    }

    public static Collection<? extends Friend> getAll() {
        return new Select().from(Friend.class).orderBy("name ASC").execute();
    }

    public int getFriendId() {
        return id;
    }

    public void setFriendId(int paramInt) {
        id = paramInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String paramString) {
        name = paramString;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean paramBoolean) {
        checkBox = paramBoolean;
    }

    public String toString() {
        return "Friend{name='" + name + ", friendId=" + id + '}';
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Friend

 * JD-Core Version:    0.7.0.1

 */