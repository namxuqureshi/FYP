package com.example.n_u.officebotapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @Expose
    @SerializedName ("login")
    private boolean login;
    @Expose
    @SerializedName ("user")
    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User paramUser) {
        this.user = paramUser;
    }

    public boolean isLogin() {
        return this.login;
    }

    public void setLogin(boolean paramBoolean) {
        this.login = paramBoolean;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.models.Login

 * JD-Core Version:    0.7.0.1

 */