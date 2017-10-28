package com.example.n_u.officebotapp.fragments;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
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

public class FragmentYours
        extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final IMessages request = (IMessages) OfficeBotURI.retrofit().create(IMessages.class);
    List<Message> msgList = new ArrayList<>();
    private MessagesAdapter adapter = null;
    private SwingLeftInAnimationAdapter animationAdapter = null;
    private Call<List<Message>> call = null;
    private ListView lv = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;

    public static FragmentYours newInstance(int paramInt) {
        FragmentYours localFragmentYours = new FragmentYours();
        Bundle localBundle = new Bundle();
        localBundle.putInt("section_number", paramInt);
        localFragmentYours.setArguments(localBundle);
        return localFragmentYours;
    }

    void data() throws IllegalStateException {
        if (InternetConnection.checkConnection(getActivity())) {
            try {
                HashMap<String, Object> map = new HashMap<>();
                map.put(getActivity().getString(R.string.user_id_key), Integer.parseInt(OBSession.getPreference(getString(R.string.id_key), getActivity())));
                map.put(getActivity().getString(R.string.tag_id_key), getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0));
                AppLog.logString(map.toString());
                call = request.getMessages(map);
                if (!call.isExecuted())
                    call.enqueue(new Callback<List<Message>>() {
                        public void onFailure(Call<List<Message>> callback
                                , Throwable throwable) {
                            AppLog.logString(throwable.getMessage());
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        public void onResponse(Call<List<Message>> callback
                                , Response<List<Message>> response) {
                            try {
                                if (response.code() == 200) {
                                    msgList = response.body();
//                    adapter.clear();
//                    adapter.addAll(msgList);
                                    new Delete().from(Message.class).where("user_id = ?",
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
                                    adapter.addAll(getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0));
                                    adapter.notifyDataSetChanged();

                                    animationAdapter.notifyDataSetChanged();
                                } else {

                                    AppLog.toastShortString("Server Offline", getActivity());
                                }
                                swipeRefreshLayout.setRefreshing(false);
                            } catch (IllegalStateException e) {
                                AppLog.logString(e.getMessage());
                                AppLog.toastString(e.getMessage(), getActivity());
                            }
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    });
            } catch (IllegalStateException e) {
                AppLog.logString(e.getMessage());
                AppLog.toastString(e.getMessage(), getActivity());
            }
        } else AppLog.toastShortString("Net not Available", getActivity());
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater lf
            , ViewGroup viewGroup
            , Bundle bundle) {
        View layoutInflater = lf.inflate(R.layout.tag_main_fragement_messages, viewGroup, false);
        lv = ((DynamicListView) layoutInflater.findViewById(list_note));
        swipeRefreshLayout = ((SwipeRefreshLayout) layoutInflater.findViewById(post_note_refresh_list));
        adapter = new MessagesAdapter(getActivity(), this.msgList, getChildFragmentManager());
        this.animationAdapter = new SwingLeftInAnimationAdapter(this.adapter);
        animationAdapter.setAbsListView(this.lv);
        lv.setAdapter(this.animationAdapter);
        if (getActivity().getIntent().getStringExtra(getString(R.string.history)) == null
                || !getActivity().getIntent().getStringExtra(getString(R.string.history)).equals(getString(R.string.history))) {
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View listener, int position, long id) {
////                    Message message = adapter.getItem(position);
////                    if (message.getMsgId() > 0) {
////                        Intent i = new Intent(getActivity(), ReplyActivity.class);
////                        Bundle bundle = new Bundle();
////                        bundle.putString(getActivity().getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
////                        bundle.putString(getActivity().getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
////                        bundle.putString(getActivity().getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
////                        bundle.putString(getActivity().getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
////                        i.putExtra(getActivity().getString(R.string.MSG_KEY), bundle);
////                        getActivity().startActivity(i);
////                    } else {
////                        AppLog.toastShortString("Note is not posted yet", getActivity());
////                    }
//                }
//            });
            swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    try {
                        data();
                    } catch (IllegalStateException e) {
                        AppLog.logString(e.getMessage());
                        AppLog.toastString(e.getMessage(), getActivity());
                    }
                }
            });
            swipeRefreshLayout.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View l) {
                    swipeRefreshLayout.setRefreshing(true);
                    try {
                        data();
                        return false;
                    } catch (IllegalStateException e) {
                        AppLog.logString(e.getMessage());
                        AppLog.toastString(e.getMessage(), getActivity());
                    }
                    return false;
                }
            });
            lv.setOnItemLongClickListener(new OnItemLongClickListener() {
                @TargetApi (Build.VERSION_CODES.KITKAT)
                public boolean onItemLongClick(AdapterView<?> parent, final View listener, final int position, long id) {
                    if (Objects.equals(adapter.getItem(position).getUser_id(), Integer.parseInt(OBSession.getPreference(getActivity().getString(R.string.id_key), getActivity())))) {
                        Builder ab = new Builder(getActivity());
                        final HashMap<String, Object> map = new HashMap<>();
                        final IMessages iMessages = OfficeBotURI.retrofit().create(IMessages.class);
                        ab.setMessage("Are you sure, You wanted to delete this?");
                        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int paramAnonymous2Int) {
                                map.put(getActivity().getString(R.string.message_id_key), adapter.getItem(position).getMsgId());

                                iMessages.deleteMessage(map).enqueue(new Callback<Status>() {
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
                                            new Delete().from(Message.class).where("message_id =?"
                                                    , adapter.getItem(position).getMsgId())
                                                    .where("tag_id =?"
                                                            , getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0)).execute();
                                            adapter.remove(position);
                                            adapter.notifyDataSetChanged();
                                            Tag.setTagTotalMsg(getActivity().getIntent().getIntExtra(getString(R.string.tag_id_key), 0)
                                                    , (getActivity().getIntent().getIntExtra(getString(R.string.total_msg), 0) - 1));

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
        lv.setEmptyView(layoutInflater.findViewById(empty_view_notes));
        if (this.adapter.getCount() > 0) {

        }
        viewGroup = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.footer, this.lv, false);
        lv.addFooterView(viewGroup, null, false);

        return layoutInflater;
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
            adapter.refresh(getActivity().getIntent().getIntExtra(getActivity().getString(R.string.tag_id_key), 0));
            super.onResume();
        } catch (Exception localException) {
            AppLog.logString(localException.getMessage());
        }
    }
}