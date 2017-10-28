package com.example.n_u.officebotapp.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.SectionPagerOtherAdapter;
import com.example.n_u.officebotapp.utils.AppLog;

import java.util.Objects;

public class ScanTagActivity
        extends AppCompatActivity {
    public static final String S_NAME = "ScanActivity";
    private SectionPagerOtherAdapter mSectionsPagerAdapter = null;

    @TargetApi (Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_tag_main);
        mSectionsPagerAdapter = new SectionPagerOtherAdapter(getSupportFragmentManager());
        findViewById(R.id.backBtnImg).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                ScanTagActivity.this.onBackPressed();
            }
        });
        AppLog.logString(getIntent().getIntExtra(getString(R.string.tag_id_key), 0));
        ViewPager vp = (ViewPager) findViewById(R.id.container);
        vp.setAdapter(mSectionsPagerAdapter);
        FloatingActionButton localFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(vp);
        if (Objects.equals(getIntent().getStringExtra(getString(R.string.permission_key)), "rw")) {
            localFloatingActionButton.setOnClickListener(new OnClickListener() {
                public void onClick(View vi) {
                    Intent v = new Intent(ScanTagActivity.this, NewMessageActivity.class);
                    v.putExtra(getString(R.string.activity_key), S_NAME);
                    v.putExtra(getString(R.string.tag_id_key), getIntent().getIntExtra(getString(R.string.tag_id_key), 0));
                    startActivity(v);
                }
            });
        } else {

            localFloatingActionButton.setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.text_title_in_bar)).setText(R.string.scan);
    }
}

