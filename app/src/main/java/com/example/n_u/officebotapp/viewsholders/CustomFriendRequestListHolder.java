package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomFriendRequestListHolder {
    @Bind (R.id.child_card_group)
    CardView childCardGroup;
    @Bind (R.id.request_friend_name)
    TextView requestFriendName;
    @Bind (R.id.request_circle_view)
    CircleImageView requestCircleView;
    @Bind (R.id.request_time_view)
    TextView requestTimeView;
    @Bind (R.id.send_request_btn)
    Button sendRequestBtn;
    @Bind (R.id.text_view_msg)
    TextView textViewMsg;

    public CustomFriendRequestListHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getRequestFriendName() {
        return requestFriendName;
    }

    public Button getSendRequestBtn() {
        return sendRequestBtn;
    }

    public TextView getTextViewMsg() {
        return textViewMsg;
    }

    public CircleImageView getRequestCircleView() {
        return requestCircleView;
    }

    public CardView getChildCardGroup() {
        return childCardGroup;
    }

    public TextView getRequestTimeView() {
        return requestTimeView;
    }
}
