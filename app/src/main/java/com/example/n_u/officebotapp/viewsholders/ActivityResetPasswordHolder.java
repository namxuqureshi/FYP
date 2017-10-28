package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityResetPasswordHolder {
    @Bind (R.id.activity_reset_password)
    ScrollView activityResetPassword;
    @Bind (R.id.inner_layout_for)
    RelativeLayout innerLayoutFor;
    @Bind (R.id.reset_pass)
    TextView resetPass;
    @Bind (R.id.layout_pass)
    LinearLayout layoutPass;
    @Bind (R.id.text_input_label_password_old)
    TextInputLayout textInputLabelPasswordOld;
    @Bind (R.id.input_password_old)
    TextInputEditText inputPasswordOld;
    @Bind (R.id.text_input_label_password_forget)
    TextInputLayout textInputLabelPasswordForget;
    @Bind (R.id.input_password_forget)
    TextInputEditText inputPasswordForget;
    @Bind (R.id.text_input_label_password_forget_again)
    TextInputLayout textInputLabelPasswordForgetAgain;
    @Bind (R.id.input_password_forget_again)
    TextInputEditText inputPasswordForgetAgain;
    @Bind (R.id.btn_forget_pass_reset)
    Button btnForgetPassReset;

    public ActivityResetPasswordHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public RelativeLayout getInnerLayoutFor() {
        return innerLayoutFor;
    }

    public TextInputLayout getTextInputLabelPasswordForget() {
        return textInputLabelPasswordForget;
    }

    public TextInputEditText getInputPasswordForget() {
        return inputPasswordForget;
    }

    public LinearLayout getLayoutPass() {
        return layoutPass;
    }

    public ScrollView getActivityResetPassword() {
        return activityResetPassword;
    }

    public TextInputLayout getTextInputLabelPasswordOld() {
        return textInputLabelPasswordOld;
    }

    public TextInputLayout getTextInputLabelPasswordForgetAgain() {
        return textInputLabelPasswordForgetAgain;
    }

    public TextInputEditText getInputPasswordOld() {
        return inputPasswordOld;
    }

    public Button getBtnForgetPassReset() {
        return btnForgetPassReset;
    }

    public TextView getResetPass() {
        return resetPass;
    }

    public TextInputEditText getInputPasswordForgetAgain() {
        return inputPasswordForgetAgain;
    }
}
