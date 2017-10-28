package com.example.n_u.officebotapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileTagActivity
        extends NavigationActivity {

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_nav_profile);
        nav(getString(R.string.profile_activity));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUi();
    }

    private void setUi() {
        Object o = findViewById(R.id.user_name);
        ((TextView) o).setText(OBSession.getPreference(getString(R.string.name_key), this));
        if (((TextView) o).getText().length() > 8) {
            ((TextView) o).setTextSize(2, 20.0F);
        }
        CircleImageView cm = (CircleImageView) findViewById(R.id.circleViewProfile);
        cm.startAnimation(new AlphaAnimation(this, null));
        cm.animate();
        cm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(OBSession.getPreference(getString(R.string.name_key)
                        , context)
                        , OBSession.getPreference(getString(R.string.email_key)
                                , context)
                        , OBSession.getPreference(getString(R.string.phone_key)
                                , context)
                        , OBSession.getPreference(getString(R.string.image_src_key)
                                , context));
                editNameDialogFragment.show(getSupportFragmentManager(), "fragment_edit_name");
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        Glide.with(cm.getContext())
                .load(OfficeBotURI.getFileUrlPreFix() + OBSession
                        .getPreference(getString(R.string.image_src_key)
                                , this))
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter().fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .into(cm);
        findViewById(R.id.friends).setOnClickListener(new OnClickListener() {
            public void onClick(View vi) {
                Intent v = new Intent(ProfileTagActivity.this, FriendListActivity.class);
                ProfileTagActivity.this.startActivity(v);
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        findViewById(R.id.groups).setOnClickListener(new OnClickListener() {
            public void onClick(View vi) {
                Intent v = new Intent(ProfileTagActivity.this, GroupsActivity.class);
                ProfileTagActivity.this.startActivity(v);
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        findViewById(R.id.tags).setOnClickListener(new OnClickListener() {
            public void onClick(View vi) {
                Intent v = new Intent(ProfileTagActivity.this, TagActivity.class);
                ProfileTagActivity.this.startActivity(v);
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
    }

}

