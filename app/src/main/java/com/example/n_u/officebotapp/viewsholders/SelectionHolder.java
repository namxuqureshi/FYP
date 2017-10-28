package com.example.n_u.officebotapp.viewsholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import static com.example.n_u.officebotapp.R.id.checked;
import static com.example.n_u.officebotapp.R.id.selection_name;

public class SelectionHolder
        extends RecyclerView.ViewHolder {
    private CheckBox checkBox;
    private TextView name;

    public SelectionHolder(View paramView) {
        super(paramView);
        this.name = ((TextView) paramView.findViewById(selection_name));
        this.checkBox = ((CheckBox) paramView.findViewById(checked));
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public void setCheckBox(CheckBox paramCheckBox) {
        this.checkBox = paramCheckBox;
    }

    public TextView getName() {
        return this.name;
    }

    public void setName(TextView paramTextView) {
        this.name = paramTextView;
    }
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.viewsholders.SelectionHolder

 * JD-Core Version:    0.7.0.1

 */