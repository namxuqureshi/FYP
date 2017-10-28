package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class CustomTagPermissionHolder
        extends RecyclerView.ViewHolder {
    CardView cardViewTagPermission;
    RadioButton checkedNone;
    RadioButton checkedR;
    RadioButton checkedRW;
    TextView selectionNameBase;

    public CustomTagPermissionHolder(View paramView) {
        super(paramView);
//        this.cardViewTagPermission = ((CardView) paramView.findViewById(2132017655));
//        this.selectionNameBase = ((TextView) paramView.findViewById(2132017656));
//        this.checkedNone = ((RadioButton) paramView.findViewById(2132017657));
//        this.checkedR = ((RadioButton) paramView.findViewById(2132017658));
//        this.checkedRW = ((RadioButton) paramView.findViewById(2132017659));
    }

    public CardView getCardViewTagPermission() {
        return this.cardViewTagPermission;
    }

    public RadioButton getCheckedNone() {
        return this.checkedNone;
    }

    public RadioButton getCheckedR() {
        return this.checkedR;
    }

    public RadioButton getCheckedRW() {
        return this.checkedRW;
    }

    public TextView getSelectionNameBase() {
        return this.selectionNameBase;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.viewsholders.CustomTagPermissionSelectionHolder

 * JD-Core Version:    0.7.0.1

 */