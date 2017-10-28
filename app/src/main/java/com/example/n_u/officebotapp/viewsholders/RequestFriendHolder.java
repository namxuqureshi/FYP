package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestFriendHolder
        extends RecyclerView.ViewHolder {
    private TextView msg;
    private TextView nameTextView;
    private Button sendBtn;
    private CircleImageView requestCircles;
    private RelativeLayout backgroundView;

    public RequestFriendHolder(View view) {
        super(view);
        nameTextView = (TextView) itemView.findViewById(R.id.request_friend_name);
        msg = (TextView) itemView.findViewById(R.id.text_view_msg);
        sendBtn = (Button) itemView.findViewById(R.id.send_request_btn);
        requestCircles = (CircleImageView) itemView.findViewById(R.id.request_circle_view);
        TextView tv = (TextView) itemView.findViewById(R.id.request_time_view);
        tv.setVisibility(View.INVISIBLE);
        backgroundView = (RelativeLayout) itemView.findViewById(com.example.n_u.officebotapp.R.id.background_view);

    }

    public TextView getMsg() {
        return this.msg;
    }

    public void setMsg(TextView paramTextView) {
        this.msg = paramTextView;
    }

    public TextView getNameTextView() {
        return this.nameTextView;
    }

    public void setNameTextView(TextView paramTextView) {
        this.nameTextView = paramTextView;
    }

    public Button getSendBtn() {
        return this.sendBtn;
    }

    public void setSendBtn(Button paramButton) {
        this.sendBtn = paramButton;
    }

    public CircleImageView getRequestCircles() {
        return requestCircles;
    }

    public void setRequestCircles(CircleImageView requestCircles) {
        this.requestCircles = requestCircles;
    }

    public android.widget.RelativeLayout getBackgroundView() {
        return backgroundView;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.viewsholders.RequestFriendHolder

 * JD-Core Version:    0.7.0.1

 */