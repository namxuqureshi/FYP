package com.example.n_u.officebotapp.viewsholders;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityFriendListHolder {
    @Bind (R.id.content_main)
    RelativeLayout contentMain;
    @Bind (R.id.friend_list_refresh_list)
    SwipeRefreshLayout friendListRefreshList;
    @Bind (R.id.rv_friend_list)
    RecyclerView rvFriendList;
    @Bind (R.id.empty_view_text_friend_list_below)
    TextView emptyViewTextFriendListBelow;

    public ActivityFriendListHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getEmptyViewTextFriendListBelow() {
        return emptyViewTextFriendListBelow;
    }

    public RelativeLayout getContentMain() {
        return contentMain;
    }

    public SwipeRefreshLayout getFriendListRefreshList() {
        return friendListRefreshList;
    }

    public RecyclerView getRvFriendList() {
        return rvFriendList;
    }
}
