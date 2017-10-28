package com.example.n_u.officebotapp.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.R.drawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIntroContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIntroContent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "param1";
    private static final String DESC = "param2";
    public static final String COLR = "color";
    public static final String DRWI = "drawable";

    // TODO: Rename and change types of parameters
    private String title;
    private String desc;
    private int clr, drw;
    private TextView introtitle;
    private ImageView introimage;
    private TextView introdesc;
    private RelativeLayout parentrel;
    private ScrollView parentscroll;


    public FragmentIntroContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentIntroContent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIntroContent newInstance(String param1, String param2) {
        FragmentIntroContent fragment = new FragmentIntroContent();
        Bundle args = new Bundle();
        args.putString(TITLE, param1);
        args.putString(DESC, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentIntroContent newInstance(String title, String description,
                                                   @android.support.annotation.DrawableRes int imageDrawable,
                                                   @android.support.annotation.ColorInt int bgColor) {
        FragmentIntroContent fragment = new FragmentIntroContent();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESC, description);
        args.putInt(COLR, bgColor);
        args.putInt(DRWI, imageDrawable);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            desc = getArguments().getString(DESC);
            clr = getArguments().getInt(COLR);
            drw = getArguments().getInt(DRWI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        android.view.View v = inflater.inflate(R.layout.fragment_intro_content_user, container, false);
        parentscroll = (ScrollView) v.findViewById(R.id.parent_scroll);
        parentrel = (RelativeLayout) v.findViewById(R.id.parent_rel);
        introdesc = (TextView) v.findViewById(R.id.intro_desc);
        introimage = (ImageView) v.findViewById(R.id.intro_image);
        introtitle = (TextView) v.findViewById(R.id.intro_title);
        introtitle.setTextColor(getResources().getColor(android.R.color.white));
        introdesc.setTextColor(getResources().getColor(android.R.color.white));
        introdesc.setText(desc);
        introtitle.setText(title);
        parentrel.setBackgroundColor(clr);
        parentscroll.setBackgroundColor(clr);
//        Drawable dr = getActivity().getResources().getDrawable(drw);
//        Bitmap map = ImageUtils.drawableToBitmap(dr);
//        File f = bitmapToFile(map);
//        try {
//            f = new id.zelory.compressor.Compressor(getActivity()).compressToFile(f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (!title.equalsIgnoreCase("Refresh Activates")) {
            Glide.with(getActivity())
                    .load(drw)
                    .asBitmap()
                    .error(drawable.zzz_cloud_outline_off)
                    .placeholder(drw)
                    .fitCenter().fitCenter()
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(introimage);
            introimage.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    Bitmap bmap = com.example.n_u.officebotapp.utils.ImageUtils.drawableToBitmap(introimage.getDrawable());
                    File file = bitmapToFile(bmap);
                    android.content.Intent install = new android.content.Intent(android.content.Intent.ACTION_VIEW);
                    install.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                    android.net.Uri apkURI = android.support.v4.content.FileProvider.getUriForFile(
                            getActivity(),
                            getActivity().getApplicationContext()
                                    .getPackageName() + ".provider", file);
                    install.setDataAndType(apkURI, "image/*");
                    install.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getActivity().startActivity(install);
                }
            });
        } else {
            introimage.setAdjustViewBounds(true);
            introimage.setMaxHeight(700);
            introimage.setMaxWidth(700);
            Glide.with(getActivity())
                    .load(drw)
                    .asGif()
                    .override(250, 250)
                    .error(drawable.zzz_cloud_outline_off)
                    .placeholder(drw)
                    .fitCenter().fitCenter()
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(introimage);
        }

        return v;
    }

    public static File bitmapToFile(Bitmap bitmap) {
        File outFile = getImageFilePNG();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outFile;
    }

    public static File getImageFilePNG() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/any name folder");
        dir.mkdirs();

        String fileName = "test.png";
        return new File(dir, fileName);
    }
}
