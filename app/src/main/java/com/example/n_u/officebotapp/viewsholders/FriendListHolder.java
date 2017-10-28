package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListHolder
        extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private CircleImageView friendCircleView;
    private LinearLayout ll;

    public FriendListHolder(View paramView) {
        super(paramView);
        nameTextView = ((TextView) paramView.findViewById(R.id.friend_list_name));
        friendCircleView = (CircleImageView) paramView.findViewById(R.id.friend_Circle_View);
        ll = (LinearLayout) paramView.findViewById(R.id.layout_linear);
    }

    public TextView getNameTextView() {
        return this.nameTextView;
    }

    public void setNameTextView(TextView paramTextView) {
        this.nameTextView = paramTextView;
    }

    public CircleImageView getFriendCircleView() {
        return friendCircleView;
    }

    public void setFriendCircleView(CircleImageView friendCircleView) {
        this.friendCircleView = friendCircleView;
    }

    public LinearLayout getLl() {
        return ll;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.viewsholders.FriendListHolder

 * JD-Core Version:    0.7.0.1

 */