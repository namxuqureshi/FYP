package com.example.n_u.officebotapp.viewsholders;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;
import com.github.fabtransitionactivity.SheetLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityTagMainHolder {
    @Bind (R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind (R.id.appbar)
    AppBarLayout appbar;
    @Bind (R.id.backBtnImg)
    ImageView backBtnImg;
    @Bind (R.id.toolbar)
    Toolbar toolbar;
    @Bind (R.id.text_title_in_bar)
    TextView textTitleInBar;
    @Bind (R.id.tabs)
    TabLayout tabs;
    @Bind (R.id.container)
    ViewPager container;
    @Bind (R.id.fab)
    FloatingActionButton fab;
    @Bind (R.id.bottom_sheet)
    SheetLayout bottomSheet;

    public ActivityTagMainHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public CoordinatorLayout getMainContent() {
        return mainContent;
    }

    public TextView getTextTitleInBar() {
        return textTitleInBar;
    }

    public SheetLayout getBottomSheet() {
        return bottomSheet;
    }

    public ViewPager getContainer() {
        return container;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public ImageView getBackBtnImg() {
        return backBtnImg;
    }

    public TabLayout getTabs() {
        return tabs;
    }

    public AppBarLayout getAppbar() {
        return appbar;
    }
}
