package com.example.n_u.officebotapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.azimolabs.maskformatter.MaskFormatter;
import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.viewsholders.ActivityProfileSettingHolder;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.utils.OBSession.getPreference;
import static com.example.n_u.officebotapp.utils.OBSession.hasPreference;
import static com.example.n_u.officebotapp.utils.OBSession.putPreference;
import static java.lang.Integer.parseInt;

public class ProfileSettingActivity
        extends NavigationActivity
        implements OnClickListener, IPickResult {
    public static final String EXTRA_NAME = "profile";
    private static final String PHONE_MASK = "03999999999";
    private static final String NAME_MASK = "1234567890`~!@#$%^&*()_+=_-{[}}|?;:<,>.\\/'\"";
    private ActivityProfileSettingHolder holder;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_nav_setting, null);
        holder = new ActivityProfileSettingHolder(v);
        setContentView(v);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUi();
        setListener();
        if (OBSession.hasPreference("OffVibrate", getApplicationContext())) {
            holder.getVibFeed().setChecked(false);
        } else holder.getVibFeed().setChecked(true);
    }

    public void onClick(final View v) {
        AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
        int i = v.getId();
        final IUsers request = OfficeBotURI.retrofit().create(IUsers.class);
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(getString(R.string.edit_text_dialoge));
        final MaterialDialog.Builder mb = new MaterialDialog.Builder(this);
        mb.customView(R.layout.dialogue_box, true);
        mb.title(getString(R.string.edit_text_dialoge));
        mb.negativeText(R.string.cancel_btn);
        mb.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Toast.makeText(context, "Edit Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        mb.positiveText(R.string.okay);
        MaterialDialog md;
        TextInputLayout textLabel;
        MaskFormatter maskFormatter;
        md = mb.build();
        final EditText text = (EditText) md.findViewById(R.id.name);
        textLabel = (TextInputLayout) md.findViewById(R.id.name_label);
        switch (i) {
            case R.id.cp_label:
            case R.id.profile_new_password:
                Intent intent = new Intent(this, ResetPassword.class);
                intent.putExtra(getString(R.string.email_key), getPreference(getString(R.string.email_key), this));
                intent.putExtra(EXTRA_NAME, EXTRA_NAME);
                startActivity(intent);
                break;
            case R.id.ph_label:
            case R.id.profile_phone:
                textLabel.setError("Hint: 03001234567");
                text.setInputType(InputType.TYPE_CLASS_PHONE);
                textLabel.setCounterMaxLength(11);
                maskFormatter = new MaskFormatter(PHONE_MASK, text);
                text.addTextChangedListener(maskFormatter);
                text.setText(holder.getProfilePhone().getText().toString());
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        putPreference(getString(R.string.phone_key), text.getText().toString(), getApplicationContext());
                        AppLog.toastString("Edit Successfully", getApplicationContext());
                        final HashMap<String, Object> map = new HashMap<>();
                        map.put("phone"
                                , text.getText().toString());
                        final String tm = text.getText().toString();
                        Call<Status> call = request.changeUserDetail(map
                                , getPreference("id", context));
                        if (InternetConnection.checkConnection(context)) {
                            Progress.Show(context, "Updating..");
                            call.enqueue(new Callback<Status>() {
                                @Override
                                public void onResponse(Call<Status> call, Response<Status> response) {
//                                AppLog.logString(response.body().toString());
                                    AppLog.logString(response.code());
                                    if (response.code() == 200) {
                                        holder.getProfilePhone().setText(tm);
                                        putPreference(getString(R.string.phone_key)
                                                , tm
                                                , getApplicationContext());
                                    } else {
                                        AppLog.toastShortString(response.message(), getApplicationContext());
                                    }
                                    Progress.Cancel();
                                    onRestart();
                                }

                                @Override
                                public void onFailure(Call<Status> call, Throwable t) {
                                    AppLog.logString(t.getMessage());
                                    AppLog.toastShortString(t.getMessage(), getApplicationContext());
                                    Progress.Cancel();
                                }
                            });
                        } else
                            AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());

                        dialog.dismiss();
                    }
                });
                mb.show();
                break;
            case R.id.nm_label:
            case R.id.profile_name:
                textLabel.setError("Hint: ABC XYZ");
                textLabel.setCounterEnabled(true);
                textLabel.setCounterMaxLength(30);
                text.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 30) {
                            s.delete(s.length() - 1, s.length());
                        }
//                        if ((s.charAt(s.length() - 1) < 65 && s.charAt(s.length() - 1) != 32)
//                                && (s.charAt(s.length() - 1) > 90 && s.charAt(s.length() - 1) < 97)) {
//                            s.delete(s.length() - 1, s.length());
//                        } else AppLog.logString(s.toString());
//                        if ((s.charAt(s.length() - 1) > 122 && s.charAt(s.length() - 1) != 32)
//                                && (s.charAt(s.length() - 1) > 96)) {
//                            s.delete(s.length() - 1, s.length());
//                        } else AppLog.logString(s.toString());
                    }
                });
                text.setText(holder.getProfileName().getText().toString());
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        putPreference(getString(R.string.name_key), text.getText().toString(), getApplicationContext());
                        AppLog.toastString("Edit Successfully", getApplicationContext());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name"
                                , text.getText().toString());
                        Call<Status> call = request.changeUserDetail(map
                                , getPreference("id", context));
                        if (InternetConnection.checkConnection(context)) {
                            Progress.Show(context, "Updating..");
                            call.enqueue(new Callback<Status>() {
                                @Override
                                public void onResponse(Call<Status> call, Response<Status> response) {
                                    if (response.code() == 200) {
                                        putPreference(getString(R.string.name_key)
                                                , text.getText().toString()
                                                , getApplicationContext());
                                        holder.getProfileName().setText(text.getText().toString());
                                    } else {
                                        AppLog.toastShortString(response.message(), getApplicationContext());
                                    }
                                    Progress.Cancel();
                                    onRestart();
                                }

                                @Override
                                public void onFailure(Call<Status> call, Throwable t) {
                                    AppLog.logString(t.getMessage());
                                    AppLog.toastShortString(t.getMessage(), getApplicationContext());
                                    Progress.Cancel();
                                }
                            });
                        } else
                            AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());
                        dialog.dismiss();
                    }
                });
                mb.show();
                break;
            case R.id.pic_set_float_btn:
                if (InternetConnection.checkConnection(context)) {
                    PickImageDialog
                            .build(new PickSetup()
                                    .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                                    .setFlip(true)
                            )
                            .show(this);
                } else AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());
                break;
            case R.id.profile_img:
                ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(OBSession.getPreference(getString(R.string.name_key)
                        , context)
                        , OBSession.getPreference(getString(R.string.email_key)
                                , context)
                        , OBSession.getPreference(getString(R.string.phone_key)
                                , context)
                        , OBSession.getPreference(getString(R.string.image_src_key)
                                , context));
                editNameDialogFragment.show(getSupportFragmentManager(), "fragment_edit_name");
                break;
            default:
                break;
        }

    }

    void setListener() {
        holder.getCpLabel().setOnClickListener(this);
        holder.getProfileImg().setOnClickListener(this);
        holder.getPicSetFloatBtn().setOnClickListener(this);
        holder.getNmLabel().setOnClickListener(this);
        holder.getPhLabel().setOnClickListener(this);
        holder.getProfilePhone().setOnClickListener(this);
        holder.getProfileName().setOnClickListener(this);
        holder.getProfileNewPassword().setOnClickListener(this);
        holder.getVibFeed().setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    OBSession.delPreference("OffVibrate", getApplicationContext());
                } else {
                    OBSession.putPreference("OffVibrate", "OffVib", getApplicationContext());
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    void setUi() {
        nav(getString(R.string.profile_setting));
        holder.getProfileNewPassword().setText(getString(R.string.change_password));
        holder.getProfileName().setText(getPreference(getString(R.string.name_key), this));
        if (holder.getProfileName().getText().length() > 8) {
            holder.getProfileName().setTextSize(2, 20.0F);
        }
        if (getPreference(getString(R.string.phone_key), this).length() > 0) {
            holder.getProfilePhone().setText(getPreference(getString(R.string.phone_key), this));
        } else {
            holder.getProfilePhone().setText(getString(R.string.phone));
        }
        holder.getProfileNewEmail().setText(getPreference(getString(R.string.email_key), this));
        holder.getProfileName().clearFocus();
        holder.getProfileName().setKeyListener(null);
        holder.getProfilePhone().clearFocus();
        holder.getProfilePhone().setKeyListener(null);
        holder.getProfileNewPassword().clearFocus();
        holder.getProfileNewPassword().setKeyListener(null);
        holder.getProfileNewEmail().clearFocus();
        holder.getProfileNewEmail().setKeyListener(null);
        if (hasPreference(getString(R.string.flag_other), context)) {
            holder.getCpLabel().setVisibility(View.GONE);
        } else {
            holder.getCpLabel().setVisibility(View.VISIBLE);
        }
        Glide.with(holder.getProfileImg().getContext())
                .load(OfficeBotURI.getFileUrlPreFix() +
                        getPreference(getString(R.string.image_src_key)
                                , this))
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .skipMemoryCache(true).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .fitCenter().fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .into(holder.getProfileImg());
    }

    @Override
    public void onPickResult(PickResult r) {
        try {
            if (r.getError() == null) {
                //If you want the Uri.
                //Mandatory to refresh image from Uri.
                //getImageView().setImageURI(null);
                AppLog.logString(r.getPath());
                AppLog.logString(r.getBitmap().toString());
                AppLog.logString(r.getUri().toString());
                holder.getProfileImg().setImageURI(r.getUri());
                IUsers request = OfficeBotURI.retrofit().create(IUsers.class);
                File file = new File(r.getPath());
                try {
                    file = new Compressor(this).compressToFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody = RequestBody
                        .create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part b = MultipartBody.Part.createFormData(getString(R.string.image_key)
                        , file.getName()
                        , requestBody);
                Call<Status> call = request.setUserImage(b, getPreference(getString(R.string.id_key), context));
                if (!call.isExecuted()) {
                    Progress.Show(context, "Updating..");
                    call.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            AppLog.logString(response.toString());
                            if (response.code() == 200) {
                                putPreference(getString(R.string.image_src_key)
                                        , response.body().getMessage()
                                        , getApplicationContext());
                                SearchUser user = SearchUser.getUserOther(parseInt(getPreference(getString(R.string.id_key)
                                        , context)));
                                if (user != null) {
                                    user.setImage_src(response.body().getMessage());
                                    user.save();
                                }
                            } else {
                                AppLog.toastShortString(response.message(), getApplicationContext());
                            }
                            Progress.Cancel();
                            onRestart();
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            AppLog.logString(t.getMessage());
                            AppLog.toastShortString(t.getMessage(), getApplicationContext());
                            Progress.Cancel();
                        }
                    });
                } else
                    AppLog.toastShortString("Already Uploading Please Wait!"
                            , getApplicationContext());

                //Setting the real returned image.
                //getImageView().setImageURI(r.getUri());

                //If you want the Bitmap.
//            getImageView().setImageBitmap(r.getBitmap());

                //Image path
                //r.getPath();
            } else {
                //Handle possible errors
                Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (IOError e) {
            AppLog.logString(e.getMessage());
        }
    }
}
