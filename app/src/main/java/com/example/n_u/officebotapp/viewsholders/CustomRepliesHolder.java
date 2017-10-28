package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

public class CustomRepliesHolder
        extends RecyclerView.ViewHolder {
    private ProgressBar audioBar = null;
    private ImageView audioFile = null;
    private FrameLayout frameAudio = null;
    private FrameLayout frameOther = null;
    private ProgressBar otherBar = null;
    private ImageView otherFile = null;
    private TextView replyMainMsg = null;
    private TextView replyMainTime = null;
    private TextView replyMainViewSent = null;
    private TextView replyMainViewNonSent = null;
    private TextView replyOwnerName = null;

    public CustomRepliesHolder(View paramView) {
        super(paramView);
        replyMainViewSent = ((TextView) paramView.findViewById(R.id.reply_main_sent));
        replyMainMsg = ((TextView) paramView.findViewById(R.id.reply_main_msg));
        replyMainTime = ((TextView) paramView.findViewById(R.id.reply_main_time));
        replyOwnerName = ((TextView) paramView.findViewById(R.id.reply_owner_name));
        frameAudio = ((FrameLayout) paramView.findViewById(R.id.frame_audio_reply));
        frameOther = ((FrameLayout) paramView.findViewById(R.id.frame_other_reply));
        audioFile = ((ImageView) paramView.findViewById(R.id.audio_source_icon));
        otherFile = ((ImageView) paramView.findViewById(R.id.file_source_icon));
        audioBar = ((ProgressBar) paramView.findViewById(R.id.audio_progress));
        otherBar = ((ProgressBar) paramView.findViewById(R.id.other_file_progress));
        replyMainViewNonSent = ((TextView) paramView.findViewById(R.id.reply_main_non_sent));
        replyMainViewNonSent.setVisibility(View.GONE);
        replyMainViewSent.setVisibility(View.VISIBLE);
        replyMainTime.setTextSize(2, 8.0F);
    }

    public ProgressBar getAudioBar() {
        return audioBar;
    }

    public ImageView getAudioFile() {
        return audioFile;
    }

    public FrameLayout getFrameAudio() {
        return frameAudio;
    }

    public FrameLayout getFrameOther() {
        return frameOther;
    }

    public ProgressBar getOtherBar() {
        return otherBar;
    }

    public ImageView getOtherFile() {
        return otherFile;
    }

    public TextView getReplyMainMsg() {
        return replyMainMsg;
    }

    public TextView getReplyMainTime() {
        return replyMainTime;
    }

    public TextView getReplyMainViewSent() {
        return replyMainViewSent;
    }

    public TextView getReplyOwnerName() {
        return replyOwnerName;
    }

    public TextView getReplyMainViewNonSent() {
        return replyMainViewNonSent;
    }

    public void setReplyMainViewNonSent(TextView replyMainViewNonSent) {
        replyMainViewNonSent = replyMainViewNonSent;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.viewsholders.CustomRepliesHolder

 * JD-Core Version:    0.7.0.1

 */