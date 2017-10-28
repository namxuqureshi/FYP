package com.example.n_u.officebotapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.intefaces.IStatus;
import com.example.n_u.officebotapp.models.Request;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.services.UserListService;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestListAdapter
        extends BaseAdapter {
    private final Context mContext;
    private List<Request> adapterData = new ArrayList<>();
    private FragmentManager fm;

    public RequestListAdapter(Activity paramActivity, List<Request> paramList, FragmentManager fm) {
        this.mContext = paramActivity;
        this.fm = fm;
        this.adapterData = paramList;
    }

    public void addAll(List<Request> paramList) {
        this.adapterData.addAll(paramList);
        AppLog.logString("addAll: " + this.adapterData.size());
        notifyDataSetChanged();
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

    public Request getItem(int paramInt) {
        return this.adapterData.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return adapterData.get(paramInt).getRequestId();
    }

    @NonNull
    public View getView(final int position,
                        View view,
                        @NonNull ViewGroup parent) {
        LayoutInflater from = LayoutInflater.from(mContext);
        View rowView = view;
        if (view == null) {
            rowView = from.inflate(R.layout.custom_requests, parent, false);
        }
        CircleImageView imageView = (CircleImageView) rowView.findViewById(R.id.request_list_circle);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.request_list_name);
        Button btn_block = (Button) rowView.findViewById(R.id.block);
        btn_block.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                IStatus user = OfficeBotURI.retrofit().create(IStatus.class);
                HashMap<String, Object> map = new HashMap<>();
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                map.put(mContext.getString(R.string.request_id_key), String.valueOf(getItemId(position)));
                retrofit2.Call<Status> call = user.verifyRequestBlock(map);
                Snackbar.make(paramAnonymousView, R.string.block_request, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                call.enqueue(new Callback<Status>() {
                    public void onFailure(Call<Status> callback, Throwable throwable) {
                        Toast.makeText(mContext, throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == 200) {
                            Toast.makeText(RequestListAdapter.this.mContext,
                                    "Request Blocked", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        notifyDataSetChanged();
                    }
                });
                adapterData.remove(getItem(position));
                notifyDataSetChanged();
            }
        });
        Button btn_add = (Button) rowView.findViewById(R.id.add);
        btn_add.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                IStatus localIStatus = OfficeBotURI.retrofit().create(IStatus.class);
                HashMap<String, Object> localHashMap = new HashMap<>();
                Snackbar.make(paramAnonymousView, R.string.accept_request, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                localHashMap.put(mContext.getString(R.string.request_id_key), String.valueOf(getItemId(position)));
                retrofit2.Call<Status> call = localIStatus.verifyRequestAccept(localHashMap);
                call.enqueue(new Callback<Status>() {
                    public void onFailure(Call<Status> call, Throwable throwable) {
                        Toast.makeText(RequestListAdapter.this.mContext,
                                throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    public void onResponse(Call<Status> call, Response<Status> response) {
                        if (response.code() == 200) {
                            Toast.makeText(mContext,
                                    R.string.accept_request, Toast.LENGTH_LONG).show();
                            mContext.startService(new android.content.Intent(mContext
                                    , UserListService.class));
                        } else {

                            Toast.makeText(mContext,
                                    response.code() + "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        RequestListAdapter.this.notifyDataSetChanged();
                    }
                });
                adapterData.remove(RequestListAdapter.this.getItem(position));
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                notifyDataSetChanged();
            }
        });
        TextView tv_time = (TextView) rowView.findViewById(R.id.received_time);
        tv_time.setText(AppLog.getPretty(adapterData.get(position).getUpdated_at(), mContext));
        txtTitle.setText(adapterData.get(position).getName());
        final SearchUser user = SearchUser.imagePath(adapterData
                .get(position)
                .getRequestUserId());
        String st = "no.jpg";
        try {
            if (user.getImage_src() == null)
                st = "no.jpg";
            else st = user.getImage_src();
        } catch (RuntimeException e) {
            AppLog.logString(e.getMessage());
        }
        txtTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getPhone() != null) {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(user.getName()
                            , user.getEmail()
                            , String.valueOf(user.getPhone())
                            , user.getImage_src());

                    editNameDialogFragment.show(fm, "fragment_edit_name");
                } else {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(user.getName()
                            , user.getEmail()
                            , user.getImage_src());
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
            }
        });
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getPhone() != null) {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(user.getName()
                            , user.getEmail()
                            , String.valueOf(user.getPhone())
                            , user.getImage_src());

                    editNameDialogFragment.show(fm, "fragment_edit_name");
                } else {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(user.getName()
                            , user.getEmail()
                            , user.getImage_src());
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
            }
        });
        Glide.with(imageView.getContext())
                .load(OfficeBotURI.getFileUrlPreFix() + st)
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .fitCenter().fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        return rowView;
    }
}

