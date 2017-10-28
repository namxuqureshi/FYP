package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityNfcHolder {
    @Bind (R.id.backBtnImg)
    ImageView backBtnImg;
    @Bind (R.id.toolbar)
    Toolbar toolbar;
    @Bind (R.id.text_title_in_bar)
    TextView textTitleInBar;
//    @Bind (R.id.animate)
//    CircleImageView animate;
//    @Bind (R.id.animate2)
//    CircleImageView animate2;

    public ActivityNfcHolder(View view) {
        ButterKnife.bind(this, view);
    }

//    public CircleImageView getAnimate2() {
//        return animate2;
//    }

    public TextView getTextTitleInBar() {
        return textTitleInBar;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }

//    public CircleImageView getAnimate() {
//        return animate;
////    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
