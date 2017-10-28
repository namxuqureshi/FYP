package com.example.n_u.officebotapp.viewsholders;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TagMainFragementMessagesHolder {

    @Bind (R.id.post_note_refresh_list)
    SwipeRefreshLayout postNoteRefreshList;
    @Bind (R.id.list_note)
    DynamicListView listNote;
    @Bind (R.id.empty_view_notes)
    TextView emptyViewNotes;

    public TagMainFragementMessagesHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getEmptyViewNotes() {
        return emptyViewNotes;
    }

    public SwipeRefreshLayout getPostNoteRefreshList() {
        return postNoteRefreshList;
    }

    public DynamicListView getListNote() {
        return listNote;
    }
}
