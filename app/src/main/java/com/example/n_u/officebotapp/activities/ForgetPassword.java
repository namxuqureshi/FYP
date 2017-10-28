package com.example.n_u.officebotapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.appnirman.vaidationutils.ValidationUtils;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword
        extends AppCompatActivity {
    private Activity context = this;
    private TextInputEditText email = null;
    private TextInputLayout emailLayout = null;
    private String emailTemp = null;
    private PinEntryEditText pinEntry = null;
    private ValidationUtils validationUtils = null;

    private boolean isValidData() {
//        boolean bool2 = true;
//        boolean bool1 = bool2;
//        if (!Pattern.compile("[a-zA-Z0-9\\_\\.]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+").matcher(email.getText().toString()).matches()) {
//            bool1 = bool2;
//            if (!validationUtils.isValidEmail(email.getText().toString())) {
//                emailLayout.setErrorEnabled(true);
//                emailLayout.setError(getString(R.string.email_error));
//                AppLog.setVibrate(context, AppLog.INTENSITY_HIGH);
//                bool1 = false;
//            }
//        }
//        return bool1;
        if (!validationUtils.isValidEmail(email.getText().toString())) {
            if ((!java.util.regex.Pattern.compile("[a-zA-Z0-9\\_\\.]" +
                    "{7,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]" +
                    "{3,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]" +
                    "{3,25})+").
                    matcher(email.getText().toString()).matches())
                    && (!validationUtils.isValidEmail(email.getText().toString()))) {
                emailLayout.setErrorEnabled(true);
                emailLayout.setError(getString(R.string.email_error));
                AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
                return false;
            }
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0) {
            View localView = getCurrentFocus();
            if ((localView instanceof EditText)) {
                Rect localRect = new Rect();
                localView.getGlobalVisibleRect(localRect);
                if (!localRect.contains((int) paramMotionEvent.getRawX(), (int) paramMotionEvent.getRawY())) {
                    localView.clearFocus();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(localView.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(paramMotionEvent);
    }

    public void loginActivity(View paramView) {
        startActivity(new Intent(context, LoginActivity.class));
        AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(context, LoginActivity.class));
        AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_forget_password);
        validationUtils = new ValidationUtils(this);
        email = ((TextInputEditText) findViewById(R.id.input_email_forget));
        emailLayout = ((TextInputLayout) findViewById(R.id.text_input_label_email_forget));
        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                emailLayout.setErrorEnabled(false);
            }

            public void beforeTextChanged(CharSequence s
                    , int start
                    , int count
                    , int after) {
            }

            public void onTextChanged(CharSequence s
                    , int start
                    , int before
                    , int count) {
            }
        });
    }

    public void sendEmailForPassCode(final View view) {
        if ((email.getText().toString().isEmpty()) && !isValidData() &&
                (InternetConnection.checkConnection(getApplicationContext()))) {
            if (!isValidData())
                Snackbar.make(view, "Invalid Input", Snackbar.LENGTH_LONG).show();
            else if (InternetConnection.checkConnection(getApplicationContext()))
                Snackbar.make(view, R.string.field_empty_email, Snackbar.LENGTH_LONG).show();
            else Snackbar.make(view, AppLog.NET_NOT_AVAILABLE, Snackbar.LENGTH_LONG).show();
            AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
        } else {
            if (isValidData()) {
                emailTemp = email.getText().toString();
                final IUsers userRequest = OfficeBotURI.retrofit().create(IUsers.class);
                final HashMap<String, Object> map = new HashMap<>();
                map.put(getString(R.string.email_key), emailTemp);
                Progress.Show(context, "Authenticating..");
                final retrofit2.Call<Status> call = userRequest.forgetPasswordRequestCode(map);
                call.enqueue(new retrofit2.Callback<Status>() {
                    private PinEntryEditText pinEntryEditText;

                    @Override
                    public void onFailure(Call<Status> call,
                                          Throwable t) {
                        AppLog.toastString(t.getMessage(), context);
                        AppLog.logString(t.getMessage());
                        Progress.Cancel();
                    }

                    @Override
                    public void onResponse(Call<Status> call,
                                           final Response<Status> response) {
                        Progress.Cancel();
                        if (response.code() == 200) {
                            boolean bool = Boolean.parseBoolean(response.body().getError());
                            AppLog.logString("onResponse: " + bool);
                            if (bool) {
//                                AppLog.toastString(getString(R.string.valid_email), context);
                                Snackbar.make(view, "Email is not Registered", Snackbar.LENGTH_LONG).show();
                                Progress.Cancel();
                                return;
                            }
                            Progress.Cancel();
                            final Builder alert = new Builder(context);
                            final View v = getLayoutInflater().inflate(R.layout.dialog_code_authenticate, null);
                            pinEntryEditText = (PinEntryEditText) v.findViewById(R.id.txt_pin_entry);
                            alert.setView(v).setPositiveButton(R.string.send_forget_email, new OnClickListener() {
                                public void onClick(final DialogInterface dialog,
                                                    int tag) {
                                    AppLog.logString("onClick: " + pinEntryEditText.getText().toString());
                                    if (!pinEntryEditText.getText().toString().isEmpty()) {
                                        Progress.Show(context, "Authenticating..");
                                        final IUsers userRequest
                                                = OfficeBotURI.retrofit().create(IUsers.class);
                                        map.put(getString(R.string.email_key), emailTemp);
                                        map.put(getString(R.string.token_key), pinEntryEditText.getText().toString());
                                        final retrofit2.Call<Status> call
                                                = userRequest.forgetPasswordSendRequestCode(map);
                                        call.enqueue(new Callback<Status>() {
                                            @Override
                                            public void onResponse(Call<Status> call,
                                                                   Response<Status> response) {
                                                boolean bool = Boolean.parseBoolean(response.body().getError());
                                                if (bool) {
                                                    AppLog.toastString("Wrong Pin", context);
                                                    Progress.Cancel();
                                                    finish();
                                                } else {
                                                    Progress.Cancel();
                                                    Intent i = new Intent(ForgetPassword.this,
                                                            ResetPassword.class);
                                                    i.putExtra(getString(R.string.email_key), emailTemp);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Status> call, Throwable t) {
                                                AppLog.logString(t.getMessage());
                                                AppLog.toastString(t.getMessage(), context);
                                                Progress.Cancel();
                                            }
                                        });

                                    } else {
                                        AppLog.toastString(getString(R.string.field_empty), context);
                                    }

                                }
                            }).setNegativeButton(R.string.no, new OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.cancel();
                                    loginActivity(v);
                                }
                            });
                            alert.setTitle(getString(R.string.enter_here));
                            alert.setMessage(getString(R.string.pincode));
                            alert.create().show();
                        } else
                            Snackbar.make(view, AppLog.SERVER_LOG + response.code(), Snackbar.LENGTH_SHORT).show();
                    }
                });
                AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
            } else
                Snackbar.make(view, "Invalid Input", Snackbar.LENGTH_LONG).show();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}