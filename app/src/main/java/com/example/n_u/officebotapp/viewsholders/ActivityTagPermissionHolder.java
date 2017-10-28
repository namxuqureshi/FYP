package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityTagPermissionHolder {
    @Bind (R.id.activity_tag_permission)
    RelativeLayout activityTagPermission;
    @Bind (R.id.backBtnImg)
    ImageView backBtnImg;
    @Bind (R.id.toolbar)
    Toolbar toolbar;
    @Bind (R.id.text_title_in_bar)
    TextView textTitleInBar;
    @Bind (R.id.tbl)
    TableLayout tbl;
    @Bind (R.id.tag_permission_public)
    Button tagPermissionPublic;
    @Bind (R.id.checked_none_public)
    RadioButton checkedNonePublic;
    @Bind (R.id.checked_r_public)
    RadioButton checkedRPublic;
    @Bind (R.id.checked_r_w_public)
    RadioButton checkedRWPublic;
    @Bind (R.id.tag_permission_friend)
    CheckBox tagPermissionFriend;
    @Bind (R.id.tag_friend_select_list)
    TextView tagFriendSelectList;
    @Bind (R.id.tag_permission_group)
    CheckBox tagPermissionGroup;
    @Bind (R.id.tag_group_select_list)
    TextView tagGroupSelectList;
    @Bind (R.id.go_permission_tag)
    Button goPermissionTag;
    @Bind (R.id.cancel_permission_tag)
    Button cancelPermissionTag;

    public ActivityTagPermissionHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getTextTitleInBar() {
        return textTitleInBar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TableLayout getTbl() {
        return tbl;
    }

    public Button getCancelPermissionTag() {
        return cancelPermissionTag;
    }

    public RadioButton getCheckedRWPublic() {
        return checkedRWPublic;
    }

    public Button getGoPermissionTag() {
        return goPermissionTag;
    }

    public RelativeLayout getActivityTagPermission() {
        return activityTagPermission;
    }

    public CheckBox getTagPermissionGroup() {
        return tagPermissionGroup;
    }

    public TextView getTagGroupSelectList() {
        return tagGroupSelectList;
    }

    public RadioButton getCheckedRPublic() {
        return checkedRPublic;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }

    public Button getTagPermissionPublic() {
        return tagPermissionPublic;
    }

    public TextView getTagFriendSelectList() {
        return tagFriendSelectList;
    }

    public RadioButton getCheckedNonePublic() {
        return checkedNonePublic;
    }

    public CheckBox getTagPermissionFriend() {
        return tagPermissionFriend;
    }
}
