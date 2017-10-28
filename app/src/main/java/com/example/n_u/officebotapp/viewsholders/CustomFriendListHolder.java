package com.example.n_u.officebotapp.viewsholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomFriendListHolder {
    @Bind (R.id.layout_linear)
    LinearLayout layoutLinear;
    @Bind (R.id.friend_Circle_View)
    CircleImageView friendCircleView;
    @Bind (R.id.friend_list_name)
    TextView friendListName;

    public CustomFriendListHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public CircleImageView getFriendCircleView() {
        return friendCircleView;
    }

    public LinearLayout getLayoutLinear() {
        return layoutLinear;
    }

    public TextView getFriendListName() {
        return friendListName;
    }
}
