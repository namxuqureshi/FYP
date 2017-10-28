package com.example.n_u.officebotapp.fragments;

import android.content.DialogInterface;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.MessagesAdapter;
import com.example.n_u.officebotapp.intefaces.IMessages;
import com.example.n_u.officebotapp.models.Message;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.HashMap;
import java.util.List;

import static android.support.v7.app.AlertDialog.Builder;
import static android.widget.AdapterView.OnItemLongClickListener;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.activeandroid.ActiveAndroid.beginTransaction;
import static com.activeandroid.ActiveAndroid.endTransaction;
import static com.activeandroid.ActiveAndroid.setTransactionSuccessful;
import static com.example.n_u.officebotapp.R.id.empty_view_notes;
import static com.example.n_u.officebotapp.R.id.list_note;
import static com.example.n_u.officebotapp.R.id.post_note_refresh_list;
import static com.example.n_u.officebotapp.models.Tag.getUserOwnerId;
import static com.example.n_u.officebotapp.utils.InternetConnection.checkConnection;
import static com.example.n_u.officebotapp.utils.OBSession.getPreference;
import static com.example.n_u.officebotapp.utils.OfficeBotURI.retrofit;

/**
 * Created by usman on 13-Jun-17.
 */

public class FragmentYoursNoteOtherTag extends android.support.v4.app.Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final IMessages request = (IMessages) retrofit().create(IMessages.class);
    List<Message> msgList = new java.util.ArrayList<>();
    private MessagesAdapter adapter = null;
    private SwingRightInAnimationAdapter animationAdapter = null;
    private android.widget.ListView lv = null;
    private retrofit2.Call<List<Message>> call = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;

    public static FragmentYoursNoteOtherTag newInstance(int paramInt) {
        FragmentYoursNoteOtherTag localFragmentFriend = new FragmentYoursNoteOtherTag();
        Bundle localBundle = new Bundle();
        localBundle.putInt("section_number", paramInt);
        localFragmentFriend.setArguments(localBundle);
        return localFragmentFriend;
    }

    void data() throws Exception {
        if (InternetConnection.checkConnection(getActivity())) {
            java.util.HashMap<String, Object> map = new HashMap<>();
            map.put(getActivity().getString(R.string.tag_id_key), getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0));
            AppLog.logString(map.toString());
            call = request.otherMessages(map);
            if (!call.isExecuted())
                call.enqueue(new retrofit2.Callback<List<Message>>() {
                    public void onFailure(retrofit2.Call<List<Message>> callback
                            , Throwable throwable) {
                        AppLog.logString(throwable.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    public void onResponse(retrofit2.Call<List<Message>> callback
                            , retrofit2.Response<List<Message>> response) {
                        AppLog.logString(response.code());
                        if (response.code() == 200) {
                            msgList = response.body();
                            AppLog.logString(response.body().toString());
                            new com.activeandroid.query.Delete().from(Message.class)
                                    .where("user_id = ?", getPreference(getString(R.string.id_key),
                                            getActivity()))
                                    .where("tag_id = ?",
                                            getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0)).execute();
                            beginTransaction();
                            try {
                                for (Message temp : response.body()) {
                                    Message item = new Message(temp);
                                    item.save();
                                }
                                setTransactionSuccessful();
                            } finally {
                                endTransaction();
                            }
                            adapter.clear();
//                    adapter = new MessagesAdapter(getActivity(), msgList, getChildFragmentManager());
                            adapter.addAll(getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0),
                                    Integer.parseInt(getPreference(getString(R.string.id_key),
                                            getActivity())), false);
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
        try {
            if (checkConnection(getActivity())) {
                data();
            } else
                AppLog.toastString("Net Not Available", getActivity());

        } catch (Exception e) {
            AppLog.logString(e.getMessage());
            AppLog.toastString(e.getMessage(), getActivity());
        }
    }

    public android.view.View onCreateView(android.view.LayoutInflater lf
            , android.view.ViewGroup viewGroup
            , Bundle bundle) {
        android.view.View view = lf.inflate(R.layout.tag_main_fragement_messages, viewGroup, false);
        lv = ((DynamicListView) view.findViewById(list_note));
        swipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(post_note_refresh_list));
        adapter = new MessagesAdapter(getActivity(), this.msgList, getChildFragmentManager());
        animationAdapter = new SwingRightInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(this.lv);
        lv.setAdapter(animationAdapter);
        if (getActivity().getIntent().getStringExtra(getString(R.string.history)) == null
                || !getActivity().getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history))) {
//            lv.setOnItemClickListener(new OnItemClickListener() {
//                public void onItemClick(android.widget.AdapterView<?> parent, android.view.View listener, int position, long id) {
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
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                public void onRefresh() {
                    try {
                        data();
                    } catch (Exception localException) {
                        AppLog.logString(localException.getMessage());
                        AppLog.toastString(localException.getMessage(), getActivity());
                    }
                }
            });
            swipeRefreshLayout.setOnLongClickListener(new android.view.View.OnLongClickListener() {
                public boolean onLongClick(android.view.View v) {
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
            lv.setOnItemLongClickListener(new OnItemLongClickListener() {
                @android.annotation.TargetApi (VERSION_CODES.KITKAT)
                public boolean onItemLongClick(android.widget.AdapterView<?> parent, final android.view.View listener, final int position, long id) {
                    if (java.util.Objects.equals(adapter.getItem(position).getUser_id()
                            , Integer.parseInt(getPreference(getActivity().getString(R.string.id_key)
                                    , getActivity())))
                            || java.util.Objects.equals(Integer.parseInt(getPreference(getActivity().getString(R.string.id_key)
                            , getActivity()))
                            , getUserOwnerId(adapter.getItem(position).getTag_id()))) {
                        Builder ab = new Builder(getActivity());
                        final java.util.HashMap<String, Object> map = new HashMap<>();
                        final IMessages localIMessages = retrofit().create(IMessages.class);
                        ab.setMessage("Are you sure, You wanted to delete this?");
                        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int paramAnonymous2Int) {
                                map.put(getActivity().getString(R.string.message_id_key), adapter.getItem(position).getMsgId());

                                localIMessages.deleteMessage(map).enqueue(new retrofit2.Callback<com.example.n_u.officebotapp.models.Status>() {
                                    public void onFailure(retrofit2.Call<com.example.n_u.officebotapp.models.Status> callback, Throwable throwable) {
                                        makeText(getActivity()
                                                , throwable.getMessage()
                                                , LENGTH_LONG).show();
                                    }

                                    public void onResponse(retrofit2.Call<com.example.n_u.officebotapp.models.Status> callback
                                            , retrofit2.Response<com.example.n_u.officebotapp.models.Status> response) {
                                        if (response.code() == 200) {
                                            makeText(getActivity()
                                                    , "" + response.body().getMessage()
                                                    , LENGTH_SHORT).show();
                                            adapter.remove(position);
                                            adapter.notifyDataSetChanged();

                                        } else
                                            makeText(getActivity()
                                                    , "" + response.errorBody()
                                                    , LENGTH_LONG).show();
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
                        makeText(getActivity()
                                , "Your have no right to delete this message"
                                , LENGTH_LONG).show();
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
        viewGroup = (android.view.ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.footer, this.lv, false);
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
//            adapter.refresh(getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0),
//                    Integer.parseInt(getPreference(getString(R.string.id_key),
//                            getActivity())), false);
        } catch (Exception localException) {
            AppLog.logString(localException.getMessage());
        }

    }
}
