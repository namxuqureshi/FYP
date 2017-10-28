package com.example.n_u.officebotapp.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.intefaces.NamePassThroughInterface;

import java.util.ArrayList;
import java.util.List;

public class AddNewTagAdapter
        extends BaseAdapter {
    private final Activity context;
    private List<Integer> adapterData = new ArrayList<>();
    private NamePassThroughInterface nameSet;
    private boolean a, b, c;
    private RadioButton ab, bc, ca;

    public AddNewTagAdapter(Activity paramActivity, List<Integer> paramList) {
        this.context = paramActivity;
        this.adapterData = paramList;
        nameSet = (NamePassThroughInterface) paramActivity;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public int getCount() {
        return this.adapterData.size();
    }

    public Integer getItem(int i) {
        return this.adapterData.get(i);
    }

    public long getItemId(int paramInt) {
        return this.adapterData.get(paramInt).longValue();
    }

    @NonNull
    public View getView(final int position, View rowView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final ViewHolder holder;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.grid_view, parent, false);
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.imageView.setImageResource(adapterData.get(position));
        if (adapterData.get(position).equals(R.drawable.office)) {
            ab = holder.radioButton;
        }
        if (adapterData.get(position).equals(R.drawable.home)) {
            bc = holder.radioButton;
        }
        if (adapterData.get(position).equals(R.drawable.logo)) {
            ca = holder.radioButton;
        }
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterData.get(position).equals(R.drawable.office)) {
                    a = true;
                    b = false;
                    c = false;
                    ab.setChecked(true);
                    bc.setChecked(false);
                    ca.setChecked(false);
                    nameSet.onTouchRB("Office");
                }
                if (adapterData.get(position).equals(R.drawable.home)) {
                    a = false;
                    b = true;
                    c = false;
                    bc.setChecked(true);
                    ca.setChecked(false);
                    ab.setChecked(false);
                    nameSet.onTouchRB("Home");
                }
                if (adapterData.get(position).equals(R.drawable.logo)) {
                    a = false;
                    b = false;
                    c = true;
                    ca.setChecked(true);
                    ab.setChecked(false);
                    bc.setChecked(false);
                    nameSet.onTouchRB("Enter Name here");
                }
            }
        });
        return rowView;
    }

    public String getName() {
        if (a) {
            return "office";
        } else if (b) {
            return "home";
        } else if (c) {
            return "logo";
        } else return "0";
    }

    public boolean hasStableIds() {
        return true;
    }


    private static class ViewHolder {
        ImageView imageView;
        RadioButton radioButton;

        ViewHolder(View view) {
            imageView = ((ImageView) view.findViewById(R.id.grid_item_image));
            radioButton = (RadioButton) view.findViewById(R.id.checked_pic);
        }
    }
}
