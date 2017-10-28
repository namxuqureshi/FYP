package com.example.n_u.officebotapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.intefaces.IStatus;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.viewsholders.RequestFriendHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFriendAdapter
        extends RecyclerView.Adapter<RequestFriendHolder> {
    private final HashMap<String, Object> body = new HashMap<>();
    private final IStatus request = (IStatus) OfficeBotURI.retrofit().create(IStatus.class);
    private Call<Status> call = null;
    private List<SearchUser> filterData;
    private List<SearchUser> adapterData;
    private FragmentManager fm;
    private Context mContext;

    public RequestFriendAdapter(Context paramContext, List<SearchUser> users, FragmentManager fm) {
        this.adapterData = users;
        this.filterData = new ArrayList<>();
        this.filterData.addAll(this.adapterData);
        this.mContext = paramContext;
        this.fm = fm;
    }

    public void addAll(List<SearchUser> paramList) {
        this.adapterData = paramList;
        this.filterData.addAll(paramList);
        notifyDataSetChanged();
    }

    public void clear() {
        if ((!this.filterData.isEmpty()) && (!this.adapterData.isEmpty())) {
            this.filterData.clear();
            this.adapterData.clear();
        }
        notifyDataSetChanged();
    }

    public void addAll() {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(SearchUser.getAll());
                filterData.addAll(adapterData);
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(mContext, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterData.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterData.addAll(adapterData);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (SearchUser item : adapterData) {
                        if (item.getName().toLowerCase().contains(text.toLowerCase())
//                                || item.place.toLowerCase().contains(text.toLowerCase())
                                ) {
                            // Adding Matched items
                            filterData.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public int getItemCount() {
        if (this.filterData != null) {
            return this.filterData.size();
        }
        return 0;
    }

    public void onBindViewHolder(final RequestFriendHolder holder
            , final int position) {
        final int pos = holder.getAdapterPosition();
        TextView textView = holder.getNameTextView();
        final TextView msg = holder.getMsg();
        final Button btn_send = holder.getSendBtn();
        if ((filterData.get(holder.getAdapterPosition()).isRequest())
                && (this.filterData.get(holder.getAdapterPosition()).isFriends())) {
            msg.setVisibility(View.VISIBLE);
            msg.setText("Already Friend");
            btn_send.setVisibility(View.GONE);
        } else {
            if ((this.filterData.get(holder.getAdapterPosition()).isRequest())
                    && (!this.filterData.get(holder.getAdapterPosition()).isFriends())) {
                msg.setVisibility(View.VISIBLE);
                msg.setText("Request Send");
                btn_send.setVisibility(View.GONE);
            } else {
                msg.setVisibility(View.GONE);
                btn_send.setVisibility(View.VISIBLE);
                btn_send.setOnClickListener(new OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                        body.put(mContext.getString(R.string.user_id_key), OBSession.getPreference(mContext.getString(R.string.id_key), mContext));
                        body.put(mContext.getString(R.string.user2_id_key),
                                filterData.get(holder.getAdapterPosition()).getSearch_user_id());
                        call = request.getSendRequestStatus(body);
                        btn_send.setVisibility(View.GONE);
                        msg.setVisibility(View.VISIBLE);
                        msg.setText("Request Send");
                        call.enqueue(new Callback<Status>() {
                            public void onFailure(Call<Status> callback,
                                                  Throwable throwable) {
                                Toast.makeText(mContext,
                                        throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            public void onResponse(Call<Status> callback,
                                                   Response<Status> response) {
                                if (response.body().isRequest()) {
                                    filterData.get(pos).setRequest(true);
                                    Toast.makeText(RequestFriendAdapter.this.mContext,
                                            "Request Send Successfully",
                                            Toast.LENGTH_LONG).show();

                                } else
                                    Toast.makeText(RequestFriendAdapter.this.mContext,
                                            "Request Send UnSuccessfully",
                                            Toast.LENGTH_SHORT).show();
                                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                            }
                        });
                    }
                });
            }
        }
        textView.setText(filterData.get(holder.getAdapterPosition()).getName());
//        textView.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                if (filterData.get(pos).getPhone() != null) {
//                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(filterData.get(pos).getName()
//                            , filterData.get(pos).getEmail()
//                            , String.valueOf(filterData.get(pos).getPhone())
//                            , filterData.get(pos).getImage_src());
//
//                    editNameDialogFragment.show(fm, "fragment_edit_name");
//                } else {
//                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(filterData.get(pos).getName()
//                            , filterData.get(pos).getEmail()
//                            , filterData.get(pos).getImage_src());
//                    editNameDialogFragment.show(fm, "fragment_edit_name");
//                }
//                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
//            }
//        });
        holder.getBackgroundView().setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (filterData.get(pos).getPhone() != null) {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(filterData.get(pos).getName()
                            , filterData.get(pos).getEmail()
                            , String.valueOf(filterData.get(pos).getPhone())
                            , filterData.get(pos).getImage_src());

                    editNameDialogFragment.show(fm, "fragment_edit_name");
                } else {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(filterData.get(pos).getName()
                            , filterData.get(pos).getEmail()
                            , filterData.get(pos).getImage_src());
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
            }
        });

        Glide.with(holder.getRequestCircles().getContext())
                .load(OfficeBotURI.getFileUrlPreFix() + filterData.get(holder.getAdapterPosition()).getImage_src())
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .fitCenter().fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .skipMemoryCache(false).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .into(holder.getRequestCircles());
    }

    public RequestFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RequestFriendHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_friend_request_list, parent, false));
    }
}
