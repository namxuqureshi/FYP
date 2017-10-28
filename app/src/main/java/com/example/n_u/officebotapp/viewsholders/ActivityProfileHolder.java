package com.example.n_u.officebotapp.viewsholders;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfileHolder {
    @Bind (R.id.activity_profile)
    ConstraintLayout activityProfile;
    @Bind (R.id.rl1)
    RelativeLayout rl1;
    @Bind (R.id.user_name)
    TextView userName;
    @Bind (R.id.circleViewProfile)
    CircleImageView circleViewProfile;
    @Bind (R.id.tags)
    FrameLayout tags;
    @Bind (R.id.groups)
    FrameLayout groups;
    @Bind (R.id.friends)
    FrameLayout friends;

    public ActivityProfileHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public FrameLayout getFriends() {
        return friends;
    }

    public RelativeLayout getRl1() {
        return rl1;
    }

    public FrameLayout getGroups() {
        return groups;
    }

    public ConstraintLayout getActivityProfile() {
        return activityProfile;
    }

    public TextView getUserName() {
        return userName;
    }

    public CircleImageView getCircleViewProfile() {
        return circleViewProfile;
    }

    public FrameLayout getTags() {
        return tags;
    }
}
