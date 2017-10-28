package com.example.n_u.officebotapp.activities.permissions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.SelectionFriendAdapter;
import com.example.n_u.officebotapp.adapters.SelectionGroupAdapter;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IFriends;
import com.example.n_u.officebotapp.intefaces.IGroups;
import com.example.n_u.officebotapp.models.Friend;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.activities.NewMessageActivity.REQUEST_CODE_FOR_SHARE;

public class SharePermissionActivity
        extends AppCompatActivity
        implements OnClickListener {
    private final HashMap<String, Object> body = new HashMap<>();
    private final IFriends friendListRequest = OfficeBotURI.retrofit().create(IFriends.class);
    private final IGroups groupListRequest = OfficeBotURI.retrofit().create(IGroups.class);
    private Call<ModelList> callRequest = null;
    private boolean check_friend_list = true, check_group_list = true;
    private Context context = this;
    private CompoundButton forEveryone = null, friend = null, group = null;
    private ArrayList<Integer> friendId = new ArrayList<>(), groupId = new ArrayList<>();
    private List<Friend> friendList = null;
    private List<Group> groupList = null;
    private Bundle permissionList = new Bundle();

    //    protected void onCreate(Bundle paramBundle) {
//        super.onCreate(paramBundle);
//        if (InternetConnection.checkConnection(getApplicationContext())) {
//            data();
//        }
//        for (; ; ) {
//            setContentView(2130968634);
//            this.group = ((CompoundButton) findViewById(2132017442));
//            this.friend = ((CompoundButton) findViewById(2132017559));
//            this.forEveryone = ((CompoundButton) findViewById(2132017558));
//            this.go = ((Button) findViewById(2132017444));
//            this.group.setOnClickListener(new OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    if ((forEveryone.isChecked()) || (friend.isChecked())) {
//                        forEveryone.setChecked(false);
//                        friend.setChecked(false);
//                    }
//                    SharePermissionActivity.access$202(SharePermissionActivity.this, false);
//                    SharePermissionActivity.access$302(SharePermissionActivity.this, true);
//                }
//            });
//            this.friend.setOnClickListener(new OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    if ((forEveryone.isChecked()) || (group.isChecked())) {
//                        forEveryone.setChecked(false);
//                        group.setChecked(false);
//                    }
//                    SharePermissionActivity.access$202(SharePermissionActivity.this, true);
//                    SharePermissionActivity.access$302(SharePermissionActivity.this, false);
//                }
//            });
//            this.forEveryone.setOnClickListener(new OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    if ((friend.isChecked()) || (group.isChecked())) {
//                        friend.setChecked(false);
//                        group.setChecked(false);
//                    }
//                    SharePermissionActivity.access$202(SharePermissionActivity.this, false);
//                    SharePermissionActivity.access$302(SharePermissionActivity.this, false);
//                }
//            });
//            findViewById(2132017438).setOnClickListener(new OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    onBackPressed();
//                }
//            });
//            this.go.setOnClickListener(new OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    Progress.Show(getApplication());
//                    if ((check_friend_list) && (friend.isChecked())) {
//                        new Runnable() {
//                            public void run() {
//                                if (friendId.isEmpty()) {
//                                    Iterator localIterator = friendList.iterator();
//                                    while (localIterator.hasNext()) {
//                                        Friend localFriend = (Friend) localIterator.next();
//                                        friendId.add(localFriend.getFriendId());
//                                    }
//                                    permissionList.putString(getString(2131427640), "1");
//                                    permissionList.putString(getString(2131427597), "4");
//                                }
//                                for (; ; ) {
//                                    Progress.Cancel();
//                                    setResult(1, new Intent().putExtra(getString(2131427724), permissionList));
//                                    finish();
//                                    return;
//                                    permissionList.putIntegerArrayList(getString(2131427793), friendId);
//                                    permissionList.putString(getString(2131427597), "2");
//                                }
//                            }
//                        }.run();
//                        return;
//                    }
//                    if ((check_group_list) && (group.isChecked())) {
//                        new Runnable() {
//                            public void run() {
//                                if ((groupId.isEmpty()) && (groupList.size() >= 1)) {
//                                    Iterator localIterator = groupList.iterator();
//                                    while (localIterator.hasNext()) {
//                                        Group localGroup = (Group) localIterator.next();
//                                        groupId.add(localGroup.getGroupId());
//                                    }
//                                }
//                                if ((groupId.size() == 1) && (Objects.equals(groupId.get(0), -10))) {
//                                    permissionList.putString(getString(2131427597), "4");
//                                }
//                                for (; ; ) {
//                                    permissionList.putIntegerArrayList(getString(2131427651), groupId);
//                                    Progress.Cancel();
//                                    setResult(1, new Intent().putExtra(getString(2131427724), permissionList));
//                                    finish();
//                                    return;
//                                    permissionList.putString(getString(2131427597), "3");
//                                }
//                            }
//                        }.run();
//                        return;
//                    }
//                    new Runnable() {
//                        public void run() {
//                            Progress.Cancel();
//                            permissionList.putString(getString(2131427597), "1");
//                            permissionList.putString(getString(2131427733), "1");
//                            setResult(1, new Intent().putExtra(getString(2131427724), permissionList));
//                            finish();
//                        }
//                    }.run();
//                }
//            });
//            ((TextView) findViewById(2132017641)).setText(2131427721);
//            return;
//            AppLog.toastString("Net Not Available", getApplicationContext());
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_share_permission);
        data();
        findViewById(R.id.backBtnImg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        group = (RadioButton) findViewById(R.id.checkBoxGroup);
        friend = (RadioButton) findViewById(R.id.checkBoxFriend);
        group.setEnabled(false);
        friend.setEnabled(false);
        forEveryone = (RadioButton) findViewById(R.id.checkBoxPublic);
        Button go = (Button) findViewById(R.id.go_permission);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forEveryone.isChecked() || friend.isChecked()) {
                    forEveryone.setChecked(false);
                    friend.setChecked(false);
                }
                check_friend_list = false;
                check_group_list = true;
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (forEveryone.isChecked() || group.isChecked()) {
                    forEveryone.setChecked(false);
                    group.setChecked(false);
                }
                check_friend_list = true;
                check_group_list = false;
            }
        });
        forEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friend.isChecked() || group.isChecked()) {
                    friend.setChecked(false);
                    group.setChecked(false);
                }
                check_friend_list = false;
                check_group_list = false;
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progress.Show(context);
                if (check_friend_list && friend.isChecked()) {
                    new Runnable() {

                        @Override
                        public void run() {
                            if (friendId.isEmpty()) {
                                for (Friend frd : friendList) {
                                    friendId.add(frd.getFriendId());
                                }
                                permissionList.putString("friends_only", "1");
                                permissionList.putString("case_type", "4");
                            } else {
                                permissionList.putIntegerArrayList("users", friendId);
                                permissionList.putString("case_type", "2");
                            }

                            Progress.Cancel();
                            setResult(REQUEST_CODE_FOR_SHARE,
                                    new Intent().putExtra("permission", permissionList));
                            finish();
                        }
                    }.run();
                } else if (check_group_list && group.isChecked()) {
                    new Runnable() {

                        @Override
                        public void run() {
                            if (groupId.isEmpty()) {
                                for (Group frd : groupList) {
                                    groupId.add(frd.getGroupId());
                                }
                            }
                            permissionList.putString("case_type", "3");
                            permissionList.putIntegerArrayList("groups", groupId);
                            Progress.Cancel();
                            setResult(REQUEST_CODE_FOR_SHARE,
                                    new Intent().putExtra("permission", permissionList));
                            finish();
                        }
                    }.run();

                } else {
                    new Runnable() {

                        @Override
                        public void run() {

                            Progress.Cancel();
                            permissionList.putString("case_type", "1");
                            permissionList.putString("public", "1");
                            setResult(REQUEST_CODE_FOR_SHARE,
                                    new Intent().putExtra("permission", permissionList));
                            finish();

                        }
                    }.run();
                }


            }
        });
        TextView title = (TextView) findViewById(R.id.text_title_in_bar);
        title.setText(R.string.permission);
    }

    public void canceled(View paramView) {
        finish();
    }

    void data() {
        friendList = new ArrayList<>();
        friendList.addAll(Friend.getAll());
        groupList = new ArrayList<>();
        groupList.addAll(Group.getAll());
        groupList.add(new Group(getString(R.string.friend_group_element)
                , Integer.parseInt(OBSession.getPreference(getString(R.string.id_key), context)), -10));
        if (groupList.size() < 1) {
            body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key)
                    , getApplicationContext()));
            Progress.Show(this);
            callRequest = groupListRequest.getGroupList(body);
            callRequest.enqueue(new Callback<ModelList>() {
                public void onFailure(Call<ModelList> callback
                        , Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Net Not Available"
                            , Toast.LENGTH_LONG).show();
                    Progress.Cancel();
                }

                public void onResponse(Call<ModelList> callback
                        , Response<ModelList> response) {
                    if (response.body() != null && response.code() == 200) {
                        groupList = response.body().getGroupList();
                        groupList.add(new Group(getString(R.string.friend_group_element)
                                , Integer.parseInt(OBSession.getPreference(getString(R.string.id_key)
                                , context)), -10));
                        if (friendList.size() < 1) {
                            callRequest = friendListRequest.getFriendList(body);
                            callRequest.enqueue(new Callback<ModelList>() {
                                public void onFailure(Call<ModelList> call
                                        , Throwable throwable) {
                                    Toast.makeText(getApplicationContext()
                                            , "Net Not Available"
                                            , Toast.LENGTH_LONG).show();
                                    Progress.Cancel();
                                }

                                public void onResponse(Call<ModelList> call
                                        , Response<ModelList> response) {
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
                                    } else {
                                        AppLog.toastString("Server not Available", context);
                                    }
                                    if (response.code() == 200) {
                                        if (response.body() != null) {
                                            friendList = response.body().getFriendList();
                                        }
                                    } else {
                                        Progress.Cancel();
                                        Toast.makeText(getApplicationContext()
                                                , "Server Offline"
                                                , Toast.LENGTH_LONG).show();
                                    }
                                    Progress.Cancel();
                                }
                            });
                        } else
                            Progress.Cancel();
                    } else {
                        AppLog.toastString("ServerOffline", getApplicationContext());
                    }

                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        final MaterialDialog.Builder mb = new MaterialDialog.Builder(this);
        mb.customView(R.layout.dialouge_list_view, false);
        mb.title(getString(R.string.selection_list));
        mb.positiveText(R.string.selective);
        mb.negativeText(R.string.cancel_btn);
        mb.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Toast.makeText(context, R.string.cancel, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        final MaterialDialog md = mb.build();
        RecyclerView rv = (RecyclerView) md.findViewById(R.id.dialog_rv_list);
        rv.setVisibility(View.VISIBLE);
        rv.setHasFixedSize(true);
        rv.setAnimation(new AnimationSet(true));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        switch (id) {
            case R.id.friend_select_list:
                final SelectionFriendAdapter adapterFriend;
                adapterFriend = new SelectionFriendAdapter(this, friendList);
                rv.setAdapter(adapterFriend);
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (friendId.isEmpty()) friendId.addAll(adapterFriend.checkedList());
                        else {
                            friendId.clear();
                            friendId.addAll(adapterFriend.checkedList());
                        }
                        AppLog.logString("onClick: " + friendId.toString());
                        friend.setChecked(true);
                        group.setChecked(false);
                        check_friend_list = true;
                        check_group_list = false;
                        friend.setEnabled(true);
                        dialog.cancel();
                    }
                });
                mb.show();
                break;
            case R.id.group_select_list:
                final SelectionGroupAdapter adapterGroup;
                adapterGroup = new SelectionGroupAdapter(this, groupList);
                rv.setAdapter(adapterGroup);
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (groupId.isEmpty()) groupId.addAll(adapterGroup.checkedList());
                        else {
                            groupId.clear();
                            groupId.addAll(adapterGroup.checkedList());
                        }
                        AppLog.logString("onClick: " + groupId.toString());
                        group.setChecked(true);
                        friend.setChecked(false);
                        check_friend_list = false;
                        check_group_list = true;
                        group.setEnabled(true);
                        dialog.cancel();
                    }
                });
                mb.show();
                break;
            default:
                break;
        }
    }

}