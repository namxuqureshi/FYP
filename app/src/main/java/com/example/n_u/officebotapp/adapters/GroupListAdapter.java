package com.example.n_u.officebotapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.models.Member;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.n_u.officebotapp.R.id.group_member;
import static com.example.n_u.officebotapp.R.layout.list_group;

public class GroupListAdapter
        extends BaseExpandableListAdapter {
    private Context mContext;
    private Map<Group, List<Member>> dataChild = new HashMap<>();
    private List<Group> adapterData = new ArrayList<>();
    private FragmentManager fm;

    public GroupListAdapter(Context activity
            , List<Group> paramList
            , Map<Group
            , List<Member>> paramMap
            , FragmentManager fm) {
        mContext = activity;
        adapterData = paramList;
        dataChild = paramMap;
        this.fm = fm;
    }

    public void addAll(List<Group> groups, Map<Group, List<Member>> listMap) {
        this.adapterData.addAll(groups);
        this.dataChild.putAll(listMap);
        notifyDataSetChanged();
    }

    public void addAll() {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Group.getAll());
                AppLog.logString(adapterData.toString());
                int j = adapterData.size();
                int i = 0;
                while (i < j) {
                    dataChild.put(adapterData.get(i),
                            Member.getAll(adapterData.get(i).getGroupId()));
                    i += 1;
                }
                notifyDataSetChanged();
            } catch (NullPointerException e) {
                AppLog.logString(e.getMessage());
                Toast.makeText(mContext, e.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void clear() {
        if ((!this.adapterData.isEmpty()) && (!this.dataChild.isEmpty())) {
            this.adapterData.clear();
            this.dataChild.clear();
        }
        notifyDataSetChanged();
    }

    public Member getChild(int groupPosition, int childPosition) {
        return ((Member) ((List) dataChild.get(adapterData.get(groupPosition))).get(childPosition));
    }

    public List<Member> getChildDetail(long position) {
        return dataChild.get(adapterData.get((int) position));
    }

    public long getChildId(int groupPosition, int childPosition) {
        return ((Member) ((List) this.dataChild.get(this.adapterData.get(groupPosition))).get(childPosition)).getUserId();
    }

    public View getChildView(int groupPosition
            , int childPosition
            , boolean isLastChild
            , View convertView
            , ViewGroup parent) {
        String str = getChild(groupPosition, childPosition).getName();
        View view = convertView;
        final SearchUser su = SearchUser.getUserOther(getChild(groupPosition, childPosition).getUserId());
        String st = "no.jpg";
        if (su != null)
            st = su.getImage_src();
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }
        TextView tv = (TextView) view.findViewById(R.id.lblListItem);
        tv.setText(str);
        if (tv.getText().length() > 8) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        CircleImageView image = (CircleImageView) view.findViewById(R.id.group_child_pic);
        Glide.with(image.getContext())
                .load(OfficeBotURI.getFileUrlPreFix() + st)
                .asBitmap()
                .placeholder(R.drawable.profile_pic_default)
                .fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .skipMemoryCache(true).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        CardView cw = (CardView) view.findViewById(R.id.child_card_group);
        cw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        return view;
    }

    public int getChildrenCount(int groupPosition) {
        if (dataChild.get(adapterData.get(groupPosition)) != null)
            return ((List) dataChild.get(adapterData.get(groupPosition))).size();
        else return 0;
    }

    public Object getGroup(int groupPosition) {
        return this.adapterData.get(groupPosition).getName();
    }

    public Group getGroupDetail(int position) {
        return this.adapterData.get(position);
    }

    public int getGroupCount() {
        if (dataChild != null)
            return adapterData.size();
        else return 0;
    }

    public long getGroupId(int paramInt) {
        return this.adapterData.get(paramInt).getGroupId();
    }

    public View getGroupView(int groupPosition
            , boolean isExpanded
            , View convertView
            , ViewGroup parent) {
        String str = (String) getGroup(groupPosition);
        View localView = convertView;
        if (convertView == null) {
            localView = LayoutInflater.from(mContext).inflate(list_group, parent, false);
        }
        TextView countChild = (TextView) localView.findViewById(group_member);
        countChild.setTypeface(null, 1);
        countChild.setText(String.valueOf(getChildrenCount(groupPosition)));
        TextView tv = (TextView) localView.findViewById(R.id.lblListHeader);
        tv.setText(str);
        if (tv.getText().length() > 8) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        CircleImageView image = (CircleImageView) localView.findViewById(R.id.group_pic);
        Glide.with(image.getContext())
                .load(R.drawable.group)
                .asBitmap()
                .fitCenter().crossFade().animate(new AlphaAnimation(2.0F, -2.0F))
                .skipMemoryCache(true).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE)
                .into(image);
        return localView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int paramInt1, int paramInt2) {
        return true;
    }

    public void delete(Group id) {
        dataChild.remove(id);
        adapterData.remove(id);
        notifyDataSetChanged();
    }
}