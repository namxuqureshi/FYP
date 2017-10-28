package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityReplyHolder {
    @Bind (R.id.activity_message_main)
    RelativeLayout activityMessageMain;
    @Bind (R.id.linearLayout)
    AppBarLayout linearLayout;
    @Bind (R.id.backBtnImg)
    ImageView backBtnImg;
    @Bind (R.id.toolbar)
    Toolbar toolbar;
    @Bind (R.id.text_title_in_bar)
    TextView textTitleInBar;
    @Bind (R.id.child_card_group)
    CardView cardView3;
    @Bind (R.id.message_owner_name)
    TextView messageOwnerName;
    @Bind (R.id.message_main_time)
    TextView messageMainTime;
    @Bind (R.id.message_main_content)
    TextView messageMainContent;
    @Bind (R.id.message_main_view_by)
    TextView messageMainViewBy;
    @Bind (R.id.frame_audio)
    FrameLayout frameAudio;
    @Bind (R.id.audio_source_icon)
    ImageView audioSourceIcon;
    @Bind (R.id.audio_progress)
    ProgressBar audioProgress;
    @Bind (R.id.frame_other)
    FrameLayout frameOther;
    @Bind (R.id.file_source_icon)
    ImageView fileSourceIcon;
    @Bind (R.id.other_file_progress)
    ProgressBar otherFileProgress;
    @Bind (R.id.reply_list_refresh_list)
    SwipeRefreshLayout replyListRefreshList;
    @Bind (R.id.reply_list)
    ListView replyList;
    @Bind (R.id.empty_view_text_reply_below)
    TextView emptyViewTextReplyBelow;
    @Bind (R.id.reply_msg_edit_text)
    EditText replyMsgEditText;
    @Bind (R.id.send_other_options_fab)
    FloatingActionMenu floatMsgOptions;
    @Bind (R.id.menu_item_audio)
    FloatingActionButton menuItemAudio;
    @Bind (R.id.menu_item_file)
    FloatingActionButton menuItemFile;
    @Bind (R.id.send_msg_fab)
    FloatingActionMenu sendMsgFab;
    @Bind (R.id.refresh_reply)
    Button refresh;

    public ActivityReplyHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public Button getRefresh() {
        return refresh;
    }

    public FloatingActionMenu getFloatMsgOptions() {
        return floatMsgOptions;
    }

    public FrameLayout getFrameAudio() {
        return frameAudio;
    }

    public FloatingActionButton getMenuItemAudio() {
        return menuItemAudio;
    }

    public ProgressBar getAudioProgress() {
        return audioProgress;
    }

    public TextView getMessageMainContent() {
        return messageMainContent;
    }

    public TextView getMessageMainTime() {
        return messageMainTime;
    }

    public TextView getEmptyViewTextReplyBelow() {
        return emptyViewTextReplyBelow;
    }

    public TextView getMessageOwnerName() {
        return messageOwnerName;
    }

    public ListView getReplyList() {
        return replyList;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }

    public ImageView getFileSourceIcon() {
        return fileSourceIcon;
    }

    public RelativeLayout getActivityMessageMain() {
        return activityMessageMain;
    }

    public ImageView getAudioSourceIcon() {
        return audioSourceIcon;
    }

    public FrameLayout getFrameOther() {
        return frameOther;
    }

    public SwipeRefreshLayout getReplyListRefreshList() {
        return replyListRefreshList;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public CardView getCardView3() {
        return cardView3;
    }

    public TextView getMessageMainViewBy() {
        return messageMainViewBy;
    }

    public ProgressBar getOtherFileProgress() {
        return otherFileProgress;
    }

    public FloatingActionButton getMenuItemFile() {
        return menuItemFile;
    }

    public TextView getTextTitleInBar() {
        return textTitleInBar;
    }

    public FloatingActionMenu getSendMsgFab() {
        return sendMsgFab;
    }

    public AppBarLayout getLinearLayout() {
        return linearLayout;
    }

    public EditText getReplyMsgEditText() {
        return replyMsgEditText;
    }
}
