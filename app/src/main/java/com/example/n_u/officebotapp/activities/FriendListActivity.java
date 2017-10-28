package com.example.n_u.officebotapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.FriendListAdapter;
import com.example.n_u.officebotapp.intefaces.IFriends;
import com.example.n_u.officebotapp.models.Friend;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;

public class FriendListActivity
        extends NavigationActivity {
    private final HashMap<String, Object> body = new HashMap<>();
    private final IFriends request = (IFriends) OfficeBotURI.retrofit().create(IFriends.class);
    private FriendListAdapter adapter = null;
    private SlideInLeftAnimationAdapter alphaAdapter = null;
    private TextView emptyTextView = null;
    private Context friendListActivity = this;
    private Call<ModelList> call;
    private List<Friend> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout = null;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_nav_friend);
        nav(getString(R.string.friend_list_activity));
        emptyTextView = ((TextView) findViewById(R.id.empty_view_text_friend_list_below));
        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.rv_friend_list));
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.friend_list_refresh_list));
        adapter = new FriendListAdapter(friendListActivity, list, getSupportFragmentManager());
        alphaAdapter = new SlideInLeftAnimationAdapter(adapter);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1.0F)));
        alphaAdapter.setDuration(5000);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                    if (adapter.getItemCount() < 1) {
                        emptyTextView.setVisibility(View.VISIBLE);
                    } else emptyTextView.setVisibility(View.GONE);
                    return;
                }
                AppLog.toastString("Net Not Available", getApplicationContext());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramAnonymousView) {
                swipeRefreshLayout.setRefreshing(true);
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                    return false;
                }
                AppLog.toastString("Net Not Available", getApplicationContext());
                swipeRefreshLayout.setRefreshing(false);
                return false;
            }
        });
        try {
            adapter.addAll();
        } catch (Exception localException) {
            AppLog.logString(localException.getMessage());
        }
        if (adapter.getItemCount() < 1) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else emptyTextView.setVisibility(View.GONE);
    }

    protected void onResume() {
        try {

            adapter.addAll();
            swipeRefreshLayout.performLongClick();
            super.onResume();
        } catch (Exception localException) {
            AppLog.logString(localException.getMessage());
        }
    }

    void data() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), this));
            call = request.getFriendList(this.body);
            if (!call.isExecuted())
                call.enqueue(new Callback<ModelList>() {
                    public void onFailure(Call<ModelList> callback, Throwable t) {
//                        AppLog.toastShortString(t.getMessage(), getApplicationContext());
                        AppLog.logString(t.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    public void onResponse(Call<ModelList> callback,
                                           retrofit2.Response<ModelList> response) {

                        if (response.code() == 200 && response.body() != null) {
                            new Delete().from(Friend.class).execute();
                            ActiveAndroid.beginTransaction();
                            try {
                                for (Friend temp : response.body().getFriendList()) {
                                    Friend item = new Friend(temp);
                                    AppLog.logString(item.toString());
                                    item.save();
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            } finally {
                                ActiveAndroid.endTransaction();
                            }
                            adapter.clear();
                            adapter.addAll();
                            if (adapter.getItemCount() < 1) {
                                emptyTextView.setVisibility(View.VISIBLE);
                            } else emptyTextView.setVisibility(View.GONE);
                        } else {
                            AppLog.toastString("Server not Available", getApplicationContext());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        } else AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());
    }

    public void onCreateContextMenu(android.view.ContextMenu contextMenu,
                                    View view,
                                    android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        android.widget.ExpandableListView.ExpandableListContextMenuInfo info =
                (android.widget.ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        contextMenu.setHeaderTitle("Options for " + adapter.getName(android.widget.ExpandableListView.
                getPackedPositionGroup(info.packedPosition)));
        contextMenu.add(1, 1, 1, getString(R.string.un_friend));
        contextMenu.add(1, 2, 2, R.string.block);
    }

    public boolean onContextItemSelected(android.view.MenuItem menuItem) {
        final android.widget.ExpandableListView.ExpandableListContextMenuInfo info =
                (android.widget.ExpandableListView.ExpandableListContextMenuInfo)
                        menuItem.getMenuInfo();
        switch (menuItem.getItemId()) {
            case 1:
                AppLog.toastShortString("UNFriend Selected", getApplicationContext());
                break;
            case 2:
                AppLog.toastShortString("Block Selected", getApplicationContext());
                break;
        }
        return super.onContextItemSelected(menuItem);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (call != null) {
            if (call.isExecuted()) {
                call.cancel();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }
}


