package com.example.n_u.officebotapp.fragments;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.MessagesAdapter;
import com.example.n_u.officebotapp.intefaces.IMessages;
import com.example.n_u.officebotapp.models.Message;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.models.Tag;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.R.id.empty_view_notes;
import static com.example.n_u.officebotapp.R.id.list_note;
import static com.example.n_u.officebotapp.R.id.post_note_refresh_list;

public class FragmentFriend
        extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final IMessages request = (IMessages) OfficeBotURI.retrofit().create(IMessages.class);
    List<Message> msgList = new ArrayList<>();
    private MessagesAdapter adapter = null;
    private SwingRightInAnimationAdapter animationAdapter = null;
    private ListView lv = null;
    private Call<List<Message>> call = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;

    public static FragmentFriend newInstance(int paramInt) {
        FragmentFriend localFragmentFriend = new FragmentFriend();
        Bundle localBundle = new Bundle();
        localBundle.putInt("section_number", paramInt);
        localFragmentFriend.setArguments(localBundle);
        return localFragmentFriend;
    }

    void data() throws Exception {
        if (InternetConnection.checkConnection(getActivity())) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(getActivity().getString(R.string.tag_id_key), getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0));
            AppLog.logString(map.toString());
            call = request.otherMessages(map);
            if (!call.isExecuted())
                call.enqueue(new Callback<List<Message>>() {
                    public void onFailure(Call<List<Message>> callback
                            , Throwable throwable) {
                        AppLog.logString(throwable.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    public void onResponse(Call<List<Message>> callback
                            , Response<List<Message>> response) {
                        if (response.code() == 200) {
                            msgList = response.body();
//                    adapter.clear();
//                    adapter.addAll(msgList);
//                    AppLog.logString(new Select().from(Message.class).where("user_id != ?",
//                            OBSession.getPreference(getString(R.string.id_key)
//                                    , getActivity())).where("tag_id = ?",
//                            getActivity().getIntent().getStringExtra(getActivity().getString(R.string.tag_id_key))).executeSingle().toString());
                            new Delete().from(Message.class).where("user_id != ?",
                                    OBSession.getPreference(getString(R.string.id_key),
                                            getActivity())).where("tag_id = ?",
                                    getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0)).execute();
                            ActiveAndroid.beginTransaction();
                            try {
                                for (Message temp : response.body()) {
                                    Message item = new Message(temp);
                                    item.save();
                                }
                                ActiveAndroid.setTransactionSuccessful();
                            } finally {
                                ActiveAndroid.endTransaction();
                            }
                            adapter.clear();
                            adapter.addAll(getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0),
                                    Integer.parseInt(OBSession.getPreference(getString(R.string.id_key),
                                            getActivity())));
                            adapter.notifyDataSetChanged();

                            animationAdapter.notifyDataSetChanged();
                        } else {

                            AppLog.toastShortString("Server Offline", getActivity());
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        } else AppLog.toastShortString("Net not Available", getActivity());
    }

    public void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
//        try {
//            if (InternetConnection.checkConnection(getActivity())) {
//                data();
//            } else
//                AppLog.toastString("Net Not Available", getActivity());
//
//        } catch (Exception e) {
//            AppLog.logString(e.getMessage());
//            AppLog.toastString(e.getMessage(), getActivity());
//        }
    }

    public View onCreateView(LayoutInflater lf
            , ViewGroup viewGroup
            , Bundle bundle) {
        View view = lf.inflate(R.layout.tag_main_fragement_messages, viewGroup, false);
        lv = ((DynamicListView) view.findViewById(list_note));
        swipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(post_note_refresh_list));
        adapter = new MessagesAdapter(getActivity(), this.msgList, getChildFragmentManager());
        animationAdapter = new SwingRightInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(this.lv);
        lv.setAdapter(animationAdapter);
        if (getActivity().getIntent().getStringExtra(getString(R.string.history)) == null
                || !getActivity().getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history))) {
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View listener, int position, long id) {
////                    Intent i = new Intent(getActivity(), ReplyActivity.class);
////                    Bundle bundle = new Bundle();
////                    Message message = adapter.getItem(position);
////                    bundle.putString(getActivity().getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
////                    bundle.putString(getActivity().getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
////                    bundle.putString(getActivity().getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
////                    bundle.putString(getActivity().getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
////                    i.putExtra(getActivity().getString(R.string.MSG_KEY), bundle);
////                    getActivity().startActivity(i);
//                }
//            });
            swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    try {
                        data();
                    } catch (Exception localException) {
                        AppLog.logString(localException.getMessage());
                        AppLog.toastString(localException.getMessage(), getActivity());
                    }
                }
            });
            swipeRefreshLayout.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View paramAnonymousView) {
                    swipeRefreshLayout.setRefreshing(true);
                    try {
                        data();
                        return false;
                    } catch (Exception e) {
                        AppLog.logString(e.getMessage());
                        AppLog.toastString(e.getMessage(), getActivity());
                    }
                    return false;
                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @TargetApi (Build.VERSION_CODES.KITKAT)
                public boolean onItemLongClick(AdapterView<?> parent, final View listener, final int position, long id) {
                    if (Objects.equals(adapter.getItem(position).getUser_id()
                            , Integer.parseInt(OBSession.getPreference(getActivity().getString(R.string.id_key)
                                    , getActivity())))
                            || Objects.equals(Integer.parseInt(OBSession.getPreference(getActivity().getString(R.string.id_key)
                            , getActivity()))
                            , Tag.getUserOwnerId(adapter.getItem(position).getTag_id()))) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                        final HashMap<String, Object> map = new HashMap<>();
                        final IMessages localIMessages = OfficeBotURI.retrofit().create(IMessages.class);
                        ab.setMessage("Are you sure, You wanted to delete this?");
                        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int paramAnonymous2Int) {
                                map.put(getActivity().getString(R.string.message_id_key), adapter.getItem(position).getMsgId());

                                localIMessages.deleteMessage(map).enqueue(new Callback<Status>() {
                                    public void onFailure(Call<Status> callback, Throwable throwable) {
                                        Toast.makeText(getActivity()
                                                , throwable.getMessage()
                                                , Toast.LENGTH_LONG).show();
                                    }

                                    public void onResponse(Call<Status> callback
                                            , Response<Status> response) {
                                        if (response.code() == 200) {
                                            Toast.makeText(getActivity()
                                                    , "" + response.body().getMessage()
                                                    , Toast.LENGTH_SHORT).show();
                                            adapter.remove(position);
                                            adapter.notifyDataSetChanged();

                                        } else
                                            Toast.makeText(getActivity()
                                                    , "" + response.errorBody()
                                                    , Toast.LENGTH_LONG).show();
                                    }
                                });
                                dialogInterface.cancel();
                            }
                        });
                        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int paramAnonymous2Int) {
                                dialogInterface.cancel();
                            }
                        });
                        ab.create().show();
                    } else {
                        Toast.makeText(getActivity()
                                , "Your have no right to delete this message"
                                , Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
        lv.setEmptyView(view.findViewById(empty_view_notes));
        if (this.adapter.getCount() > 0) {
        }
        viewGroup = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.footer, this.lv, false);
        lv.addFooterView(viewGroup, null, false);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (call != null) {
            if (call.isExecuted()) {

                call.cancel();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            swipeRefreshLayout.performLongClick();
            adapter.refresh(getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0),
                    Integer.parseInt(OBSession.getPreference(getString(R.string.id_key),
                            getActivity())));
        } catch (Exception localException) {
            AppLog.logString(localException.getMessage());
        }

//        try {
//            if (InternetConnection.checkConnection(getActivity())) {
//                data();
//
//            } else
//                AppLog.toastString("Net Not Available", getActivity());
//        } catch (Exception e) {
//            AppLog.logString(e.getMessage());
//            AppLog.toastString(e.getMessage(), getActivity());
//        }
    }
}
