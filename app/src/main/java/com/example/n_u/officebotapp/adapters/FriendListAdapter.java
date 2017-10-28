package com.example.n_u.officebotapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.models.Friend;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.viewsholders.FriendListHolder;

import java.util.ArrayList;
import java.util.List;

public class FriendListAdapter
        extends RecyclerView.Adapter<FriendListHolder> {
    private List<Friend> adapterData = new ArrayList<>();
    private Context mContext;
    private FragmentManager fm;

    public FriendListAdapter(Context paramContext, List<Friend> paramList, FragmentManager fm) {
        this.adapterData = paramList;
        this.mContext = paramContext;
        this.fm = fm;
    }

    public void addAll() {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Friend.getAll());
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(this.mContext, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void addAll(List<Friend> friends) {
        adapterData.addAll(friends);
        notifyDataSetChanged();
    }

    public void clear() {
        if (!this.adapterData.isEmpty()) {
            this.adapterData.clear();
        }
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (adapterData != null)
            return adapterData.size();
        else return 0;
    }

    public void onBindViewHolder(final FriendListHolder holder, final int position) {
        AppLog.logString(adapterData.get(holder.getAdapterPosition()).getName());
        AppLog.logString(holder.getNameTextView().getTag() + "");
        holder.getNameTextView().setText(adapterData.get(holder.getAdapterPosition()).getName());
        holder.getLl().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SearchUser su = SearchUser.getUserOther(adapterData.get(holder.getAdapterPosition()).getFriendId());
                if (su.getPhone() != null) {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(su.getName()
                            , su.getEmail(), String.valueOf(su.getPhone()), su.getImage_src());

                    editNameDialogFragment.show(fm, "fragment_edit_name");
                } else {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(su.getName()
                            , su.getEmail(), su.getImage_src());
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
            }
        });
        String st = "no.jpg";
        try {
            if (SearchUser.imagePath(adapterData
                    .get(position)
                    .getFriendId())
                    .getImage_src() == null)
                st = "no.jpg";
            else st = SearchUser.imagePath(adapterData
                    .get(position)
                    .getFriendId())
                    .getImage_src();
        } catch (RuntimeException e) {
            AppLog.logString(e.getMessage());
        }
        Glide.with(holder.getFriendCircleView().getContext())
                .load(OfficeBotURI.getFileUrlPreFix() + st)
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .skipMemoryCache(true).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .fitCenter().fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .into(holder.getFriendCircleView());
    }

    public FriendListHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new FriendListHolder(LayoutInflater.
                from(mContext).
                inflate(R.layout.custom_friend_list, parent, false));
    }

    public String getName(int pos) {
        return adapterData.get(pos).getName();
    }

    public int getFriendId(int position) {
        return adapterData.get(position).getFriendId();
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.adapters.FriendListAdapter

 * JD-Core Version:    0.7.0.1

 */