package com.example.n_u.officebotapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.appnirman.vaidationutils.ValidationUtils;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Login;
import com.example.n_u.officebotapp.services.TokenService;
import com.example.n_u.officebotapp.services.UserListService;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.utils.OkHttp;
import com.example.n_u.officebotapp.viewsholders.ActivityLoginHolder;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity
        extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    private final List<String> permissions = Arrays.asList("email", "user_birthday", "user_friends");
    private final Context context = this;
    private ActivityLoginHolder holder;
    private GoogleApiClient googleApiClient = null;
    private CallbackManager mCallbackManager = null;
    private JSONObject objJson = null;
    private ValidationUtils validationUtils = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        @android.annotation.SuppressLint ("InflateParams") View v = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        validationUtils = new ValidationUtils(this);
        holder = new ActivityLoginHolder(v);
        setContentView(v);
        setUi();
        setListener();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setUi() {
        mCallbackManager = CallbackManager.Factory.create();
        holder.getInputEmail().addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramAnonymousEditable) {
                holder.getTextInputLabelEmail().setErrorEnabled(false);
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
        holder.getInputPassword().addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                holder.getTextInputLabelPassword().setErrorEnabled(false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int watcher) {
            }
        });
        holder.getFbLoginButton().setReadPermissions(permissions);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setListener() {
        holder.getFbLoginButton().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    public void onCancel() {
                        AppLog.toastString("User: Cancel ", getApplicationContext());
                        AppLog.logString("facebook:onCancel");
                    }

                    public void onError(FacebookException e) {
                        AppLog.toastString("User: Error " + e.getMessage()
                                , getApplicationContext());
                        AppLog.logString("facebook:onError" + e.getMessage());
                    }

                    public void onSuccess(LoginResult loginResult) {
                        Progress.Show(LoginActivity.this, getString(R.string.signing));
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                        AppLog.logString(jsonObject.toString());
                                        objJson = jsonObject;
                                        if (InternetConnection.checkConnection(getApplicationContext())) {
                                            loginCredential();
                                        } else
                                            AppLog.toastString(AppLog.INTERNET_NOT_AVAILABLE,
                                                    getApplicationContext());
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name, email, gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                });
        holder.getGoogleSignInButton().setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    signIn();
                } else
                    AppLog.toastString(AppLog.INTERNET_NOT_AVAILABLE, getApplicationContext());
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        holder.getFb().setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    holder.getFbLoginButton().performClick();
                } else
                    AppLog.toastString(AppLog.INTERNET_NOT_AVAILABLE, getApplicationContext());
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        holder.getGoogle().setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    signIn();
                } else
                    AppLog.toastString(AppLog.INTERNET_NOT_AVAILABLE, getApplicationContext());
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result)
            throws JSONException {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (objJson == null) {
                Progress.Show(context, getString(R.string.signing));
                objJson = new JSONObject();
                AppLog.logString(acct != null ? acct.getDisplayName() : null);
                AppLog.logString(acct != null ? acct.toString() : null);
                objJson.put(getString(R.string.name_key), acct != null ? acct.getDisplayName() : null);
                objJson.put(getString(R.string.id_key), acct != null ? acct.getId() : null);
                objJson.put(getString(R.string.email_key), acct != null ? acct.getEmail() : null);
                loginCredential();
            } else {
                Progress.Show(context, getString(R.string.signing));
                loginCredential();
            }
        } else AppLog.logString(result.getStatus().toString());
    }

    private boolean isValidData() {
        if ((!Pattern.compile("[a-zA-Z0-9\\_\\.]" +
                "{10,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]" +
                "{3,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]" +
                "{3,25})+").
                matcher(holder.getInputEmail().getText().toString()).matches())
                && (!validationUtils.isValidEmail(holder.getInputEmail().getText().toString()))) {
            holder.getTextInputLabelEmail().setErrorEnabled(true);
            holder.getTextInputLabelEmail().setError(getString(R.string.email_error));
            return false;
        }
        if (!validationUtils.isValidPassword(holder.getInputPassword().getText().toString())) {
            holder.getTextInputLabelPassword().setErrorEnabled(true);
            holder.getTextInputLabelPassword().setError(getString(R.string.password_error));
            return false;
        }
        return true;
    }

    private void loginCredential() {
        OBSession.putPreference(getString(R.string.flag_other), "Not Has Pass", context);
        AppLog.logString(objJson.toString());
        OkHttp.post(OfficeBotURI.getFacebookLogin()
                , objJson.toString()
                , new okhttp3.Callback() {
                    public void onFailure(okhttp3.Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Progress.Cancel();
                                AppLog.toastString(AppLog.INTERNET_NOT_AVAILABLE, getApplicationContext());
                                if (getGoogleApiClient() != null) {
                                    Auth.GoogleSignInApi.signOut(getGoogleApiClient());
                                }
                                FacebookSdk.sdkInitialize(getApplicationContext());
                                LoginManager.getInstance().logOut();
                            }
                        });
                    }

                    public void onResponse(okhttp3.Call call, final okhttp3.Response response)
                            throws IOException {
                        if (response.code() == 200) {
                            final String responseData = response.body().string();
                            response.body().close();
                            try {
                                JSONObject jsonObject1 = new JSONObject(responseData).getJSONObject(getString(R.string.user_data_key));
                                OBSession.putPreference(getString(R.string.user_data_key),
                                        responseData,
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.id_key),
                                        jsonObject1.getString("id"),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.name_key),
                                        jsonObject1.getString("name"),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.email_key),
                                        jsonObject1.getString("email"),
                                        getApplicationContext());
                                if (jsonObject1.has("phone")) {
                                    OBSession.putPreference(getString(R.string.phone_key),
                                            jsonObject1.getString("phone"),
                                            getApplicationContext());
                                } else {
                                    OBSession.putPreference(getString(R.string.phone_key),
                                            "Phone# Not Specified.",
                                            getApplicationContext());
                                }
                                if (jsonObject1.has("image_src")) {
                                    OBSession.putPreference(getString(R.string.image_src_key),
                                            jsonObject1.getString("image_src"),
                                            getApplicationContext());
                                }

                                try {
                                    if (getGoogleApiClient() != null) {
                                        Auth.GoogleSignInApi.signOut(getGoogleApiClient());
                                    }
                                } catch (IllegalStateException e) {
                                    AppLog.logString(e.getMessage());
                                    AppLog.toastShortString(e.getMessage(), getApplicationContext());
                                }
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Progress.Cancel();
                                        try {
                                            Intent intent = new Intent(LoginActivity.this, ProfileTagActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startService(new Intent(LoginActivity.this
                                                    , UserListService.class));
                                            startService(new Intent(LoginActivity.this
                                                    , TokenService.class));
                                            startActivity(intent);
                                            finish();
                                        } catch (IllegalStateException e) {
                                            AppLog.logString(e.getMessage());
                                            AppLog.toastShortString(e.getMessage(), getApplicationContext());
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                Progress.Cancel();
                                AppLog.logString(e.getMessage());
                            }
                        } else {
                            AppLog.logString("Credentials");
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    FacebookSdk.sdkInitialize(getApplicationContext());
                                    LoginManager.getInstance().logOut();
                                    if (getGoogleApiClient() != null) {
                                        Auth.GoogleSignInApi.signOut(getGoogleApiClient());
                                    }
                                    Snackbar.make(holder.getFbLoginButton(), response.message(), Snackbar.LENGTH_LONG).show();
                                    AppLog.logString(response.toString() + response.code() + response.message());
                                    Progress.Cancel();
                                    AppLog.toastString(AppLog.SERVER_LOG, getApplicationContext());
                                }
                            });

                        }
                    }
                });
    }

    private void signIn() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), AppLog.RC_SIGN_IN);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if ((currentFocus instanceof EditText)) {
                Rect localRect = new Rect();
                currentFocus.getGlobalVisibleRect(localRect);
                if (!localRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    currentFocus.clearFocus();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void getForgetPassword(View view) {
        startActivity(new Intent(this, ForgetPassword.class));
        AppLog.setVibrate(view.getContext(), AppLog.INTENSITY_MIDDLE);
        finish();
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void getSignUp(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        AppLog.setVibrate(view.getContext(), AppLog.INTENSITY_MIDDLE);
    }

    public void login(final View view) {
        if ((!holder.getInputEmail().getText().toString().isEmpty())
                && (!holder.getInputPassword().getText().toString().isEmpty())) {
            if (isValidData()) {
                IUsers iUsers = OfficeBotURI.retrofit().create(IUsers.class);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(getString(R.string.email_key)
                        , holder.getInputEmail().getText().toString());
                hashMap.put(getString(R.string.password_key)
                        , holder.getInputPassword().getText().toString());
                Progress.Show(this, getString(R.string.signing));
                final retrofit2.Call<Login> call = iUsers.loginUser(hashMap);
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        AppLog.logString(String.valueOf(response.code()) + response.body());
                        if (response.code() == 200) {
                            if (((Login) response.body()).isLogin()) {
                                AppLog.toastString(getString(R.string.log_success), getApplicationContext());
                                OBSession.putPreference(getString(R.string.user_data_key),
                                        ((Login) response.body()).getUser().toString(),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.id_key),
                                        ((Login) response.body()).getUser().getUserId().toString(),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.name_key),
                                        ((Login) response.body()).getUser().getName(),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.email_key),
                                        ((Login) response.body()).getUser().getEmail(),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.phone_key),
                                        ((Login) response.body()).getUser().getPhone(),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.image_src_key),
                                        ((Login) response.body()).getUser().getImage_src(),
                                        getApplicationContext());
                                OBSession.putPreference(getString(R.string.pass_user_enc),
                                        holder.getInputPassword().getText().toString(),
                                        getApplicationContext());

                                Intent i = new Intent(LoginActivity.this, ProfileTagActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startService(new Intent(LoginActivity.this
                                        , TokenService.class));
                                startService(new Intent(LoginActivity.this
                                        , UserListService.class));
                                startActivity(i);
                                Progress.Cancel();
                                finish();
                                return;
                            }
                            AppLog.toastString(getString(R.string.incorrect_credential), getApplicationContext());
                            Progress.Cancel();
                            return;
                        }
                        Snackbar.make(view, AppLog.SERVER_LOG + response.code(), Snackbar.LENGTH_SHORT).show();
                        Progress.Cancel();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        AppLog.toastString(AppLog.INTERNET_NOT_AVAILABLE + t.getMessage(), getApplicationContext());
                        Progress.Cancel();
                    }
                });
            } else
                Snackbar.make(view, R.string.pass_or_email_valid, Snackbar.LENGTH_SHORT).show();
        } else
            Snackbar.make(view, R.string.pass_email_empty, Snackbar.LENGTH_SHORT).show();
        AppLog.setVibrate(context, AppLog.INTENSITY_HIGH);
    }

    protected void onActivityResult(int requestCode
            , int resultCode
            , Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppLog.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                handleSignInResult(result);
            } catch (JSONException | IllegalStateException e) {
                AppLog.toastString(e.getMessage(), getApplicationContext());
                AppLog.logString(e.getMessage());
            }
        } else
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        AppLog.toastShortString(connectionResult.getErrorMessage(), getApplicationContext());
        AppLog.logString("onConnectionFailed:" + connectionResult);
    }

}


