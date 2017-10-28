package com.example.n_u.officebotapp.activities.permissions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.TagPermissionGroupAdapter;
import com.example.n_u.officebotapp.adapters.TagPermissionUserAdapter;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IGroups;
import com.example.n_u.officebotapp.intefaces.IRequests;
import com.example.n_u.officebotapp.intefaces.ITags;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.ModelList;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagPermissionActivity extends AppCompatActivity {

    private final IGroups groupListRequest = OfficeBotURI.retrofit().create(IGroups.class);
    private final IRequests userListRequest = OfficeBotURI.retrofit().create(IRequests.class);
    Context context = this;
    private ArrayList<Integer[]> groupId = new ArrayList<>(), userId = new ArrayList<>();
    private List<Group> groupList;
    private List<SearchUser> userList;
    private Call<ModelList> callRequest;
    private RadioButton checkedNonePublic, checkedRPublic, checkedRWPublic;
    private CheckBox tagPermissionFriend, tagPermissionGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_permission);
        data();
        setUi();
        setListener();

    }

    private void setListener() {
        checkedNonePublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedRPublic.setChecked(false);
                    checkedRWPublic.setChecked(false);
                } else {
                    checkedNonePublic.setChecked(false);
//                    checkedNonePublic.toggle();
                }

            }
        });
        checkedRPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedNonePublic.setChecked(false);
                    checkedRWPublic.setChecked(false);
                } else {
                    checkedRPublic.setChecked(false);
//                    checkedRPublic.toggle();
                }
            }
        });
        checkedRWPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedRPublic.setChecked(false);
                    checkedNonePublic.setChecked(false);
                } else {
                    checkedRWPublic.setChecked(false);
//                    checkedRWPublic.toggle();
                }
            }
        });


    }

    public void cancelPermission(View view) {
        finish();
    }

    public void setPermissionTag(View view) {
        HashMap<String, Object> map = new HashMap<>();
        ITags iTagPermission = OfficeBotURI.retrofit().create(ITags.class);
        map.put(getString(R.string.tag_id_key), getIntent().getIntExtra(getString(R.string.tag_id_key), 0));
        if (checkedNonePublic.isChecked())
            map.put(getString(R.string.permission_id_key), 4);
        else if (checkedRPublic.isChecked())
            map.put(getString(R.string.permission_id_key), 1);
        else if (checkedRWPublic.isChecked())
            map.put(getString(R.string.permission_id_key), 3);
        if (tagPermissionGroup.isChecked() && tagPermissionFriend.isChecked()) {
            map.put(getString(R.string.permission_case_key), 6);
            map.put(getString(R.string.users_integer_key), userId);
            map.put(getString(R.string.groups_integers_key), groupId);
        } else if (tagPermissionGroup.isChecked()) {
            map.put(getString(R.string.permission_case_key), 5);
            map.put(getString(R.string.groups_integers_key), groupId);
        } else if (tagPermissionFriend.isChecked()) {
            map.put(getString(R.string.permission_case_key), 4);
            map.put(getString(R.string.users_integer_key), userId);
        } else {
            map.put(getString(R.string.permission_case_key), 1);
        }
        AppLog.logString(map.toString());
        Progress.Show(context, "setting Permission..");
        Call<Status> callSetPer = iTagPermission.setPermissionForTag(map);
        callSetPer.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.code() == 200) {
                    AppLog.toastString("Permission Set", getApplicationContext());
                } else {
                    AppLog.toastString(response.message() + response.code(), getApplicationContext());
                }
                Progress.Cancel();
                finish();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                AppLog.toastString(t.getMessage(), getApplicationContext());
                Progress.Cancel();
                finish();
            }
        });

    }

    void data() {
        userList = new ArrayList<>();
        userList.addAll(SearchUser.getAll());
        groupList = new ArrayList<>();
        groupList.addAll(Group.getAll());
        groupList.add(new Group(getString(R.string.friend_group_element)
                , Integer.parseInt(OBSession.getPreference(getString(R.string.id_key), context)), -10));
        if (groupList.size() < 1) {
            final HashMap<String, Object> body = new HashMap<>();
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

    void setUi() {
//        TextView tagGroupSelectList = (TextView) findViewById(R.id.tag_group_select_list);
        tagPermissionGroup = (CheckBox) findViewById(R.id.tag_permission_group);
//        TextView tagFriendSelectList = (TextView) findViewById(R.id.tag_friend_select_list);
        tagPermissionFriend = (CheckBox) findViewById(R.id.tag_permission_friend);
        checkedRWPublic = (RadioButton) findViewById(R.id.checked_r_w_public);
        checkedRPublic = (RadioButton) findViewById(R.id.checked_r_public);
        checkedNonePublic = (RadioButton) findViewById(R.id.checked_none_public);
        findViewById(R.id.backBtnImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.text_title_in_bar);
        title.setText(R.string.permission);
        checkedRPublic.setChecked(true);
        tagPermissionFriend.setEnabled(false);
        tagPermissionGroup.setEnabled(false);
    }

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
        final ListView lv;
        rv = (RecyclerView) md.findViewById(R.id.dialog_rv_list);
        lv = (ListView) md.findViewById(R.id.dialog_lv_for_tag);
        lv.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        switch (id) {

            case R.id.tag_friend_select_list:
                final TagPermissionUserAdapter adapter;
                adapter = new TagPermissionUserAdapter(this, userList);
                lv.setAdapter(adapter);
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                        if (userId.isEmpty()) userId.addAll(adapter.checkedList());
                        else {
                            userId.clear();
                            userId.addAll(adapter.checkedList());
                        }
                        AppLog.logString(Arrays.toString(userId.toArray()));
                        tagPermissionFriend.setChecked(true);
                        tagPermissionFriend.setEnabled(true);
                        dialog.cancel();

                    }
                });
                mb.show();
                break;
            case R.id.tag_group_select_list:
                final TagPermissionGroupAdapter adapterGroup;
                adapterGroup = new TagPermissionGroupAdapter(this, groupList);
                lv.setAdapter(adapterGroup);
                mb.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (groupId.isEmpty()) groupId.addAll(adapterGroup.checkedList());
                        else {
                            groupId.clear();
                            groupId.addAll(adapterGroup.checkedList());
                        }
                        tagPermissionGroup.setChecked(true);
                        tagPermissionGroup.setEnabled(true);
                        dialog.cancel();
                    }
                });
                mb.show();
                break;
        }
    }

}