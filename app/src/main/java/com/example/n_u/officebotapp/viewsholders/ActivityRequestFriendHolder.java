package com.example.n_u.officebotapp.viewsholders;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityRequestFriendHolder {
    @Bind (R.id.activity_request_friend)
    LinearLayout activityRequestFriend;
    @Bind (R.id.bar_search)
    LinearLayout barSearch;
    @Bind (R.id.backBtnImg)
    ImageView backBtnImg;
    @Bind (R.id.search_view)
    SearchView searchView;
    @Bind (R.id.search_refresh_list)
    SwipeRefreshLayout searchRefreshList;
    @Bind (R.id.search_list_view)
    RecyclerView searchListView;
    @Bind (R.id.empty_view_text_request_friend_list_below)
    TextView emptyViewTextRequestFriendListBelow;

    public ActivityRequestFriendHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public LinearLayout getBarSearch() {
        return barSearch;
    }

    public LinearLayout getActivityRequestFriend() {
        return activityRequestFriend;
    }

    public TextView getEmptyViewTextRequestFriendListBelow() {
        return emptyViewTextRequestFriendListBelow;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public SwipeRefreshLayout getSearchRefreshList() {
        return searchRefreshList;
    }

    public RecyclerView getSearchListView() {
        return searchListView;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }
}
