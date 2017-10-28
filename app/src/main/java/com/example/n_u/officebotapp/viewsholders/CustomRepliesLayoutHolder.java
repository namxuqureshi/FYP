package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomRepliesLayoutHolder {
    @Bind (R.id.child_card_group)
    CardView childCardGroup;
    @Bind (R.id.reply_owner_name)
    TextView replyOwnerName;
    @Bind (R.id.reply_main_time)
    TextView replyMainTime;
    @Bind (R.id.reply_main_msg)
    TextView replyMainMsg;
    @Bind (R.id.reply_main_non_sent)
    TextView replyMainNonSent;
    @Bind (R.id.reply_main_sent)
    TextView replyMainSent;
    @Bind (R.id.frame_audio_reply)
    FrameLayout frameAudioReply;
    @Bind (R.id.audio_source_icon)
    ImageView audioSourceIcon;
    @Bind (R.id.audio_progress)
    ProgressBar audioProgress;
    @Bind (R.id.frame_other_reply)
    FrameLayout frameOtherReply;
    @Bind (R.id.file_source_icon)
    ImageView fileSourceIcon;
    @Bind (R.id.other_file_progress)
    ProgressBar otherFileProgress;

    public CustomRepliesLayoutHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public ImageView getAudioSourceIcon() {
        return audioSourceIcon;
    }

    public ProgressBar getAudioProgress() {
        return audioProgress;
    }

    public ProgressBar getOtherFileProgress() {
        return otherFileProgress;
    }

    public FrameLayout getFrameOtherReply() {
        return frameOtherReply;
    }

    public TextView getReplyMainNonSent() {
        return replyMainNonSent;
    }

    public CardView getChildCardGroup() {
        return childCardGroup;
    }

    public TextView getReplyMainMsg() {
        return replyMainMsg;
    }

    public FrameLayout getFrameAudioReply() {
        return frameAudioReply;
    }

    public TextView getReplyOwnerName() {
        return replyOwnerName;
    }

    public TextView getReplyMainTime() {
        return replyMainTime;
    }

    public TextView getReplyMainSent() {
        return replyMainSent;
    }

    public ImageView getFileSourceIcon() {
        return fileSourceIcon;
    }
}
