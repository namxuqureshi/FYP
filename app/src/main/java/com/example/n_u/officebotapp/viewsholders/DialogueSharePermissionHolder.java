package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DialogueSharePermissionHolder {
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
    @Bind (R.id.checkBoxPublic)
    RadioButton checkBoxPublic;
    @Bind (R.id.checkBoxFriend)
    RadioButton checkBoxFriend;
    @Bind (R.id.friend_select_list)
    TextView friendSelectList;
    @Bind (R.id.checkBoxGroup)
    RadioButton checkBoxGroup;
    @Bind (R.id.group_select_list)
    TextView groupSelectList;
    @Bind (R.id.cancel_permission)
    Button cancelPermission;
    @Bind (R.id.go_permission)
    Button goPermission;

    public DialogueSharePermissionHolder(LayoutInflater inflater, ViewGroup parent) {
        this(inflater.inflate(R.layout.dialogue_share_permission, parent, false));
    }

    public DialogueSharePermissionHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getGroupSelectList() {
        return groupSelectList;
    }

    public RadioButton getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public Button getGoPermission() {
        return goPermission;
    }

    public RadioButton getCheckBoxFriend() {
        return checkBoxFriend;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }

    public RadioButton getCheckBoxPublic() {
        return checkBoxPublic;
    }

    public TableLayout getTbl() {
        return tbl;
    }

    public TextView getTextTitleInBar() {
        return textTitleInBar;
    }

    public TextView getFriendSelectList() {
        return friendSelectList;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public RelativeLayout getActivitySharePermission() {
        return activitySharePermission;
    }

    public Button getCancelPermission() {
        return cancelPermission;
    }
}
