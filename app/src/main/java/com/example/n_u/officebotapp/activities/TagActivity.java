package com.example.n_u.officebotapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.activities.permissions.TagPermissionActivity;
import com.example.n_u.officebotapp.adapters.TagAdapter;
import com.example.n_u.officebotapp.intefaces.ITags;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.models.Tag;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagActivity
        extends NavigationActivity {
    public static final int RESULT_SYNC = 1;
    public static final String S_NAME = "TagActivity";
    private final ITags request = (ITags) OfficeBotURI.retrofit().create(ITags.class);
    private TagAdapter adapter = null;
    private Call<ModelList> call = null;
    private FloatingActionButton fab = null;
    private ListView lv = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private Context tagActivity = this;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_navigation);
        if (getIntent().getStringExtra(getString(R.string.history)) == null
                || !getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history)))
            nav(getString(R.string.tag_activity));
        else nav(getString(R.string.history_activity));
        setUi();
        setListener();
        if (getIntent().getStringExtra(getString(R.string.history)) == null
                || !getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history)))
            registerForContextMenu(lv);
        else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    void setUi() {
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.tag_refresh_list));
        lv = ((ListView) findViewById(R.id.list_note));
        adapter = new TagAdapter(tagActivity);
        SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(lv);
        lv.setAdapter(animationAdapter);
        fab = ((FloatingActionButton) findViewById(R.id.fab));

    }

    void setListener() {
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TagActivity.this, TagMainActivity.class);
                AppLog.logString("onItemClick: " + adapter.getItem(position).getTagId());
                i.putExtra(getString(R.string.tag_id_key), adapter.getItem(position).getTagId());
                i.putExtra(getString(R.string.total_msg), Integer.parseInt(adapter.getItem(position).getMessage_count()));
                if (getIntent().getStringExtra(getString(R.string.history)) != null
                        && getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history)))
                    i.putExtra(getString(R.string.history), getString(R.string.history));
                AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                startActivity(i);
            }
        });
        if (getIntent().getStringExtra(getString(R.string.history)) == null
                || !getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history))) {
            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View AnonymousView) {
                    if (mNfcAdapter != null)
                        if (!mNfcAdapter.isEnabled()) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.nfc_activate,
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent("android.settings.NFC_SETTINGS"));
                        } else {
                            Intent intent = new Intent(TagActivity.this, NFCActivity.class);
                            intent.putExtra(TagActivity.this.getString(R.string.activity_key), "TagActivity");
                            startActivity(intent);
                        }
                    else Toast.makeText(getApplicationContext(),
                            R.string.nfc_not_support,
                            Toast.LENGTH_LONG).show();
                    AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                }
            });
            swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        data();
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
        }
        lv.setEmptyView(findViewById(R.id.empty_view_text_lv_below));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra(getString(R.string.history)) == null
                || !getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history))) {
            adapter.refresh(Integer.parseInt(OBSession.getPreference("id"
                    , getApplicationContext())));

        } else adapter.addAll(Integer.parseInt(OBSession.getPreference("id"
                , getApplicationContext())), true);
        if (OBSession.hasPreference(getString(R.string.v_r), getApplicationContext())) {
            swipeRefreshLayout.performLongClick();
            OBSession.delPreference(getString(R.string.v_r), getApplicationContext());
        }

    }

    private void data() {
        call = request.getTagList(OBSession.getPreference(getString(R.string.id_key), this));
        if (!call.isExecuted()) {
            call.enqueue(new Callback<ModelList>() {
                public void onFailure(Call<ModelList> callback, Throwable t) {
//                    Toast.makeText(TagActivity.this,
//                            "Request Cancel Or Net Not Available",
//                            Toast.LENGTH_SHORT).show();
                    AppLog.logString(t.getMessage() + "skdjs");
//                    AppLog.logString(t.getLocalizedMessage());
//                    AppLog.logString(t.toString());
                    swipeRefreshLayout.setRefreshing(false);
                }

                public void onResponse(Call<ModelList> callback,
                                       retrofit2.Response<ModelList> response) {
                    if (response.code() == 200 && response.body() != null) {
                        new Delete().from(Tag.class).execute();
                        ActiveAndroid.beginTransaction();
                        try {
                            for (Tag temp : response.body().getTagList()) {
                                Tag item = new Tag(temp);
                                item.save();
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                        adapter.clear();
                        adapter.addAll(Integer.parseInt(OBSession.getPreference("id", getApplicationContext())));
                    } else {
                        AppLog.toastString("Server not Available", context);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo Info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                final MaterialDialog.Builder mb = new MaterialDialog.Builder(tagActivity);
                mb.customView(R.layout.dialogue_box, true);
                mb.title(getString(R.string.edit_text_dialoge));
                mb.positiveText(R.string.okay);

                mb.negativeText(R.string.cancel_btn);
                mb.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(context, "Edit Cancel", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        AppLog.setVibrate(context, AppLog.INTENSITY_LOW);

                    }
                });
                final MaterialDialog md = mb.build();
                final EditText text = (EditText) md.findViewById(R.id.name);
                text.setText(adapter.getItem(Info.position).getName());
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (!text.getText().toString().isEmpty()) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("name", text.getText().toString());
                            Call<Status> callChange = request.setTagFeature(map, adapter.getItem(Info.position).getTagId());
                            callChange.enqueue(new Callback<Status>() {
                                @Override
                                public void onResponse(Call<Status> call, Response<Status> response) {
                                    if (response.code() == 200) {
                                        Tag.setTagName(adapter.getItem(Info.position).getTagId()
                                                , text.getText().toString());
                                        adapter.refresh(Integer.parseInt(OBSession.getPreference(getString(R.string.id_key), context)));
                                        Toast.makeText(context, "Edit Successfully", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Status> call, Throwable t) {
                                    AppLog.toastShortString(t.getMessage(), getApplicationContext());
                                }
                            });
                        } else
                            Toast.makeText(context, "Empty Feild", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                    }
                });
                mb.show();
                break;
            case 2:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, " +
                        "You wanted to delete this?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                java.util.Map<String, Object> map = new HashMap<>();
                                map.put(getString(R.string.tag_id_key), adapter.getItem(Info.position).getTagId());
                                Call<Status> callDelete = request.deleteTag(map);
                                callDelete.enqueue(new Callback<Status>() {
                                    @Override
                                    public void onResponse(Call<Status> call, Response<Status> response) {
                                        if (response.code() == 200) {
                                            AppLog.logString(String.valueOf(adapter.getItem(Info.position).getTagId()));
                                            AppLog.logString(response.body().toString());
                                            try {
                                                new Delete().from(Tag.class).where("tag_id =?"
                                                        , adapter.getItem(Info.position).getTagId()).execute();
                                            } catch (SQLiteException e) {
                                                AppLog.logString(e.getMessage());
                                                AppLog.toastShortString(e.getMessage(), getApplicationContext());
                                            }
                                            adapter.refresh(Integer.parseInt(OBSession.getPreference(getString(R.string.id_key)
                                                    , context)));
                                        } else
                                            AppLog.toastShortString(response.message(), getApplicationContext());
                                    }

                                    @Override
                                    public void onFailure(Call<Status> call, Throwable t) {
                                        AppLog.toastShortString(t.getMessage(), getApplicationContext());
                                    }
                                });
                                dialog.cancel();
                                AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                    }
                });
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case 3:
                Intent i = new Intent(this, TagPermissionActivity.class);
                i.putExtra(getString(R.string.tag_id_key), adapter.getItem((Info.position)).getTagId());
                startActivity(i);
                AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu paramContextMenu,
                                    View paramView,
                                    ContextMenuInfo paramContextMenuInfo) {
        super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
        final AdapterView.AdapterContextMenuInfo aInfo = (AdapterView.AdapterContextMenuInfo) paramContextMenuInfo;
        paramContextMenu.setHeaderTitle("Options for " + adapter.getItem(aInfo.position).getName());
        paramContextMenu.add(1, 1, 1, R.string.edit_text_dialoge);
        paramContextMenu.add(1, 2, 2, R.string.delete);
        paramContextMenu.add(1, 3, 3, R.string.add_permission_menu);
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
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_FIRST_USER) {
            if (resultCode == RESULT_SYNC) {
                swipeRefreshLayout.performLongClick();
            }
        }
    }
}