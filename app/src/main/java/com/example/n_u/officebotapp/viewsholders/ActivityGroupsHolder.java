package com.example.n_u.officebotapp.viewsholders;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityGroupsHolder {
    @Bind (R.id.content_main)
    RelativeLayout contentMain;
    @Bind (R.id.group_refresh_list)
    SwipeRefreshLayout groupRefreshList;
    @Bind (R.id.lv_expendable_group)
    ExpandableListView lvExpendableGroup;
    @Bind (R.id.empty_view_text_group_below)
    TextView emptyViewTextGroupBelow;

    public ActivityGroupsHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public RelativeLayout getContentMain() {
        return contentMain;
    }

    public SwipeRefreshLayout getGroupRefreshList() {
        return groupRefreshList;
    }

    public TextView getEmptyViewTextGroupBelow() {
        return emptyViewTextGroupBelow;
    }

    public ExpandableListView getLvExpendableGroup() {
        return lvExpendableGroup;
    }
}
