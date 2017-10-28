package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityNewMessageHolder {
    @Bind (R.id.activity_new_message)
    RelativeLayout activityNewMessage;
    @Bind (R.id.edit_text_content_message)
    EditText editTextContentMessage;
    @Bind (R.id.text_time_out)
    TextView textTimeOut;
    @Bind (R.id.timeout_check)
    AppCompatCheckBox timeoutCheck;
    @Bind (R.id.text_attach_file)
    TextView textAttachFile;
    @Bind (R.id.attach_file_check)
    AppCompatCheckBox attachFileCheck;
    @Bind (R.id.btn_audio_source)
    Button btnAudioSource;
    @Bind (R.id.btn_play_audio)
    TextView btnPlayAudio;
    @Bind (R.id.audio_check)
    AppCompatCheckBox audioCheck;
    @Bind (R.id.text_add_permission)
    TextView textAddPermission;
    @Bind (R.id.permission_check)
    AppCompatCheckBox permissionCheck;
    @Bind (R.id.btn_go_button)
    Button btnGoButton;
    @Bind (R.id.btn_cancel_button)
    Button btnCancelButton;

    public ActivityNewMessageHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getBtnPlayAudio() {
        return btnPlayAudio;
    }

    public Button getBtnAudioSource() {
        return btnAudioSource;
    }

    public AppCompatCheckBox getTimeoutCheck() {
        return timeoutCheck;
    }

    public AppCompatCheckBox getAudioCheck() {
        return audioCheck;
    }

    public Button getBtnCancelButton() {
        return btnCancelButton;
    }

    public RelativeLayout getActivityNewMessage() {
        return activityNewMessage;
    }

    public TextView getTextTimeOut() {
        return textTimeOut;
    }

    public AppCompatCheckBox getPermissionCheck() {
        return permissionCheck;
    }

    public EditText getEditTextContentMessage() {
        return editTextContentMessage;
    }

    public TextView getTextAttachFile() {
        return textAttachFile;
    }

    public AppCompatCheckBox getAttachFileCheck() {
        return attachFileCheck;
    }

    public Button getBtnGoButton() {
        return btnGoButton;
    }

    public TextView getTextAddPermission() {
        return textAddPermission;
    }
}
