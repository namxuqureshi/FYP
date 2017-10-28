package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GridViewHolder extends RecyclerView.ViewHolder {
    @Bind (R.id.grid_item_image)
    CircleImageView gridItemImage;
    @Bind (R.id.checked_pic)
    RadioButton checkedPic;

    public GridViewHolder(LayoutInflater inflater, ViewGroup parent) {
        this(inflater.inflate(R.layout.grid_view, parent, false));
    }

    public GridViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public RadioButton getCheckedPic() {
        return checkedPic;
    }

    public CircleImageView getGridItemImage() {
        return gridItemImage;
    }
}
