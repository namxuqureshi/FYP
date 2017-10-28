package com.example.n_u.officebotapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.models.Tag;
import com.example.n_u.officebotapp.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.n_u.officebotapp.R.id.tag_name;

public class TagAdapter
        extends BaseAdapter {
    private final Context context;
    private final String ANDROID_RESOURCE = "android.resource://";
    private final String FOREWARD_SLASH = "/";
    private List<Tag> adapterData = new ArrayList<>();
    private String uri;  // where myresource (without the extension) is the file

    public TagAdapter(Context paramContext) {
        this.context = paramContext;
    }

//    public TagAdapter(Context paramContext, List<Tages> paramList) {
//        this.context = paramContext;
//    }

    public void refresh(int id) {
        clear();
        if (adapterData.isEmpty()) {
            try {
                adapterData.addAll(Tag.getAll(id));
                notifyDataSetChanged();
            } catch (NullPointerException e) {
                AppLog.logString(e.getMessage());
                Toast.makeText(this.context, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addAll(int id) {
        if (adapterData.isEmpty()) {
            try {
                adapterData.addAll(Tag.getAll(id));
                notifyDataSetChanged();
            } catch (NullPointerException e) {
                AppLog.logString(e.getMessage());
                Toast.makeText(this.context, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addAll(int id, boolean b) {
        if (adapterData.isEmpty()) {
            try {
                adapterData.addAll(Tag.getAll(id, b));
                notifyDataSetChanged();
            } catch (NullPointerException e) {
                AppLog.logString(e.getMessage());
                Toast.makeText(this.context, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addAll(List<Tag> paramList) {
        adapterData.addAll(paramList);
        notifyDataSetChanged();
    }

    public void clear() {
        if (!this.adapterData.isEmpty()) {
            this.adapterData.clear();
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.adapterData.size();
    }

    @Nullable
    public Tag getItem(int paramInt) {
        return this.adapterData.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return this.adapterData.get(paramInt).getTagId();
    }

    @NonNull
    public View getView(final int position
            , View convertView
            , @NonNull ViewGroup viewGroup) {
        LayoutInflater from = LayoutInflater.from(this.context);
        View rootView = convertView;
        if (convertView == null) {
            rootView = from.inflate(R.layout.custom_tag, viewGroup, false);
        }
        TextView tvName = (TextView) rootView.findViewById(tag_name);
        tvName.setText(adapterData.get(position).getName());
        if (tvName.getText().length() > 3) {
            tvName.setTextSize(2, 20.0F);
        }
        TextView tv = (TextView) rootView.findViewById(R.id.total_message_tab);
        tv.setText(adapterData.get(position).getMessage_count());
        CircleImageView imageView = (CircleImageView) rootView.findViewById(R.id.tag_image);

        if (adapterData.get(position).getImage_src() != null) {
            uri = "@drawable/" + adapterData.get(position).getImage_src();
        } else {
            uri = "@drawable/office";
        }
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
//        imageView.setImageDrawable(res);
        Glide.with(imageView.getContext())
                .load("")
                .placeholder(res)
                .into(imageView);
        return rootView;
    }


}
