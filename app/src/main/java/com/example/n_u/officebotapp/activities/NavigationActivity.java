package com.example.n_u.officebotapp.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.intefaces.IRequests;
import com.example.n_u.officebotapp.intefaces.IUsers;
import com.example.n_u.officebotapp.models.Friend;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.Message;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.Reply;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.models.Tag;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.util.HashMap;
import java.util.Objects;

import static android.support.v4.view.MenuItemCompat.getActionView;

public class NavigationActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_INVITE = 1020;
    protected final Context context = this;
    protected DrawerLayout drawer = null;
    protected NavigationView navigationView = null;
    protected ActionBarDrawerToggle toggle = null;
    protected Toolbar toolbar = null;
    NfcAdapter mNfcAdapter;
    TextView badgeFriendRequests;
    private HashMap<String, Object> body = new HashMap<>();
    private IRequests request = (IRequests) OfficeBotURI.retrofit().create(IRequests.class);

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_navigation);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nav("Navigation");
    }

    protected void nav(String name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        toolbar.setTitleMarginStart(0);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        badgeFriendRequests = (TextView) getActionView(navigationView.getMenu().
                findItem(R.id.nav_requests));
    }

    private void initializeCountDrawer(int value) {
        badgeFriendRequests.setGravity(Gravity.CENTER_VERTICAL);
        badgeFriendRequests.setTypeface(null, Typeface.BOLD);
        badgeFriendRequests.setTextColor(getResources().getColor(R.color.colorAccent));
        if (!OBSession.hasIntPreference(getString(com.example.n_u.officebotapp.R.string.REQUEST_COUNT), context)) {
            OBSession.putPreference(getString(com.example.n_u.officebotapp.R.string.REQUEST_COUNT), value, context);
        }
        if (value > 0)
            badgeFriendRequests.setText("" + value);
        else badgeFriendRequests.setVisibility(android.view.View.INVISIBLE);

    }

    protected void initializeInvisible() {
        badgeFriendRequests.setVisibility(android.view.View.INVISIBLE);
    }

    private void data() {
        body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), this));
        final retrofit2.Call<ModelList> call = request.getFriendRequestList(this.body);
        if (!call.isExecuted())
            call.enqueue(new retrofit2.Callback<ModelList>() {
                public void onFailure(retrofit2.Call<ModelList> callback, Throwable throwable) {
                    Toast.makeText(context, throwable.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                public void onResponse(retrofit2.Call<ModelList> callback,
                                       retrofit2.Response<ModelList> response) {
                    if (response.code() == 200) {
                        initializeCountDrawer(response.body().getRequestList().size());
                        if (OBSession.hasIntPreference(getString(com.example.n_u.officebotapp.R.string.REQUEST_COUNT), context)) {
                            if (OBSession.getPreference(getString(com.example.n_u.officebotapp.R.string.REQUEST_COUNT), context, true) < response.body().getRequestList().size())
                                badgeFriendRequests.setVisibility(android.view.View.VISIBLE);
                        }
                    }

                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        data();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void nav(boolean b, String name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        toolbar.setTitleMarginStart(0);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        badgeFriendRequests = (TextView) getActionView(navigationView.getMenu().
                findItem(R.id.nav_requests));
    }

    @TargetApi (Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_home:
                if (Objects.equals(getClass(), TagActivity.class)) {
                    this.drawer.closeDrawers();
                } else {
                    intent = new Intent(this, TagActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
            case R.id.nav_profile:
                if (Objects.equals(getClass(), ProfileTagActivity.class)) {
                    drawer.closeDrawers();
                } else {
                    intent = new Intent(this, ProfileTagActivity.class);
                    startActivity(intent);
                }
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
//            case R.id.nav_search:
//                //TODO
//                break;
            case R.id.nav_scan:
                if (mNfcAdapter != null)
                    if (!mNfcAdapter.isEnabled()) {
                        Toast.makeText(getApplicationContext(),
                                R.string.nfc_activate,
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent("android.settings.NFC_SETTINGS"));
                    } else {
                        intent = new Intent(this, NFCActivity.class);
                        intent.putExtra(this.getString(R.string.activity_key), "TagActivity");
                        startActivity(intent);
                    }
                else
                    Toast.makeText(getApplicationContext(),
                            R.string.nfc_not_support,
                            Toast.LENGTH_LONG).show();
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
//            case R.id.nav_history:
//                if (Objects.equals(getClass(), TagActivity.class)) {
//                    this.drawer.closeDrawers();
//                } else {
//                    intent = new Intent(this, TagActivity.class);
//                    intent.putExtra(getString(R.string.history), getString(R.string.history));
//                    startActivity(intent);
//                }
//                break;
            case R.id.nav_friend_list:
                if (Objects.equals(getClass(), FriendListActivity.class)) {
                    this.drawer.closeDrawers();
                } else {
                    intent = new Intent(this, FriendListActivity.class);
                    startActivity(intent);
                }
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
            case R.id.nav_groups:
                if (Objects.equals(getClass(), GroupsActivity.class)) {
                    this.drawer.closeDrawers();
                } else {
                    intent = new Intent(this, GroupsActivity.class);
                    startActivity(intent);
                }
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
            case R.id.nav_requests:
                if (Objects.equals(getClass(), RequestsActivity.class)) {
                    this.drawer.closeDrawers();
                } else {
                    intent = new Intent(this, RequestsActivity.class);
                    startActivity(intent);
                }
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
            case R.id.nav_requests_friends:
                intent = new Intent(this, RequestFriendActivity.class);
                startActivity(intent);
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
            case R.id.nav_share:
                intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                        .setMessage(getString(R.string.invitation_message))
                        .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
//                        .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                        .setCallToActionText(getString(R.string.invitation_cta))
                        .build();
                startActivityForResult(intent, REQUEST_INVITE);
                break;
            case R.id.nav_logout:

                FacebookSdk.sdkInitialize(context);
                LoginManager.getInstance().logOut();
                new Delete().from(Friend.class).execute();
                new Delete().from(Message.class).execute();
                new Delete().from(Group.class).execute();
                new Delete().from(Reply.class).execute();
                new Delete().from(SearchUser.class).execute();
                new Delete().from(Tag.class).execute();
                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                AppLog.setVibrate(this, AppLog.INTENSITY_HIGH);
                final IUsers iUsers = OfficeBotURI.retrofit().create(IUsers.class);
                HashMap<String, Object> map = new HashMap<>();
                map.put("device_id", "null");
                retrofit2.Call<Status> call = iUsers.deviceId(map,
                        OBSession.getPreference("id", getApplicationContext()));
                if (!call.isExecuted()) {
                    call.enqueue(new retrofit2.Callback<Status>() {
                        @Override
                        public void onResponse(retrofit2.Call<Status> call, retrofit2.Response<Status> response) {
                            if (response.code() == 200) {
                                AppLog.logString(response.body().toString());
                                AppLog.logString("Success");
                            } else AppLog.logString("Fail");
                        }

                        @Override
                        public void onFailure(retrofit2.Call<Status> call, Throwable t) {
                            AppLog.logString(t.getMessage());
                        }
                    });
                }
                OBSession.deletePreference(getApplicationContext());
                finish();
                break;
            case R.id.nav_setting:
                if (Objects.equals(getClass(), ProfileSettingActivity.class)) {
                    this.drawer.closeDrawers();
                } else {
                    intent = new Intent(this, ProfileSettingActivity.class);
                    startActivity(intent);
                }
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
            case R.id.nav_help:
                intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
                AppLog.setVibrate(this, AppLog.INTENSITY_LOW);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLog.logString("onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    AppLog.logString("onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }
}
