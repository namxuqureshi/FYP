package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomTagPermissionSelectionHolder {
    @Bind (R.id.card_view_tag_permission)
    CardView cardViewTagPermission;
    @Bind (R.id.selection_name_base)
    TextView selectionNameBase;
    @Bind (R.id.checked_none)
    RadioButton checkedNone;
    @Bind (R.id.checked_r)
    RadioButton checkedR;
    @Bind (R.id.checked_r_w)
    RadioButton checkedRW;

    public CustomTagPermissionSelectionHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public RadioButton getCheckedNone() {
        return checkedNone;
    }

    public RadioButton getCheckedR() {
        return checkedR;
    }

    public RadioButton getCheckedRW() {
        return checkedRW;
    }

    public CardView getCardViewTagPermission() {
        return cardViewTagPermission;
    }

    public TextView getSelectionNameBase() {
        return selectionNameBase;
    }
}
