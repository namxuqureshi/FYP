package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityTagHolder {
    @Bind (R.id.tag_refresh_list)
    SwipeRefreshLayout tagRefreshList;
    @Bind (R.id.list_note)
    ListView listNote;
    @Bind (R.id.fab)
    FloatingActionButton fab;
    @Bind (R.id.empty_view_text_lv_below)
    TextView emptyViewTextLvBelow;

    public ActivityTagHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public SwipeRefreshLayout getTagRefreshList() {
        return tagRefreshList;
    }

    public TextView getEmptyViewTextLvBelow() {
        return emptyViewTextLvBelow;
    }

    public ListView getListNote() {
        return listNote;
    }
}
