package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityForgetPasswordHolder {
    @Bind (R.id.activity_forget_password)
    ScrollView activityForgetPassword;
    @Bind (R.id.inner_layout_for)
    RelativeLayout innerLayoutFor;
    @Bind (R.id.forget_pass)
    TextView forgetPass;
    @Bind (R.id.text_input_label_email_forget)
    TextInputLayout textInputLabelEmailForget;
    @Bind (R.id.input_email_forget)
    TextInputEditText inputEmailForget;
    @Bind (R.id.btn_forget_pass_send)
    Button btnForgetPassSend;
    @Bind (R.id.btn_login)
    Button btnLogin;

    public ActivityForgetPasswordHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextInputLayout getTextInputLabelEmailForget() {
        return textInputLabelEmailForget;
    }

    public Button getBtnForgetPassSend() {
        return btnForgetPassSend;
    }

    public TextView getForgetPass() {
        return forgetPass;
    }

    public ScrollView getActivityForgetPassword() {
        return activityForgetPassword;
    }

    public RelativeLayout getInnerLayoutFor() {
        return innerLayoutFor;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public TextInputEditText getInputEmailForget() {
        return inputEmailForget;
    }
}
