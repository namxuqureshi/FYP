package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Switch;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfileSettingHolder {
    @Bind (R.id.profile_img)
    CircleImageView profileImg;
    @Bind (R.id.pic_set_float_btn)
    FloatingActionButton picSetFloatBtn;
    @Bind (R.id.nm_label)
    TextInputLayout nmLabel;
    @Bind (R.id.profile_name)
    TextInputEditText profileName;
    @Bind (R.id.ph_label)
    TextInputLayout phLabel;
    @Bind (R.id.profile_phone)
    TextInputEditText profilePhone;
    @Bind (R.id.cp_label)
    TextInputLayout cpLabel;
    @Bind (R.id.profile_new_password)
    TextInputEditText profileNewPassword;
    @Bind (R.id.profile_new_email)
    TextInputEditText profileNewEmail;
    @Bind (R.id.em_label)
    TextInputLayout emLabel;
    @butterknife.Bind (R.id.vibrate_switch)
    Switch vibFeed;

    public ActivityProfileSettingHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public android.widget.Switch getVibFeed() {
        return vibFeed;
    }

    public TextInputLayout getPhLabel() {
        return phLabel;
    }

    public TextInputEditText getProfileNewPassword() {
        return profileNewPassword;
    }

    public CircleImageView getProfileImg() {
        return profileImg;
    }

    public TextInputEditText getProfilePhone() {
        return profilePhone;
    }

    public FloatingActionButton getPicSetFloatBtn() {
        return picSetFloatBtn;
    }

    public TextInputLayout getCpLabel() {
        return cpLabel;
    }

    public TextInputLayout getNmLabel() {
        return nmLabel;
    }

    public TextInputEditText getProfileName() {
        return profileName;
    }

    public TextInputEditText getProfileNewEmail() {
        return profileNewEmail;
    }

    public TextInputLayout getEmLabel() {
        return emLabel;
    }
}

