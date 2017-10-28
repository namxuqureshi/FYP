package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomSelectionListHolder {
    @Bind (R.id.child_card_group)
    CardView childCardGroup;
    @Bind (R.id.selection_name)
    TextView selectionName;
    @Bind (R.id.checked)
    CheckBox checked;

    public CustomSelectionListHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getSelectionName() {
        return selectionName;
    }

    public CardView getChildCardGroup() {
        return childCardGroup;
    }

    public CheckBox getChecked() {
        return checked;
    }
}
