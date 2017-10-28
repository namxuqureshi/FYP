package com.example.n_u.officebotapp.viewsholders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

public class NewMessageHolder {
    //    @Bind(R.id.activity_new_message)
    RelativeLayout activityNewMessage;
    //    @Bind(R.id.edit_text_content_message)
    EditText editTextContentMessage;
    //    @Bind(R.id.text_time_out)
    TextView textTimeOut;
    //    @Bind(R.id.text_attach_file)
    TextView textAttachFile;
    //    @Bind(R.id.btn_audio_source)
//    Button btnAudioSource;
    //    @Bind(R.id.btn_play_audio)
    TextView btnPlayAudio;
    //        @butterknife.Bind (R.id.text_add_permission)
    TextView textAddPermission;
    //    @Bind(R.id.btn_go_button)
    Button btnGoButton;
    //    @Bind(R.id.btn_cancel_button)
    Button btnCancelButton;

    CheckBox timeOutCheck, attachFileCheck, audioCheck, permissionCheck;


    public NewMessageHolder(View view, Context context) {
        activityNewMessage = (RelativeLayout) view.findViewById(R.id.activity_new_message);
        textTimeOut = (TextView) view.findViewById(R.id.text_time_out);
        textAttachFile = (TextView) view.findViewById(R.id.text_attach_file);
//        btnAudioSource = (Button) view.findViewById(R.id.btn_audio_source);
        btnPlayAudio = (TextView) view.findViewById(R.id.btn_play_audio);
        textAddPermission = (TextView) view.findViewById(R.id.text_add_permission);
        btnGoButton = (Button) view.findViewById(R.id.btn_go_button);
        btnCancelButton = (Button) view.findViewById(R.id.btn_cancel_button);
        editTextContentMessage = (EditText) view.findViewById(R.id.edit_text_content_message);
        textAddPermission.setOnClickListener((View.OnClickListener) context);
        timeOutCheck = (CheckBox) view.findViewById(R.id.timeout_check);
        attachFileCheck = (CheckBox) view.findViewById(R.id.attach_file_check);
        audioCheck = (CheckBox) view.findViewById(R.id.audio_check);
        permissionCheck = (CheckBox) view.findViewById(R.id.permission_check);
    }

    public CheckBox getTimeOutCheck() {
        return timeOutCheck;
    }

    public CheckBox getAttachFileCheck() {
        return attachFileCheck;
    }

    public CheckBox getAudioCheck() {
        return audioCheck;
    }

    public CheckBox getPermissionCheck() {
        return permissionCheck;
    }

    public TextView getBtnPlayAudio() {
        return btnPlayAudio;
    }

    public Button getBtnCancelButton() {
        return btnCancelButton;
    }

    public TextView getTextTimeOut() {
        return textTimeOut;
    }

    public TextView getTextAddPermission() {
        return textAddPermission;
    }

    public TextView getTextAttachFile() {
        return textAttachFile;
    }

    public RelativeLayout getActivityNewMessage() {
        return activityNewMessage;
    }

    public Button getBtnGoButton() {
        return btnGoButton;
    }

//    public Button getBtnAudioSource() {
//        return btnAudioSource;
//    }

    public EditText getEditTextContentMessage() {
        return editTextContentMessage;
    }

}
