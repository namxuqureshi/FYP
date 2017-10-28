package com.example.n_u.officebotapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.RequestFriendAdapter;
import com.example.n_u.officebotapp.intefaces.IRequests;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.SearchUser;
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
import retrofit2.Response;

import static com.example.n_u.officebotapp.R.id.empty_view_text_request_friend_list_below;
import static com.example.n_u.officebotapp.R.id.search_refresh_list;
import static com.example.n_u.officebotapp.R.id.search_view;

public class RequestFriendActivity
        extends AppCompatActivity
        implements OnQueryTextListener {
    public static final int DURATION = 200;
    public static final int DURATION_SECOND = 500;
    private final HashMap<String, Object> body = new HashMap<>();
    private final IRequests user = (IRequests) OfficeBotURI.retrofit().create(IRequests.class);
    private RequestFriendAdapter adapter = null;
    private SlideInLeftAnimationAdapter alphaAdapter = null;
    private Context context = this;
    private TextView emptyTextView = null;
    private SearchView mSearchView = null;
    private RecyclerView recyclerViewRequest = null;
    private List<SearchUser> searchUserArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout = null;

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint(getString(R.string.search_here));
    }

    public void data() {
        body.put(getString(R.string.name_key), "");
        body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), context));
        if (!user.getSearchUserList(body).isExecuted())
            user.getSearchUserList(body).enqueue(new Callback<ModelList>() {
                public void onFailure(Call<ModelList> callback
                        , Throwable t) {
//                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    AppLog.logString(t.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }

                public void onResponse(Call<ModelList> callback
                        , Response<ModelList> response) {
                    if (response.code() == 200) {
                        if (response.toString().isEmpty()) {
                            Toast.makeText(context,
                                    "Empty",
                                    Toast.LENGTH_SHORT).show();
                            emptyTextView.setVisibility(View.VISIBLE);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Snackbar.make(recyclerViewRequest, "List Refreshed!",
                                    Snackbar.LENGTH_SHORT).show();
//                        if (response.body().getUserList() != null)
//                            searchUserArrayList = response.body().getUserList();

                            new Delete().from(SearchUser.class).execute();
                            ActiveAndroid.beginTransaction();
                            try {
                                for (SearchUser temp : response.body().getUserList()) {
                                    SearchUser item = new SearchUser(temp);
                                    item.save();
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            } finally {
                                ActiveAndroid.endTransaction();
                            }
                            adapter.clear();
                            adapter.addAll();
                            if (adapter.getItemCount() < 1)
                                emptyTextView.setVisibility(View.VISIBLE);
                            else emptyTextView.setVisibility(View.GONE);
                        }
                    } else {
                        Snackbar.make(recyclerViewRequest,
                                response.code() + response.message(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                    if (adapter.getItemCount() >= 1) {
                        emptyTextView.setVisibility(View.GONE);
                    }
                    swipeRefreshLayout.setRefreshing(false);

                }
            });
    }

    public void onBackPressed(View view) {
        onBackPressed();
        AppLog.setVibrate(this, AppLog.INTENSITY_MIDDLE);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_friend);
        setUi();
        setListener();
        setupSearchView();
        swipeRefreshLayout.performLongClick();
    }

    void setUi() {
        recyclerViewRequest = ((RecyclerView) findViewById(R.id.search_list_view));
        mSearchView = ((SearchView) findViewById(search_view));
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(search_refresh_list));
        emptyTextView = ((TextView) findViewById(empty_view_text_request_friend_list_below));
        adapter = new RequestFriendAdapter(context, searchUserArrayList, getSupportFragmentManager());
        alphaAdapter = new SlideInLeftAnimationAdapter(adapter);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(DURATION);
        recyclerViewRequest.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1.0F)));
        recyclerViewRequest.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        alphaAdapter.setDuration(DURATION_SECOND);
        recyclerViewRequest.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewRequest.setAdapter(alphaAdapter);
        if (adapter.getItemCount() < 1)
            emptyTextView.setVisibility(View.VISIBLE);
        else emptyTextView.setVisibility(View.GONE);
    }

    void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                } else {
                    AppLog.toastString("Net Not Available", getApplicationContext());
                    swipeRefreshLayout.setRefreshing(false);
                }
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
    }

    public boolean onQueryTextChange(String paramString) {
        adapter.filter(paramString);
        return true;
    }

    public boolean onQueryTextSubmit(String paramString) {
        return false;
    }

    protected void onResume() {
        swipeRefreshLayout.performLongClick();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (user != null) {
            if (user.getSearchUserList(body).isExecuted()) {
                user.getFriendRequestList(body).cancel();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }
}