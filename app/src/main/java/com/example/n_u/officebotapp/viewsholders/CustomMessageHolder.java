package com.example.n_u.officebotapp.viewsholders;

import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomMessageHolder {
    @Bind (R.id.profile_image)
    CircleImageView profileImage;
    @Bind (R.id.message_user)
    TextView messageUser;
    @Bind (R.id.tag_message_content)
    TextView tagMessageContent;
    @Bind (R.id.tag_message_time)
    TextView tagMessageTime;
    @Bind (R.id.total_reply)
    TextView totalReply;

    public CustomMessageHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getTotalReply() {
        return totalReply;
    }

    public CircleImageView getProfileImage() {
        return profileImage;
    }

    public TextView getMessageUser() {
        return messageUser;
    }

    public TextView getTagMessageTime() {
        return tagMessageTime;
    }

    public TextView getTagMessageContent() {
        return tagMessageContent;
    }
}
