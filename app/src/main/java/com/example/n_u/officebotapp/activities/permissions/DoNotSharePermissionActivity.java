package com.example.n_u.officebotapp.activities.permissions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.activities.NewMessageActivity;
import com.example.n_u.officebotapp.adapters.SelectionGroupAdapter;
import com.example.n_u.officebotapp.adapters.SelectionUserAdapter;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IGroups;
import com.example.n_u.officebotapp.intefaces.IRequests;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoNotSharePermissionActivity
        extends AppCompatActivity
        implements OnClickListener {
    private final HashMap<String, Object> body = new HashMap<>();
    private final IGroups groupListRequest = (IGroups) OfficeBotURI.retrofit().create(IGroups.class);
    private final IRequests userListRequest = (IRequests) OfficeBotURI.retrofit().create(IRequests.class);
    private boolean check_group_list = true;
    private Context context = this;
    private Button go;
    private CompoundButton group = null;
    private ArrayList<Integer> groupId = new ArrayList<>();
    private ArrayList<Integer> userId = new ArrayList<>();
    private List<Group> groupList;
    private List<SearchUser> userList;
    private Call<ModelList> callRequest;
    private Bundle permissionList = new Bundle();
    private CompoundButton user = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogue_do_not_share_permission);
        data();
        findViewById(R.id.backBtnImg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        group = (CompoundButton) findViewById(R.id.checkBoxGroup);
        user = (CompoundButton) findViewById(R.id.checkBoxUser);
        group.setEnabled(false);
        user.setEnabled(false);
        go = (Button) findViewById(R.id.go_permission);
        group.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.isChecked()) {
                    user.setChecked(false);
                }
                check_group_list = true;
            }
        });

        user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (group.isChecked()) {
                    group.setChecked(false);

                }
                check_group_list = false;
            }
        });

        go.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Progress.Show(context);
                if (check_group_list && group.isChecked()) {

                    new Runnable() {
                        @Override
                        public void run() {
                            if (groupId.isEmpty()) {
                                for (Group frd : groupList) {
                                    groupId.add(frd.getGroupId());
                                }
                            }
                            permissionList.putString("case_type", "6");
                            permissionList.putIntegerArrayList("groups", groupId);
                            Progress.Cancel();
                            setResult(NewMessageActivity.REQUEST_CODE_FOR_DO_NOT_SHARE,
                                    new Intent().putExtra("permission", permissionList));
                            finish();
                        }
                    }.run();
                } else if (user.isChecked()) {
                    permissionList.putIntegerArrayList("users", userId);
                    permissionList.putString("case_type", "5");
                    Progress.Cancel();
                    setResult(NewMessageActivity.REQUEST_CODE_FOR_DO_NOT_SHARE,
                            new Intent().putExtra("permission", permissionList));
                    finish();
                } else {
                    if (!permissionList.isEmpty()) {
                        permissionList.clear();
                        permissionList = null;
                    } else permissionList = null;
                    Progress.Cancel();
                    setResult(NewMessageActivity.REQUEST_CODE_FOR_DO_NOT_SHARE,
                            new Intent().putExtra("permission", permissionList));
                }

            }
        });
        TextView title = (TextView) findViewById(R.id.text_title_in_bar);
        title.setText(R.string.permission);

    }

    public void cancel(View paramView) {
        finish();
    }

    void data() {
        userList = new ArrayList<>();
        userList.addAll(SearchUser.getAll());
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
                        , Throwable t) {
                    AppLog.toastString("Net Not Available" + t.getMessage(), getApplicationContext());
                    Progress.Cancel();
                }

                public void onResponse(Call<ModelList> callback
                        , Response<ModelList> response) {
                    if (response.body() != null && response.code() == 200) {
                        groupList = response.body().getGroupList();
                        groupList.add(new Group(getString(R.string.friend_group_element)
                                , Integer.parseInt(OBSession.getPreference(getString(R.string.id_key), context)), -10));
                    }
                    if (userList.size() < 1) {
                        body.put(getString(R.string.name_key), "");
                        callRequest = userListRequest.getSearchUserList(body);
                        callRequest.enqueue(new Callback<ModelList>() {
                            public void onFailure(Call<ModelList> callback1
                                    , Throwable t) {
                                AppLog.toastString(t.getMessage(), getApplicationContext());
                                Progress.Cancel();
                            }

                            public void onResponse(Call<ModelList> call
                                    , Response<ModelList> response) {
                                if (response.body() != null && response.code() == 200) {
                                    userList = response.body().getUserList();
                                } else {
                                    AppLog.toastString("Error" + response.code(), getApplicationContext());
                                }
                                Progress.Cancel();
                            }
                        });
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
        final RecyclerView rv;
        rv = (RecyclerView) md.findViewById(R.id.dialog_rv_list);
        rv.setVisibility(View.VISIBLE);
        switch (id) {

            case R.id.user_select_list:
                final SelectionUserAdapter adapter;
                adapter = new SelectionUserAdapter(this, userList);
                rv.setAdapter(adapter);
                rv.setHasFixedSize(true);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (userId.isEmpty()) userId.addAll(adapter.checkedList());
                        else {
                            userId.clear();
                            userId.addAll(adapter.checkedList());
                        }
                        AppLog.logString("onClick: " + userId.toString());
                        user.setChecked(true);
                        group.setChecked(false);
                        check_group_list = false;
                        user.setEnabled(true);
                        dialog.cancel();
                    }
                });
                mb.show();
                break;
            case R.id.group_select_list:
                final SelectionGroupAdapter adapterGroup;
                adapterGroup = new SelectionGroupAdapter(this, groupList);
                rv.setAdapter(adapterGroup);
                rv.setHasFixedSize(true);
                rv.setAnimation(new AnimationSet(true));
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                        check_group_list = true;
                        user.setChecked(false);
                        group.setEnabled(true);
                        dialog.cancel();
                    }
                });
                mb.show();
                break;
        }
    }

}