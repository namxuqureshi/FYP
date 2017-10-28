package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomTagHolder {
    @Bind (R.id.tag_pic_circle)
    CardView tagPicCircle;
    @Bind (R.id.tag_image)
    CircleImageView tagImage;
    @Bind (R.id.tag_name)
    TextView tagName;
    @Bind (R.id.total_message_tab)
    TextView totalMessageTab;

    public CustomTagHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getTotalMessageTab() {
        return totalMessageTab;
    }

    public CircleImageView getTagImage() {
        return tagImage;
    }

    public CardView getTagPicCircle() {
        return tagPicCircle;
    }

    public TextView getTagName() {
        return tagName;
    }
}
