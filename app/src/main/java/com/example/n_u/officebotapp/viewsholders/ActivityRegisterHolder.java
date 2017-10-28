package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityRegisterHolder {
    @Bind (R.id.layout_input_reg_name)
    TextInputLayout layoutInputRegName;
    @Bind (R.id.input_reg_name)
    TextInputEditText inputRegName;
    @Bind (R.id.layout_input_reg_name_last)
    TextInputLayout layoutInputRegNameLast;
    @Bind (R.id.input_reg_name_last)
    TextInputEditText inputRegNameLast;
    @Bind (R.id.layout_input_reg_email)
    TextInputLayout layoutInputRegEmail;
    @Bind (R.id.input_reg_email)
    TextInputEditText inputRegEmail;
    @Bind (R.id.layout_input_reg_password)
    TextInputLayout layoutInputRegPassword;
    @Bind (R.id.input_reg_password)
    TextInputEditText inputRegPassword;
    @Bind (R.id.btn_register)
    Button btnRegister;
    @Bind (R.id.link_sign_up)
    Button linkSignUp;

    public ActivityRegisterHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextInputEditText getInputRegName() {
        return inputRegName;
    }

    public TextInputEditText getInputRegPassword() {
        return inputRegPassword;
    }

    public Button getBtnRegister() {
        return btnRegister;
    }

    public TextInputLayout getLayoutInputRegPassword() {
        return layoutInputRegPassword;
    }

    public Button getLinkSignUp() {
        return linkSignUp;
    }

    public TextInputLayout getLayoutInputRegEmail() {
        return layoutInputRegEmail;
    }

    public TextInputEditText getInputRegEmail() {
        return inputRegEmail;
    }

    public TextInputLayout getLayoutInputRegName() {
        return layoutInputRegName;
    }

    public TextInputLayout getLayoutInputRegNameLast() {
        return layoutInputRegNameLast;
    }

    public TextInputEditText getInputRegNameLast() {
        return inputRegNameLast;
    }
}
