package com.example.n_u.officebotapp.viewsholders;

import android.view.View;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NavHeaderNavigationHolder {
    @Bind (R.id.nav_image)
    CircleImageView navImage;
    @Bind (R.id.nav_name_option)
    TextView navNameOption;

    public NavHeaderNavigationHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getNavNameOption() {
        return navNameOption;
    }

    public CircleImageView getNavImage() {
        return navImage;
    }
}
