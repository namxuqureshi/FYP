package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DialogueDoNotSharePermissionHolder {
    @Bind (R.id.activity_share_permission)
    RelativeLayout activitySharePermission;
    @Bind (R.id.backBtnImg)
    ImageView backBtnImg;
    @Bind (R.id.toolbar)
    Toolbar toolbar;
    @Bind (R.id.text_title_in_bar)
    TextView textTitleInBar;
    @Bind (R.id.tbl)
    TableLayout tbl;
    @Bind (R.id.checkBoxUser)
    RadioButton checkBoxUser;
    @Bind (R.id.user_select_list)
    TextView userSelectList;
    @Bind (R.id.checkBoxGroup)
    RadioButton checkBoxGroup;
    @Bind (R.id.group_select_list)
    TextView groupSelectList;
    @Bind (R.id.cancel_permission)
    Button cancelPermission;
    @Bind (R.id.go_permission)
    Button goPermission;

    public DialogueDoNotSharePermissionHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getTextTitleInBar() {
        return textTitleInBar;
    }

    public RelativeLayout getActivitySharePermission() {
        return activitySharePermission;
    }

    public TextView getUserSelectList() {
        return userSelectList;
    }

    public RadioButton getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public TableLayout getTbl() {
        return tbl;
    }

    public RadioButton getCheckBoxUser() {
        return checkBoxUser;
    }

    public Button getCancelPermission() {
        return cancelPermission;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }

    public Button getGoPermission() {
        return goPermission;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getGroupSelectList() {
        return groupSelectList;
    }
}
