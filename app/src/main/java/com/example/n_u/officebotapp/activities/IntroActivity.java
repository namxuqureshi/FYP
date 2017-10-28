package com.example.n_u.officebotapp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.FragmentIntroContent;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity
        extends AppIntro {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFlowAnimation();
        setBarColor(getResources().getColor(R.color.colorAccent));
        setSeparatorColor(Color.parseColor(getString(R.string.color)));
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setVibrate(true);
        setVibrateIntensity(50);
        setFadeAnimation();
        setDepthAnimation();
        addSlide(AppIntroFragment.newInstance("Office Bot"
                , "Application is based on NFC technology to send and receive messages.",
                R.drawable.logo,
                getResources().getColor(R.color.colorAccent)));
        addSlide(FragmentIntroContent.newInstance("Refresh Activates"
                , "On Mostly activities, swipe down to refresh list views.",
                R.drawable.refresh_pull,
                getResources().getColor(R.color.colorAccent)));

        addSlide(FragmentIntroContent.newInstance("How to Add Tags"
                , "As Shown in figure above, simply press the float button " +
                        "in Home activity or select NFC Scan from side menu and a screen will appear" +
                        " showing NFC gif waiting for the user to tap on the tag. " +
                        "After a successfully tap,  a new activity will display to name a tag and select picture for it and press go",
                R.drawable.add_tag_new,
                getResources().getColor(R.color.colorAccent)));

        addSlide(FragmentIntroContent.newInstance("How to Add Friends"
                , "As Shown in figure above, simply go into Request a Friend activity in side menu and list will be " +
                        "shown through which you can search your friend and then press Send. Or simply accept the request " +
                        "you received in Request activity from side menu.",
                R.drawable.add_friends,
                getResources().getColor(R.color.colorAccent)));

        addSlide(FragmentIntroContent.newInstance("How to Create Groups and Add Friends in it"
                , "As Shown in figure above, simply go into Group activity from side menu and " +
                        "press 3 dot menu from upper left corner of the action bar. And for add friends " +
                        "long press on the group you want to add friend and then select option " +
                        "Add Friend and simply follow the instruction on the screen.",
                R.drawable.add_group_add_friend,
                getResources().getColor(R.color.colorAccent)));

        addSlide(FragmentIntroContent.newInstance("How to Post Notes"
                , "As Shown in figure above, simple press on the tag on which you want to leave " +
                        "note and then press float button. New Message activity will be displayed" +
                        " from which you can add text, record audio, attach a file, set a timer for " +
                        "flash notes and add permission too for people which can access it."
                , R.drawable.add_new_notes_post
                , getResources().getColor(R.color.colorAccent)));

        addSlide(FragmentIntroContent.newInstance("How to Set Tag Permission"
                , "As shown in figure above, follow the steps."
                , R.drawable.add_tag_permssion_add,
                getResources().getColor(R.color.colorAccent)));

        addSlide(FragmentIntroContent.newInstance("How to set Notes Permission"
                , "As shown in figure above, follow the steps."
                , R.drawable.add_notes_permission,
                getResources().getColor(R.color.colorAccent)));

    }

    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    public void onSlideChanged(@Nullable Fragment oldFragment
            , @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}

