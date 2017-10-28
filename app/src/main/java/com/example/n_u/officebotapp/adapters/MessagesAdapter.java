package com.example.n_u.officebotapp.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IMessages;
import com.example.n_u.officebotapp.models.Message;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.models.Tag;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.viewsholders.TagMessageHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Callback;

public class MessagesAdapter
        extends BaseAdapter {
    private final Activity context;
    private List<Message> adapterData = new ArrayList<>();
    private FragmentManager fm;

    public MessagesAdapter(Activity paramActivity) {
        this.context = paramActivity;
    }

    public MessagesAdapter(Activity paramActivity, List<Message> paramList, FragmentManager fm) {
        this.context = paramActivity;
        this.adapterData = paramList;
        this.fm = fm;
    }

    public void addAll(List<Message> paramList) {
        this.adapterData.addAll(paramList);
        notifyDataSetChanged();
    }

    public void refresh(int id) {
        clear();
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Message.getAll(id, String.valueOf(OBSession.getPreference(context.getString(R.string.id_key),
                        context))));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(context, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void addAll(int id) {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Message.getAll(id, String.valueOf(OBSession.getPreference(context.getString(R.string.id_key),
                        context))));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(context, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void refresh(int id, int t_id) {
        clear();
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Message.getAll(id, t_id));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(context, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void refresh(int tag_id, int user_id, boolean b) {
        clear();
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Message.getAllOtherTag(tag_id, user_id));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(context, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void addAll(int tag_id, int user_id) {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Message.getAll(tag_id, user_id));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(context, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void addAll(int tag_id, int user_id, boolean b) {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Message.getAllOtherTag(tag_id, user_id));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(context, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void clear() {
        if (!this.adapterData.isEmpty()) {
            this.adapterData.clear();
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        if (adapterData != null) return adapterData.size();
        else return 0;
    }

    public Message getItem(int position) {
        return adapterData.get(position);
    }

    public long getItemId(int position) {
        return adapterData.get(position).getMsgId();
    }

    @TargetApi (Build.VERSION_CODES.KITKAT)
    @NonNull
    public View getView(final int position
            , View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final TagMessageHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_message, null);
            holder = new TagMessageHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (TagMessageHolder) convertView.getTag();
        }
        holder.getTagdelmsg().setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (Objects.equals(getItem(position).getUser_id()
                        , Integer.parseInt(OBSession.getPreference(context.getString(R.string.id_key)
                                , context)))
                        || Objects.equals(Integer.parseInt(OBSession.getPreference(context.getString(R.string.id_key)
                        , context))
                        , Tag.getUserOwnerId(getItem(position).getTag_id()))) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(context);
                    final HashMap<String, Object> map = new java.util.HashMap<>();
                    final IMessages localIMessages = OfficeBotURI.retrofit().create(IMessages.class);
                    ab.setMessage("Are you sure, You wanted to delete this?");
                    ab.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(android.content.DialogInterface dialogInterface, int paramAnonymous2Int) {
                            Progress.Show(context, "Deleting...");
                            map.put(context.getString(R.string.message_id_key), getItem(position).getMsgId());
                            localIMessages.deleteMessage(map).enqueue(new Callback<Status>() {
                                public void onFailure(retrofit2.Call<Status> callback, Throwable throwable) {
                                    Toast.makeText(context
                                            , throwable.getMessage()
                                            , Toast.LENGTH_LONG).show();
                                    Progress.Cancel();
                                }

                                public void onResponse(retrofit2.Call<Status> callback
                                        , retrofit2.Response<Status> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(context
                                                , "" + response.body().getMessage()
                                                , Toast.LENGTH_SHORT).show();
                                        remove(position);
                                        notifyDataSetChanged();

                                    } else
                                        Toast.makeText(context
                                                , "" + response.errorBody()
                                                , Toast.LENGTH_LONG).show();
                                    Progress.Cancel();
                                }
                            });
                            dialogInterface.cancel();
                        }
                    });
                    ab.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(android.content.DialogInterface dialogInterface, int paramAnonymous2Int) {
                            dialogInterface.cancel();
                        }
                    });
                    ab.create().show();
                } else {
                    Toast.makeText(context
                            , "Your have no right to delete this message"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.getTagmessagecontent().setText(adapterData.get(position).getContent());
        if (holder.getTagmessagecontent().getText().length() > 15) {
            holder.getTagmessagecontent().setTextSize(2, 8.0F);
            StringBuilder st = new StringBuilder(adapterData.get(position).getContent());
            st = st.delete(7, st.length() - 1);
            st.append("....");
            holder.getTagmessagecontent().setText(st);
        }
        holder.getMsgParentLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = getItem(position);
                if (message.getMsgId() > 0) {
                    Intent i = new Intent(context, com.example.n_u.officebotapp.activities.ReplyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(context.getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
                    bundle.putString(context.getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
                    bundle.putString(context.getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
                    bundle.putString(context.getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
                    i.putExtra(context.getString(R.string.MSG_KEY), bundle);
                    context.startActivity(i);
                } else {
                    AppLog.toastShortString("Note is not posted yet", context);
                }
            }
        });

//        holder.getTagmessagetime().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Message message = getItem(position);
//                if (message.getMsgId() > 0) {
//                    Intent i = new Intent(context, ReplyActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(context.getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
//                    bundle.putString(context.getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
//                    bundle.putString(context.getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
//                    bundle.putString(context.getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
//                    i.putExtra(context.getString(R.string.MSG_KEY), bundle);
//                    context.startActivity(i);
//                } else {
//                    AppLog.toastShortString("Note is not posted yet", context);
//                }
//            }
//        });
//        holder.getTagmessagecontent().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Message message = getItem(position);
//                if (message.getMsgId() > 0) {
//                    Intent i = new Intent(context, ReplyActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(context.getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
//                    bundle.putString(context.getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
//                    bundle.putString(context.getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
//                    bundle.putString(context.getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
//                    i.putExtra(context.getString(R.string.MSG_KEY), bundle);
//                    context.startActivity(i);
//                } else {
//                    AppLog.toastShortString("Note is not posted yet", context);
//                }
//            }
//        });
//        holder.getMessageuser().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Message message = getItem(position);
//                if (message.getMsgId() > 0) {
//                    Intent i = new Intent(context, ReplyActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(context.getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
//                    bundle.putString(context.getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
//                    bundle.putString(context.getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
//                    bundle.putString(context.getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
//                    i.putExtra(context.getString(R.string.MSG_KEY), bundle);
//                    context.startActivity(i);
//                } else {
//                    AppLog.toastShortString("Note is not posted yet", context);
//                }
//            }
//        });
//        holder.getTagreplytotal().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Message message = getItem(position);
//                if (message.getMsgId() > 0) {
//                    Intent i = new Intent(context, ReplyActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(context.getString(R.string.MSG_ID_BUNDLE), String.valueOf(message.getMsgId()));
//                    bundle.putString(context.getString(R.string.MSG_CONTENT_BUNDLE_KEY), message.getContent());
//                    bundle.putString(context.getString(R.string.MSG_TIME_BUNDLE_KEY), message.getCreated_at());
//                    bundle.putString(context.getString(R.string.MSG_OWNER_BUNDLE_KEY), String.valueOf(message.getUser_id()));
//                    i.putExtra(context.getString(R.string.MSG_KEY), bundle);
//                    context.startActivity(i);
//                } else {
//                    AppLog.toastShortString("Note is not posted yet", context);
//                }
//            }
//        });
        holder.getTagmessagetime().setText(AppLog.getPretty(adapterData.get(position).getUpdated_at(), context));
        if (Objects.equals(Integer.parseInt(OBSession.getPreference("id", context)), adapterData.get(position).getUser_id())) {
            holder.getMessageuser().setText(OBSession.getPreference("name", context));
        } else
            holder.getMessageuser().setText(String.valueOf(SearchUser.getNameUserOther(adapterData.get(position).getUser_id())));
        if (holder.getMessageuser().getText().length() > 8) {
            holder.getMessageuser().setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
        if (adapterData.get(position).getMsgId() > 0)
            if (Integer.parseInt(OBSession.getPreference("id", context)) == adapterData.get(position).getUser_id()) {
                holder.getTagreplytotal().setText(String.valueOf(adapterData.get(position).getReply_count()));
            } else {
                holder.getTagreplytotal().setVisibility(android.view.View.INVISIBLE);
                holder.getTagdelmsg().setVisibility(android.view.View.INVISIBLE);
            }
        else
            holder.getTagreplytotal().setBackground(context.getResources().getDrawable(R.drawable.zzz_timer_sand));
        String st = "no.jpg";
        try {
            if (adapterData.get(position).getUser_id() ==
                    Integer.parseInt(OBSession.getPreference(context.getString(R.string.id_key), context))) {
                st = OBSession.getPreference(context.getString(R.string.image_src_key)
                        , context);
            } else if (SearchUser.imagePath(adapterData
                    .get(position)
                    .getUser_id())
                    .getImage_src() == null)
                st = "no.jpg";
            else st = SearchUser.imagePath(adapterData
                        .get(position)
                        .getUser_id())
                        .getImage_src();
        } catch (RuntimeException e) {
            AppLog.logString(e.getMessage());
        }
        holder.getProfileimage().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (adapterData.get(position).getUser_id() !=
                        Integer.parseInt(OBSession.getPreference(context.getString(R.string.id_key), context))) {
                    SearchUser su = SearchUser.getUserOther(adapterData.get(position).getUser_id());
                    if (su.getPhone() != null) {
                        ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(su.getName()
                                , su.getEmail(), String.valueOf(su.getPhone()), su.getImage_src());

                        editNameDialogFragment.show(fm, "fragment_edit_name");
                    } else {
                        ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(su.getName()
                                , su.getEmail(), su.getImage_src());
                        editNameDialogFragment.show(fm, "fragment_edit_name");
                    }
                } else {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(OBSession.getPreference(context.getString(R.string.name_key)
                            , context)
                            , OBSession.getPreference(context.getString(R.string.email_key)
                                    , context)
                            , OBSession.getPreference(context.getString(R.string.phone_key)
                                    , context)
                            , OBSession.getPreference(context.getString(R.string.image_src_key)
                                    , context));
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
                AppLog.setVibrate(context, AppLog.INTENSITY_MIDDLE);
            }
        });
        Glide.with(holder.getProfileimage().getContext())
                .load(OfficeBotURI.getFileUrlPreFix() + st)
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .skipMemoryCache(false).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .into(holder.getProfileimage());
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void remove(int i) {
        adapterData.remove(i);
    }
}
