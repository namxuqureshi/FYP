package com.example.n_u.officebotapp.viewsholders;

import android.support.constraint.Guideline;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import at.markushi.ui.CircleButton;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityLoginHolder {
    @Bind (R.id.inner_layout_scroll)
    ScrollView innerLayoutScroll;
    @Bind (R.id.inner_layout)
    LinearLayout innerLayout;
    @Bind (R.id.text_input_label_email)
    TextInputLayout textInputLabelEmail;
    @Bind (R.id.input_email)
    TextInputEditText inputEmail;
    @Bind (R.id.text_input_label_password)
    TextInputLayout textInputLabelPassword;
    @Bind (R.id.input_password)
    TextInputEditText inputPassword;
    @Bind (R.id.btn_login)
    Button btnLogin;
    @Bind (R.id.fb_login_button)
    LoginButton fbLoginButton;
    @Bind (R.id.fb)
    CircleButton fb;
    @Bind (R.id.google_sign_in_button)
    SignInButton googleSignInButton;
    @Bind (R.id.google)
    CircleButton google;
    @Bind (R.id.link_forget_pass)
    TextView linkForgetPass;
    @Bind (R.id.guideline)
    Guideline guideline;
    @Bind (R.id.guideline2)
    Guideline guideline2;

    public ActivityLoginHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextInputEditText getInputPassword() {
        return inputPassword;
    }

    public TextView getLinkForgetPass() {
        return linkForgetPass;
    }

    public TextInputLayout getTextInputLabelEmail() {
        return textInputLabelEmail;
    }

    public Guideline getGuideline() {
        return guideline;
    }

    public LoginButton getFbLoginButton() {
        return fbLoginButton;
    }

    public TextInputEditText getInputEmail() {
        return inputEmail;
    }

    public LinearLayout getInnerLayout() {
        return innerLayout;
    }

    public Guideline getGuideline2() {
        return guideline2;
    }

    public ScrollView getInnerLayoutScroll() {
        return innerLayoutScroll;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public CircleButton getFb() {
        return fb;
    }

    public TextInputLayout getTextInputLabelPassword() {
        return textInputLabelPassword;
    }

    public CircleButton getGoogle() {
        return google;
    }

    public SignInButton getGoogleSignInButton() {
        return googleSignInButton;
    }
}
