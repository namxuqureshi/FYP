package com.example.n_u.officebotapp.viewsholders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.n_u.officebotapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomRequestsHolder {
    @Bind (R.id.request_list_name)
    TextView requestListName;
    @Bind (R.id.add)
    Button add;
    @Bind (R.id.block)
    Button block;
    @Bind (R.id.request_list_circle)
    CircleImageView requestListCircle;
    @Bind (R.id.received_time)
    TextView receivedTime;

    public CustomRequestsHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public TextView getReceivedTime() {
        return receivedTime;
    }

    public Button getAdd() {
        return add;
    }

    public Button getBlock() {
        return block;
    }

    public TextView getRequestListName() {
        return requestListName;
    }

    public CircleImageView getRequestListCircle() {
        return requestListCircle;
    }
}
