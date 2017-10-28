package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListGroupHolder {
    @Bind (R.id.child_card_group)
    CardView childCardGroup;
    @Bind (R.id.group_pic)
    CircleImageView groupPic;
    @Bind (R.id.lblListHeader)
    TextView lblListHeader;
    @Bind (R.id.group_member_label)
    TextView groupMemberLabel;
    @Bind (R.id.group_member)
    TextView groupMember;

    public ListGroupHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getGroupMember() {
        return groupMember;
    }

    public CircleImageView getGroupPic() {
        return groupPic;
    }

    public TextView getGroupMemberLabel() {
        return groupMemberLabel;
    }

    public TextView getLblListHeader() {
        return lblListHeader;
    }

    public CardView getChildCardGroup() {
        return childCardGroup;
    }
}
