package com.example.n_u.officebotapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.GroupListAdapter;
import com.example.n_u.officebotapp.adapters.SelectionFriendAdapter;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IFriends;
import com.example.n_u.officebotapp.intefaces.IGroups;
import com.example.n_u.officebotapp.intefaces.IStatus;
import com.example.n_u.officebotapp.models.Friend;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.Member;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.viewsholders.ActivityGroupsHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.R.id.empty_view_text_group_below;

public class GroupsActivity
        extends NavigationActivity {
    private final HashMap<String, Object> body = new HashMap<>();
    private final IFriends friendListRequest = (IFriends) OfficeBotURI.retrofit().create(IFriends.class);
    private final IGroups groupListRequest = (IGroups) OfficeBotURI.retrofit().create(IGroups.class);
    private final IStatus statusRequest = (IStatus) OfficeBotURI.retrofit().create(IStatus.class);
    private List<Integer> friendId = new ArrayList<>();
    private GroupListAdapter listAdapter;
    private Call<ModelList> listCall;
    private HashMap<Group, List<Member>> listDataChild;
    private List<Group> listDataHeader;
    private List<Friend> listFriend;
    private Call<Status> statusCall;
    private ActivityGroupsHolder holder;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_nav_groups, null);
        holder = new ActivityGroupsHolder(v);
        setContentView(v);
        setUi();
        setListener();
    }

    void setListener() {
        holder.getGroupRefreshList().setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                    return;
                }
                AppLog.toastString("Net Not Available", getApplicationContext());
                holder.getGroupRefreshList().setRefreshing(false);
            }
        });
        holder.getGroupRefreshList().setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                holder.getGroupRefreshList().setRefreshing(true);
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    data();
                    return false;
                }
                AppLog.toastString("Net Not Available", getApplicationContext());
                holder.getGroupRefreshList().setRefreshing(false);
                return false;
            }
        });
    }

    void setUi() {
        nav(getString(R.string.group_activity));
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listAdapter = new GroupListAdapter(context, listDataHeader, listDataChild, getSupportFragmentManager());
        holder.getLvExpendableGroup().setAnimation(new TranslateAnimation(this, null));
        holder.getLvExpendableGroup().setAnimation(new AlphaAnimation(1.5F, 2.5F));
        holder.getLvExpendableGroup().setAdapter(listAdapter);
        holder.getLvExpendableGroup().setSoundEffectsEnabled(true);
        holder.getLvExpendableGroup().setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                AppLog.setVibrate(context, AppLog.INTENSITY_LOW);
            }
        });
        registerForContextMenu(holder.getLvExpendableGroup());
        holder.getLvExpendableGroup().setEmptyView(findViewById(empty_view_text_group_below));

    }

    protected void onResume() {
        super.onResume();
        try {
            holder.getGroupRefreshList().performLongClick();
            listAdapter.addAll();
        } catch (Exception localException) {
            AppLog.logString(localException.getMessage());
        }
    }

    private void data() {
        body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), this));
        listCall = groupListRequest.getGroupList(body);
        if (!listCall.isExecuted())
            listCall.enqueue(new Callback<ModelList>() {
                @Override
                public void onFailure(Call<ModelList> callback
                        , Throwable t) {

//                    AppLog.toastString("Net Not Available", getApplication());
                    AppLog.logString(t.getMessage());
//                    AppLog.logString(t.getLocalizedMessage());
//                    AppLog.logString(t.getCause().getLocalizedMessage());
                    holder.getGroupRefreshList().setRefreshing(false);
                }

                @Override
                public void onResponse(Call<ModelList> callback
                        , Response<ModelList> response) {
                    if (response.code() == 200) {
                        if (!listDataHeader.containsAll(response.body().getGroupList())) {
                            new Delete().from(Member.class).execute();
                            new Delete().from(Group.class).execute();
                            ActiveAndroid.beginTransaction();
                            try {
                                for (Group temp : response.body().getGroupList()) {
                                    Group item = new Group(temp);
                                    for (Member mem : temp.getMember()) {
                                        Member m = new Member(mem);
                                        m.save();
                                    }
                                    AppLog.logString(item.getMember().toString());
                                    item.save();
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            } finally {
                                ActiveAndroid.endTransaction();
                            }
                            listAdapter.clear();
                            listAdapter.addAll();
//                        listDataHeader.clear();
//                        listDataHeader.addAll(response.body().getGroupList());
//                        int j = listDataHeader.size();
//                        int i = 0;
//                        while (i < j) {
//                            listDataChild.put(listDataHeader.get(i),
//                                    listDataHeader.get(i).getMember());
//                            i += 1;
//                        }
                        }
                        listAdapter.notifyDataSetChanged();
                        holder.getGroupRefreshList().setRefreshing(false);
                    } else
                        Snackbar.make(holder.getGroupRefreshList(), "Server Offline",
                                Snackbar.LENGTH_SHORT).show();
                    holder.getGroupRefreshList().setRefreshing(false);
                }
            });
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        final ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo)
                        menuItem.getMenuInfo();
        switch (menuItem.getItemId()) {
            case 1:
                AppLog.toastShortString("Edit Selected", getApplicationContext());
                break;
            case 2:
                body.put(getString(R.string.group_id_key), listDataHeader.get(ExpandableListView.
                        getPackedPositionGroup(info.packedPosition))
                        .getGroupId());
                statusCall = statusRequest.getGroupDeleteStatus(body);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                final AlertDialog alertDialog;
                alertDialogBuilder.setMessage("Are you sure, " +
                        "You wanted to delete this?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                Progress.Show(context);
                                statusCall.enqueue(new Callback<Status>() {
                                    @Override
                                    public void onResponse(Call<Status> call, Response<Status> response) {
                                        if (response.code() == 200) {
                                            Toast.makeText(context, "Delete Selected Group", Toast.LENGTH_SHORT).show();
                                            listAdapter.delete(listDataHeader.get(ExpandableListView.
                                                    getPackedPositionGroup(info.packedPosition)));
                                        } else
                                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                        Progress.Cancel();
                                        dialog.cancel();
                                        holder.getGroupRefreshList().performLongClick();
                                    }

                                    @Override
                                    public void onFailure(Call<Status> call, Throwable t) {
                                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        Progress.Cancel();
                                    }
                                });
                                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
                            }
                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        AppLog.setVibrate(context, AppLog.INTENSITY_HIGH);
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case 3:
                body.put(getString(R.string.user_id_key)
                        , OBSession.getPreference(getString(R.string.id_key), context));
                body.put(getString(R.string.group_id_key), String.valueOf(listDataHeader.get(ExpandableListView.
                        getPackedPositionGroup(info.packedPosition)).getGroupId()));
                listCall = friendListRequest.getGroupMemberList(body);
                listFriend = new ArrayList<>();
                listFriend.addAll(Friend.getAll());
                AppLog.logString(listFriend.toString() + " " + listFriend.size());
                if (listFriend.size() < 1) {
                    Progress.Show(context);
                    listCall.enqueue(new retrofit2.Callback<ModelList>() {
                        @Override
                        public void onResponse(retrofit2.Call<ModelList> call,
                                               retrofit2.Response<ModelList> response) {
                            listFriend = response.body().getFriendList();
                            if (response.code() == 200 && response.body() != null) {
                                new Delete().from(Friend.class).execute();
                                ActiveAndroid.beginTransaction();
                                try {
                                    for (Friend temp : response.body().getFriendList()) {
                                        Friend item = new Friend(temp);
                                        item.save();
                                    }
                                    ActiveAndroid.setTransactionSuccessful();
                                } finally {
                                    ActiveAndroid.endTransaction();

                                }
                                listFriend.addAll(Friend.getAll());
                                setFriendList(info.packedPosition);
                            } else {
                                AppLog.toastString("Server not Available", context);
                            }
                            Progress.Cancel();
                        }

                        @Override
                        public void onFailure(Call<ModelList> call, Throwable t) {
                            AppLog.logString("onResponse: END" + t.getMessage() +
                                    "\n " + t.getLocalizedMessage() +
                                    "\n " + t.getCause() +
                                    "\n " + Arrays.toString(t.getStackTrace()));
                            Progress.Cancel();
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    setFriendList(info.packedPosition);
                }

                break;
        }
        return super.onContextItemSelected(menuItem);
    }

    public void onCreateContextMenu(ContextMenu contextMenu,
                                    View view,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(contextMenu, view, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        contextMenu.setHeaderTitle("Options for " + listAdapter.getGroup(ExpandableListView.
                getPackedPositionGroup(info.packedPosition)));
        contextMenu.add(1, 1, 1, getString(R.string.edit_text_dialoge));
        contextMenu.add(1, 2, 2, R.string.delete);
        contextMenu.add(1, 3, 3, R.string.add_friend);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tag, menu);
        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return super.onNavigationItemSelected(item);
    }

    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add_new_group) {
            final MaterialDialog.Builder mb = new MaterialDialog.Builder(this);
            mb.customView(R.layout.dialogue_box, true);
            mb.title(getString(R.string.add_new_group));
            mb.positiveText(R.string.create);
            mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                    if (!((EditText) dialog.findViewById(R.id.name)).getText().toString().isEmpty()) {
                        body.put(getString(R.string.name_key), ((EditText) dialog.findViewById(R.id.name)).getText().toString());
                        body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), context));
                        statusCall = statusRequest.getGroupAddStatus(body);
                        Progress.Show(context, getString(R.string.new_group));
                        statusCall.enqueue(new Callback<Status>() {
                            public void onFailure(Call<Status> callback,
                                                  Throwable throwable) {
                                Toast.makeText(GroupsActivity.this, throwable.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                            public void onResponse(Call<Status> callback,
                                                   Response<Status> response) {
                                if (response.code() == 200) {
                                    if (response.body().isGroup()) {
                                        Toast.makeText(GroupsActivity.this, R.string.success,
                                                Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        Progress.Cancel();
                                        holder.getGroupRefreshList().performClick();
                                    } else {
                                        Toast.makeText(GroupsActivity.this, R.string.not_success,
                                                Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        Progress.Cancel();
                                    }
                                    holder.getGroupRefreshList().performClick();
                                } else
                                    Toast.makeText(GroupsActivity.this,
                                            "Server Offline" + response.code(),
                                            Toast.LENGTH_SHORT).show();
                                Progress.Cancel();
                                holder.getGroupRefreshList().performLongClick();

                            }
                        });
                    } else {
                        ((EditText) dialog.findViewById(R.id.name)).setError(getString(R.string.field_empty));
                        mb.show();
                    }
                }
            });
            mb.negativeText(R.string.cancel_btn);
            mb.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });
            final MaterialDialog md = mb.build();
            mb.show();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void setFriendList(final long pos) {
        final RecyclerView rv;
        final MaterialDialog.Builder mb = new MaterialDialog.Builder(context);
        mb.customView(R.layout.dialouge_list_view, false);
        mb.title(getString(R.string.add_friend));
        final MaterialDialog md = mb.build();
        final SelectionFriendAdapter adapterFriend;
        List<Member> trap = new ArrayList<>();
        trap.addAll(listAdapter.getChildDetail(ExpandableListView.
                getPackedPositionGroup(pos)));
        List<Friend> temporary = new ArrayList<>();
        temporary.addAll(listFriend);
        for (Friend frd : temporary) {
            for (Member temp : trap) {
                if (temp.getUserId() == frd.getFriendId() && temp.getName().equalsIgnoreCase(frd.getName())) {
                    listFriend.remove(frd);
                } else AppLog.logString(temp.getName());
            }
        }
        adapterFriend = new SelectionFriendAdapter(getApplicationContext(), listFriend);
        rv = (RecyclerView) md.findViewById(R.id.dialog_rv_list);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapterFriend);
        mb.positiveText(R.string.selective);
        mb.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                if (friendId.isEmpty())
                    friendId.addAll(adapterFriend.checkedList());
                else {
                    friendId.clear();
                    friendId.addAll(adapterFriend.checkedList());
                }
                body.put(getString(R.string.group_id_key), listDataHeader.get(ExpandableListView.
                        getPackedPositionGroup(pos)).getGroupId());
                body.put(getString(R.string.id_key), friendId);
                retrofit2.Call<Status> call = statusRequest.getFriendAddInGroupStatus(body);
                dialog.cancel();
                if (InternetConnection.checkConnection(context)) {
                    Progress.Show(context, "Adding Friend to group..");
                    call.enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            if (response.body().isMembers()) {
                                Toast.makeText(GroupsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                Progress.Cancel();
                                holder.getGroupRefreshList().performClick();
                                listAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(GroupsActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();
                                Progress.Cancel();
                                holder.getGroupRefreshList().performClick();
                                listAdapter.notifyDataSetChanged();
                            }
                            dialog.cancel();
                            Progress.Cancel();
                            holder.getGroupRefreshList().performLongClick();

                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            Toast.makeText(GroupsActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                            Progress.Cancel();
                            onStart();

                        }
                    });
                } else AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE, getApplicationContext());
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        mb.negativeText(R.string.cancel_btn);
        mb.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.cancel();
                AppLog.setVibrate(context, AppLog.INTENSITY_HIGH);
            }
        });
        mb.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (listCall != null) {
            if (listCall.isExecuted()) {
                listCall.cancel();
                holder.getGroupRefreshList().setRefreshing(false);
            }
        }

    }

}
