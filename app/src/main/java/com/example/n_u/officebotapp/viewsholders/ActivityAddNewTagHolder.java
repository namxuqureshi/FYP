package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityAddNewTagHolder {
    @Bind (R.id.activity_add_new_tag)
    RelativeLayout activityAddNewTag;
    @Bind (R.id.tag_label)
    TextInputLayout tagLabel;
    @Bind (R.id.tag_name)
    EditText tagName;
    @Bind (R.id.button)
    Button button;
    @Bind (R.id.gridView)
    GridView gridView;

    public ActivityAddNewTagHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public RelativeLayout getActivityAddNewTag() {
        return activityAddNewTag;
    }

    public TextInputLayout getTagLabel() {
        return tagLabel;
    }

    public Button getButton() {
        return button;
    }

    public EditText getTagName() {
        return tagName;
    }

    public GridView getGridView() {
        return gridView;
    }
}
