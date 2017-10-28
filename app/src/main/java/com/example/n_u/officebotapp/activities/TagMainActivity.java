package com.example.n_u.officebotapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.SectionsPagerAdapter;
import com.example.n_u.officebotapp.utils.AppLog;
import com.github.fabtransitionactivity.SheetLayout;

public class TagMainActivity
        extends AppCompatActivity
        implements SheetLayout.OnFabAnimationEndListener {
    public static final String S_NAME = "OwnerActivity";
    private static final int REQUEST_CODE = 1;
    FloatingActionButton fab = null;
    Context context = this;
    private SectionsPagerAdapter mSectionsPagerAdapter = null;
    private SheetLayout mSheetLayout = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        findViewById(R.id.backBtnImg).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                onBackPressed();
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        ViewPager vp = (ViewPager) findViewById(R.id.container);
        vp.setAdapter(mSectionsPagerAdapter);
        vp.setPageMargin(20);
        vp.setPageTransformer(true, new CubeOutTransformer());
        mSheetLayout = ((SheetLayout) findViewById(R.id.bottom_sheet));
        TabLayout tab = (TabLayout) findViewById(R.id.tabs);
        tab.setupWithViewPager(vp, true);
        fab = ((FloatingActionButton) findViewById(R.id.fab));
        fab.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                mSheetLayout.expandFab();
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        ((TextView) findViewById(R.id.text_title_in_bar)).setText(R.string.tag_main_activity);
        mSheetLayout.setFab(fab);
        mSheetLayout.setFabAnimationEndListener(this);
    }

    public void onActivityResult(int requestCode
            , int resultCode
            , Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            mSheetLayout.contractFab();
        }
    }

    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, NewMessageActivity.class);
        intent.putExtra(getString(R.string.activity_key), "OwnerActivity");
        intent.putExtra(getString(R.string.tag_id_key), getIntent().getIntExtra(getString(R.string.tag_id_key), 0));
        intent.putExtra(getString(R.string.total_msg), getIntent().getIntExtra(getString(R.string.total_msg), 0));
        startActivityForResult(intent, REQUEST_CODE);
    }
}
