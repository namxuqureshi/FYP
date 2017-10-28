package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListItemHolder {
    @Bind (R.id.child_card_group)
    CardView childCardGroup;
    @Bind (R.id.group_child_pic)
    CircleImageView groupChildPic;
    @Bind (R.id.lblListItem)
    TextView lblListItem;

    public ListItemHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public CardView getChildCardGroup() {
        return childCardGroup;
    }

    public CircleImageView getGroupChildPic() {
        return groupChildPic;
    }

    public TextView getLblListItem() {
        return lblListItem;
    }
}
