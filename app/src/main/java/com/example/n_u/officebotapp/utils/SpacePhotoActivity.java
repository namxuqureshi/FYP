package com.example.n_u.officebotapp.utils;

/**
 * Created by usman on 24-Aug-17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.n_u.officebotapp.R;

public class SpacePhotoActivity extends AppCompatActivity {

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        mImageView = (ImageView) findViewById(R.id.image);
        String spacePhoto = getIntent().getStringExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)
                .load(spacePhoto)
                .asBitmap()
                .error(R.drawable.zzz_cloud_outline_off)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
    }
}
