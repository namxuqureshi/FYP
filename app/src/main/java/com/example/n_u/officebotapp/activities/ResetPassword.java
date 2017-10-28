package com.example.n_u.officebotapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.appnirman.vaidationutils.ValidationUtils;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.R.id.input_password_forget;
import static com.example.n_u.officebotapp.R.id.input_password_forget_again;
import static com.example.n_u.officebotapp.R.id.text_input_label_password_forget;
import static com.example.n_u.officebotapp.R.id.text_input_label_password_forget_again;
import static com.example.n_u.officebotapp.R.id.text_input_label_password_old;
import static com.example.n_u.officebotapp.activities.ProfileSettingActivity.EXTRA_NAME;

public class ResetPassword
        extends AppCompatActivity {
    private String email = null;
    private TextInputEditText newPass = null;
    private TextInputLayout passNewLayout = null;
    private TextInputLayout passReEnterLayout = null, oldPassLabel;
    private TextInputEditText rePass = null, oldPass = null;
    private ValidationUtils validationUtils = null;

    private boolean isValidData() {
        if (!validationUtils.isValidConfirmPasswrod(rePass.getText().toString(),
                newPass.getText().toString())) {
            passReEnterLayout.setErrorEnabled(true);
            passReEnterLayout.setError(getString(R.string.confirm_pass_error));
            AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
            return false;
        }
        if (!validationUtils.isValidPassword(newPass.getText().toString())) {
            passNewLayout.setErrorEnabled(true);
            passNewLayout.setError(getString(R.string.password_error));
            AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
            return false;
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            View localView = getCurrentFocus();
            if ((localView instanceof EditText)) {
                Rect localRect = new Rect();
                localView.getGlobalVisibleRect(localRect);
                if (!localRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    localView.clearFocus();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(localView.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        validationUtils = new ValidationUtils(this);
        email = getIntent().getStringExtra(getString(R.string.email_key));
        newPass = ((TextInputEditText) findViewById(input_password_forget));
        oldPass = ((TextInputEditText) findViewById(R.id.input_password_old));
        oldPassLabel = ((TextInputLayout) findViewById(text_input_label_password_old));
        rePass = ((TextInputEditText) findViewById(input_password_forget_again));
        passNewLayout = ((TextInputLayout) findViewById(text_input_label_password_forget));
        passReEnterLayout = ((TextInputLayout) findViewById(text_input_label_password_forget_again));
        newPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                passNewLayout.setErrorEnabled(false);
            }

            public void beforeTextChanged(CharSequence s
                    , int start
                    , int count
                    , int after) {
            }

            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }
        });
        rePass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramAnonymousEditable) {
                passReEnterLayout.setErrorEnabled(false);
            }

            public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }

            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }
        });
        oldPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramAnonymousEditable) {
                oldPassLabel.setErrorEnabled(false);
            }

            public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }

            public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            }
        });
        if (getIntent().getStringExtra(EXTRA_NAME) != null)
            if (getIntent().getStringExtra(EXTRA_NAME).equals(EXTRA_NAME)) {
                oldPassLabel.setVisibility(View.VISIBLE);
            }

    }

    public void sendNewPassword(View view) {
        if (OBSession.hasPreference(getString(R.string.pass_user_enc), getApplicationContext())) {
            if (OBSession.getPreference(getString(R.string.pass_user_enc), getApplicationContext())
                    .equalsIgnoreCase(oldPass.getText().toString())) {
                setPass(view);
            } else {
                oldPassLabel.setErrorEnabled(true);
                oldPassLabel.setError(getString(R.string.wrong_prevous_pass));
                AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
            }
        } else {
            setPass(view);
        }
        AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
    }

    private void setPass(View view) {
        if (InternetConnection.checkConnection(getApplicationContext()))
            if ((!newPass.getText().toString().isEmpty())
                    && (!rePass.getText().toString().isEmpty())) {
                if (isValidData()) {
                    IUsers request = OfficeBotURI.retrofit().create(IUsers.class);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(getString(R.string.email_key), email);
                    if (getIntent().getStringExtra(EXTRA_NAME) != null)
                        if (getIntent().getStringExtra(EXTRA_NAME).equals(EXTRA_NAME)) {
                            map.put("oldpassword", oldPass.getText().toString());
                        }
                    map.put(getString(R.string.password_key), newPass.getText().toString());
                    Progress.Show(this);
                    retrofit2.Call<Status> call = request.resetPasswordRequest(map);
                    call.enqueue(new Callback<Status>() {
                        public void onFailure(Call<Status> call, Throwable throwable) {
                            Toast.makeText(ResetPassword.this, throwable.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            AppLog.logString(throwable.getMessage());
                            Progress.Cancel();
                        }

                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.code() == 200) {
                                if (!Boolean.parseBoolean(response.body().getError())) {
                                    Progress.Cancel();
                                    Toast.makeText(ResetPassword.this, R.string.pass_changed, Toast.LENGTH_LONG).show();
                                    Intent in;
                                    if (getIntent().getStringExtra(EXTRA_NAME) != null) {
                                        if (getIntent().getStringExtra(EXTRA_NAME).equals(EXTRA_NAME))
                                            Toast.makeText(ResetPassword.this, R.string.pass_changed, Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        in = new Intent(ResetPassword.this, LoginActivity.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }

                            } else {
                                Intent in = new Intent(ResetPassword.this, LoginActivity.class);
                                startActivity(in);
                                finish();
                                Toast.makeText(ResetPassword.this, "Server Offline" + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            } else
                Snackbar.make(view, R.string.fields_empty, Snackbar.LENGTH_SHORT).show();
        else Snackbar.make(view, AppLog.NET_NOT_AVAILABLE, Snackbar.LENGTH_SHORT).show();
    }
}
