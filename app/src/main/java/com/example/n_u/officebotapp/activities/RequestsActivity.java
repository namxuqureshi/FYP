package com.example.n_u.officebotapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ListView;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.RequestListAdapter;
import com.example.n_u.officebotapp.intefaces.IRequests;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.Request;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.R.id.request_refresh_list;
import static com.example.n_u.officebotapp.R.id.requests_list;

public class RequestsActivity
        extends NavigationActivity {
    private Activity activity = this;
    private RequestListAdapter adapter = null;
    private SwingRightInAnimationAdapter animationAdapter = null;
    private HashMap<String, Object> body = new HashMap<>();
    private Call<ModelList> call = null;
    private ListView listview = null;
    private IRequests request = (IRequests) OfficeBotURI.retrofit().create(IRequests.class);
    private List<Request> requests = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout = null;

    void data() {
        body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), this));
        call = this.request.getFriendRequestList(this.body);
        if (!call.isExecuted())
            call.enqueue(new Callback<ModelList>() {
                public void onFailure(Call<ModelList> callback, Throwable throwable) {
//                    Toast.makeText(activity, throwable.getMessage(),
//                            Toast.LENGTH_LONG).show();
                    AppLog.logString(throwable.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }

                public void onResponse(Call<ModelList> callback, Response<ModelList> response) {
                    if (response.code() == 200) {
                        requests = response.body().getRequestList();
                        adapter.clear();
                        adapter.addAll(requests);
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        Snackbar.make(listview, response.message()
                                + response.code(), Snackbar.LENGTH_SHORT).show();
                    }
                    swipeRefreshLayout.setRefreshing(false);

                }
            });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_nav_requests);
        nav(getString(R.string.requests_activity));
        super.initializeInvisible();
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(request_refresh_list));
        listview = ((ListView) findViewById(requests_list));
        adapter = new RequestListAdapter(activity, this.requests, getSupportFragmentManager());
        animationAdapter = new SwingRightInAnimationAdapter(this.adapter);
        animationAdapter.setAbsListView(this.listview);
        listview.setAdapter(this.animationAdapter);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                    return;
                }
                AppLog.toastString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        this.swipeRefreshLayout.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramAnonymousView) {
                swipeRefreshLayout.setRefreshing(true);
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                    return false;
                }
                AppLog.toastString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());
                swipeRefreshLayout.setRefreshing(false);
                return false;
            }
        });
        listview.setEmptyView(findViewById(R.id.empty_view_text_request_below));
    }

    protected void onResume() {
        swipeRefreshLayout.performLongClick();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.block, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_block_list) {
            // FIXME: 06-Jul-17 For Block List View here
            AppLog.toastShortString("Working..", getApplicationContext());
        }
        return super.onOptionsItemSelected(item);
    }
}
