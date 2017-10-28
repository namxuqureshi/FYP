package com.example.n_u.officebotapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.models.Group;
import com.example.n_u.officebotapp.utils.AppLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TagPermissionGroupAdapter
        extends BaseAdapter {
    private List<Group> filterData;
    private List<Group> adapterData = new ArrayList<>();
    private Context mContext;

    public TagPermissionGroupAdapter(Context paramContext, List<Group> paramList) {
        this.filterData = paramList;
        this.mContext = paramContext;
    }

    public void addAll(List<Group> paramList) {
        this.filterData.addAll(paramList);
        notifyDataSetChanged();
    }

    public ArrayList<Integer[]> checkedList() {
        ArrayList<Integer[]> localArrayList = new ArrayList<>();
        Iterator localIterator = this.adapterData.iterator();
        while (localIterator.hasNext()) {
            Group localGroup = (Group) localIterator.next();
            if (localGroup.ispNone()) {
                localArrayList.add(new Integer[]{Integer.valueOf(localGroup.getGroupId()), Integer.valueOf(4)});
            } else if (localGroup.ispRead()) {
                localArrayList.add(new Integer[]{Integer.valueOf(localGroup.getGroupId()), Integer.valueOf(1)});
            } else {
                localArrayList.add(new Integer[]{Integer.valueOf(localGroup.getGroupId()), Integer.valueOf(3)});
            }
        }
        AppLog.logString("checkedList: " + localArrayList);
        return localArrayList;
    }

    public void clear() {
        if (!this.filterData.isEmpty()) {
            this.filterData.clear();
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.filterData != null) {
            return this.filterData.size();
        }
        return 0;
    }

    public Object getItem(int paramInt) {
        return this.filterData.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return this.filterData.get(paramInt).getGroupId();
    }

    public View getView(final int position
            , View view
            , ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.custom_tag_permission_selection
                , parent
                , false);
        TextView tv = (TextView) view.findViewById(R.id.selection_name_base);
        final CompoundButton none = (CompoundButton) view.findViewById(R.id.checked_none);
        final CompoundButton read = (CompoundButton) view.findViewById(R.id.checked_r);
        final CompoundButton readWrite = (CompoundButton) view.findViewById(R.id.checked_r_w);
        tv.setText(this.filterData.get(position).getName());
        if (this.filterData.get(position).ispNone()) {
            none.setChecked(true);
            if (!this.adapterData.contains(this.filterData.get(position))) {
                this.adapterData.add(this.filterData.get(position));
            }
        }
        if (this.filterData.get(position).ispRead()) {
            read.setChecked(true);
            if (!this.adapterData.contains(this.filterData.get(position))) {
                this.adapterData.add(this.filterData.get(position));
            }
        } else if (this.filterData.get(position).ispReadWrite()) {
            readWrite.setChecked(true);
            if (!this.adapterData.contains(this.filterData.get(position))) {
                this.adapterData.add(this.filterData.get(position));
            }
        }

        none.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                if (none.isChecked()) {
                    filterData.get(position).setpNone(true);
                    filterData.get(position).setpRead(false);
                    filterData.get(position).setpReadWrite(false);
                    read.setChecked(false);
                    readWrite.setChecked(false);
                    if (!adapterData.contains(filterData.get(position))) {
                        adapterData.add(filterData.get(position));
                    }
                } else {
                    return;
                }
                adapterData.remove(filterData.get(position));
                adapterData.add(filterData.get(position));
            }
        });
        read.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                if (read.isChecked()) {
                    filterData.get(position).setpNone(false);
                    filterData.get(position).setpRead(true);
                    filterData.get(position).setpReadWrite(false);
                    none.setChecked(false);
                    readWrite.setChecked(false);
                    if (!adapterData.contains(filterData.get(position))) {
                        adapterData.add(filterData.get(position));
                    }
                } else {
                    return;
                }
                adapterData.remove(filterData.get(position));
                adapterData.add(filterData.get(position));
            }
        });
        readWrite.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
                if (readWrite.isChecked()) {
                    filterData.get(position).setpNone(false);
                    filterData.get(position).setpRead(false);
                    filterData.get(position).setpReadWrite(true);
                    read.setChecked(false);
                    none.setChecked(false);
                    if (!adapterData.contains(filterData.get(position))) {
                        adapterData.add(filterData.get(position));
                    }
                } else {
                    return;
                }
                adapterData.remove(filterData.get(position));
                adapterData.add(filterData.get(position));
            }
        });
        return view;


    }
}