package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TagMessageHolder
        extends RecyclerView.ViewHolder {
    private TextView messageuser;
    private CircleImageView profileimage;
    private TextView tagmessagecontent;
    private TextView tagmessagetime;
    private TextView tagreplytotal;
    private TextView tagdelmsg;
    private RelativeLayout msgParentLayout;

    public TagMessageHolder(View view) {
        super(view);
        tagmessagetime = ((TextView) view.findViewById(R.id.tag_message_time));
        tagmessagecontent = ((TextView) view.findViewById(R.id.tag_message_content));
        messageuser = ((TextView) view.findViewById(R.id.message_user));
        profileimage = ((CircleImageView) view.findViewById(R.id.profile_image));
        tagreplytotal = (TextView) view.findViewById(R.id.total_reply);
        msgParentLayout = (RelativeLayout) view.findViewById(R.id.msg_parent_layout);
        tagdelmsg = (TextView) view.findViewById(R.id.tag_del_msg);
    }

    public android.widget.TextView getTagdelmsg() {
        return tagdelmsg;
    }

    public TextView getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(TextView paramTextView) {
        messageuser = paramTextView;
    }

    public CircleImageView getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(CircleImageView paramCircleImageView) {
        profileimage = paramCircleImageView;
    }

    public TextView getTagmessagecontent() {
        return tagmessagecontent;
    }

    public void setTagmessagecontent(TextView paramTextView) {
        tagmessagecontent = paramTextView;
    }

    public TextView getTagmessagetime() {
        return tagmessagetime;
    }

    public void setTagmessagetime(TextView paramTextView) {
        tagmessagetime = paramTextView;
    }

    public TextView getTagreplytotal() {
        return tagreplytotal;
    }

    public void setTagreplytotal(TextView tagreplytotal) {
        this.tagreplytotal = tagreplytotal;
    }

    public RelativeLayout getMsgParentLayout() {
        return msgParentLayout;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.viewsholders.TagMessageFragmentHolder

 * JD-Core Version:    0.7.0.1

 */