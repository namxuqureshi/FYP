package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DialogueProfileHolder {
    @Bind (R.id.detail_side)
    CardView detailSide;
    @Bind (R.id.dg_profile_name)
    TextView dgProfileName;
    @Bind (R.id.dg_profile_email)
    TextView dgProfileEmail;
    @Bind (R.id.dg_profile_phone)
    TextView dgProfilePhone;
    @Bind (R.id.dg_float_ok)
    FloatingActionButton dgFloatOk;
    @Bind (R.id.dg_profile_img)
    CircleImageView dgProfileImg;

    public DialogueProfileHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getDgProfilePhone() {
        return dgProfilePhone;
    }

    public TextView getDgProfileEmail() {
        return dgProfileEmail;
    }

    public TextView getDgProfileName() {
        return dgProfileName;
    }

    public CardView getDetailSide() {
        return detailSide;
    }

    public CircleImageView getDgProfileImg() {
        return dgProfileImg;
    }

    public FloatingActionButton getDgFloatOk() {
        return dgFloatOk;
    }
}
