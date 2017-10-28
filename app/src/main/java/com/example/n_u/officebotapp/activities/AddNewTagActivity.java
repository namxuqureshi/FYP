package com.example.n_u.officebotapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.adapters.AddNewTagAdapter;
import com.example.n_u.officebotapp.helpers.Progress;
import com.example.n_u.officebotapp.intefaces.IStatus;
import com.example.n_u.officebotapp.intefaces.NamePassThroughInterface;
import com.example.n_u.officebotapp.models.Status;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.n_u.officebotapp.activities.TagActivity.RESULT_SYNC;

public class AddNewTagActivity
        extends NavigationActivity
        implements NamePassThroughInterface {
    private final HashMap<String, Object> body = new HashMap<>();
    private final IStatus request = (IStatus) OfficeBotURI.retrofit().create(IStatus.class);
    private AddNewTagAdapter adapter;
    private EditText nameText;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_nav_add_new_tag);
        nav(getString(R.string.add_new_tag_activity));
        nameText = ((EditText) findViewById(R.id.tag_name));
        List<Integer> numbers = new ArrayList<>();
        numbers.add(R.drawable.office);
        numbers.add(R.drawable.home);
        numbers.add(R.drawable.logo);
        adapter = new AddNewTagAdapter(this, numbers);
        GridView gridView = (GridView) findViewById(com.example.n_u.officebotapp.R.id.gridView);
        gridView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        gridView.setItemChecked(1, true);
        gridView.setAdapter(adapter);
    }

    public void onPressedGo(View view) {
        if ((nameText).getText().toString().isEmpty() && !adapter.getName().equals("0")) {
            if (!adapter.getName().equals("0"))
                Toast.makeText(this, R.string.empty_field, Toast.LENGTH_LONG).show();
            else Toast.makeText(this, "Chose Any One Picture!", Toast.LENGTH_LONG).show();
        } else {
            AppLog.logString(adapter.getName());
            body.put(getString(R.string.image_src_key), adapter.getName());
            body.put(getString(R.string.name_key), nameText.getText().toString());
            body.put(getString(R.string.user_id_key), OBSession.getPreference(getString(R.string.id_key), view.getContext()));
            body.put(getString(R.string.ssn_key), getIntent().getStringExtra(getString(R.string.tag_id_key)));
            Call<Status> call = request.addNewTag(this.body);
            Progress.Show(context, "Loading...");
            call.enqueue(new Callback<Status>() {
                public void onFailure(Call<Status> call,
                                      Throwable t) {
                    Toast.makeText(AddNewTagActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    Progress.Cancel();
                    finish();
                }

                public void onResponse(Call<Status> callback,
                                       Response<Status> statusResponse) {
                    if (statusResponse.code() == 200) {
                        Toast.makeText(AddNewTagActivity.this, statusResponse.body().getStatus(),
                                Toast.LENGTH_LONG).show();
                        setResult(RESULT_SYNC);
                        Intent i = new Intent(AddNewTagActivity.this, TagActivity.class);
                        i.putExtra(getString(R.string.v_r), getString(R.string.v_r));
                        OBSession.putPreference(getString(R.string.v_r), getString(R.string.v_r), getApplicationContext());
                        startActivity(i);
                    } else {
                        Toast.makeText(AddNewTagActivity.this,
                                "Server Offline" + statusResponse.code(),
                                Toast.LENGTH_LONG).show();

                    }
                    Progress.Cancel();
                    finish();
                }
            });
        }
    }

    @Override
    public void onTouchRB(String name) {
        if (!name.equalsIgnoreCase("Enter Name here"))
            nameText.setText(name);
        else {
            nameText.setText("");
        }
    }

}

