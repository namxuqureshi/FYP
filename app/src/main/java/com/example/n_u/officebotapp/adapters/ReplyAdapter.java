package com.example.n_u.officebotapp.adapters;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.n_u.officebotapp.R;
import com.example.n_u.officebotapp.fragments.ProfileFragment;
import com.example.n_u.officebotapp.models.Reply;
import com.example.n_u.officebotapp.models.SearchUser;
import com.example.n_u.officebotapp.utils.AppLog;
import com.example.n_u.officebotapp.utils.InternetConnection;
import com.example.n_u.officebotapp.utils.OBSession;
import com.example.n_u.officebotapp.utils.OfficeBotURI;
import com.example.n_u.officebotapp.viewsholders.CustomRepliesHolder;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplyAdapter
        extends BaseAdapter {
    private final Activity mContext;
    private CustomRepliesHolder holder = null;
    private List<Reply> adapterData = new ArrayList<>();
    private PrettyTime p = new PrettyTime();
    private DownloadFileFromURL downloader, downloader2;
    private FragmentManager fm;

    public ReplyAdapter(Activity paramActivity) {
        mContext = paramActivity;
    }

    public ReplyAdapter(Activity paramActivity, List<Reply> paramList, android.support.v4.app.FragmentManager fm) {
        mContext = paramActivity;
        adapterData.addAll(paramList);
        this.fm = fm;
    }

    public void addAll(List<Reply> paramList) {
        adapterData.addAll(paramList);
        notifyDataSetChanged();
    }

    public void clear() {
        if (!adapterData.isEmpty()) {
            adapterData.clear();
        }
        notifyDataSetChanged();
    }

    public void addAll() {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Reply.getAll());
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(mContext, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public void addAll(int id) {
        if (!adapterData.isEmpty()) {
            AppLog.logString("List Empty");
        } else {
            try {
                adapterData.addAll(Reply.getAll(id));
                notifyDataSetChanged();
            } catch (NullPointerException localNullPointerException) {
                AppLog.logString(localNullPointerException.getMessage());
                Toast.makeText(mContext, localNullPointerException.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    public int getCount() {
        if (adapterData != null) return adapterData.size();
        else return 0;
    }

    public Reply getItem(int position) {
        return adapterData.get(position);
    }

    public long getItemId(int position) {
        return adapterData.get(position).getReplyId();
    }

    @RequiresApi (api = Build.VERSION_CODES.KITKAT)
    @NonNull
    public View getView(final int position
            , View view
            , @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        view = inflater.inflate(R.layout.custom_replies_layout, parent, false);

        holder = new CustomRepliesHolder(view);
        holder.getReplyMainMsg().setText(adapterData.get(position).getReply());
        if (holder.getReplyMainMsg().getText().length() > 15) {
            holder.getReplyMainMsg().setTextSize(2, 12.0F);
        }
        holder.getReplyMainTime().setText(AppLog.getPretty(adapterData.get(position).getCreated_at(), mContext));
        if (Objects.equals(Integer.parseInt(OBSession.getPreference(mContext.getString(R.string.id_key), mContext))
                , adapterData.get(position).getUser_id())) {
            holder.getReplyOwnerName().setText(OBSession.getPreference(mContext.getString(R.string.name_key), mContext));
        } else {
            holder.getReplyOwnerName().setText(String.valueOf(SearchUser.getNameUserOther(adapterData.get(position).getUser_id())));
        }
        if (holder.getReplyOwnerName().getText().length() > 8) {
            holder.getReplyOwnerName().setTextSize(2, 12.0F);
        }
        holder.getReplyOwnerName().setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                SearchUser su = SearchUser.getUserOther(adapterData.get(position).getUser_id());
                if (su.getPhone() != null) {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(su.getName()
                            , su.getEmail(), String.valueOf(su.getPhone()), su.getImage_src());

                    editNameDialogFragment.show(fm, "fragment_edit_name");
                } else {
                    ProfileFragment editNameDialogFragment = ProfileFragment.newInstance(su.getName()
                            , su.getEmail(), su.getImage_src());
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
                AppLog.setVibrate(mContext, AppLog.INTENSITY_MIDDLE);
            }
        });
        if (adapterData.get(position).getAudio_src() != null) {
            downloader = new DownloadFileFromURL(true
                    , false
                    , holder.getAudioBar().getTag());
            holder.getFrameAudio().setVisibility(View.VISIBLE);
            holder.getAudioBar().setVisibility(View.GONE);
            holder.getAudioFile().setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    AppLog.setVibrate(mContext, AppLog.INTENSITY_LOW);
                    AppLog.logString(String.valueOf(AppLog.isFilePresent(adapterData.get(position).getAudio_src())));
                    if (AppLog.isFilePresent(adapterData.get(position).getAudio_src())) {
                        AppLog.defaultFileOpener(AppLog.PATH_FILE + adapterData.get(position).getAudio_src(), mContext);

                    } else {
                        if (InternetConnection.checkConnection(mContext)) {
                            if (downloader.getStatus() != android.os.AsyncTask.Status.RUNNING) {
                                downloader.execute(OfficeBotURI.getFileUrlPreFix() + adapterData.get(position).getAudio_src()
                                        , adapterData.get(position).getAudio_src());
                                AppLog.toastShortString("File Downloading please wait!", mContext);
                            } else {
                                AppLog.toastShortString(AppLog.PLEASE_WAIT
                                        , mContext);
                            }

                        } else {
                            AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE
                                    , mContext);
                        }

                    }
                }
            });
        } else {
            holder.getFrameAudio().setVisibility(View.GONE);
            holder.getAudioFile().setClickable(false);
        }
        if (adapterData.get(position).getData_src() != null) {
            downloader2 = new DownloadFileFromURL(false
                    , true
                    , holder.getOtherBar().getTag());
            holder.getFrameOther().setVisibility(View.VISIBLE);
            holder.getOtherBar().setVisibility(View.GONE);
            holder.getOtherFile().setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    AppLog.setVibrate(mContext, AppLog.INTENSITY_LOW);
                    AppLog.logString("onClick: " + AppLog.isFilePresent(adapterData.get(position).getData_src()));
                    if (AppLog.isFilePresent(adapterData.get(position).getData_src())) {
                        AppLog.defaultFileOpener(AppLog.PATH_FILE + adapterData.get(position).getData_src(), mContext);

                    } else {
                        if (InternetConnection.checkConnection(mContext)) {
                            if (downloader2.getStatus() != android.os.AsyncTask.Status.RUNNING) {
                                downloader2.execute(OfficeBotURI.getFileUrlPreFix() + adapterData.get(position).getData_src()
                                        , adapterData.get(position).getData_src());
                                AppLog.toastShortString("File Downloading please wait!", mContext);
                            } else {
                                AppLog.toastShortString(AppLog.PLEASE_WAIT
                                        , mContext);
                            }
                        } else {
                            AppLog.toastShortString(AppLog.NET_NOT_AVAILABLE
                                    , mContext);
                        }
                    }
                }
            });
        } else {
            holder.getFrameOther().setVisibility(View.GONE);
            holder.getOtherFile().setClickable(false);
        }

        view.setTag(holder);
        return view;
    }

    private class DownloadFileFromURL
            extends AsyncTask<String, String, String> {
        static final int INT_SIZE = 1024;
        static final int SIZE_BUFF = 8192;
        boolean isAudio;
        boolean isOther;
        ProgressBar audio, other;

        DownloadFileFromURL(boolean b, boolean b1, Object o) {
            isAudio = b;
            isOther = b1;
            if (b) {
                audio = new ProgressBar(mContext);
                audio.setTag(o);
            } else {
                other = new ProgressBar(mContext);
                other.setTag(o);
            }
        }

        @android.support.annotation.Nullable
        protected String doInBackground(String... strings) {
            int count;
            try {
                URL url = new URL(strings[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream(AppLog.PATH_FILE + strings[1]);

                byte data[] = new byte[INT_SIZE];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                AppLog.logString(e.getMessage());
//                AppLog.toastString(e.getMessage(), mContext.getApplicationContext());
            }

            return null;
        }

        protected void onPostExecute(String paramString) {
            if (isAudio) {
                audio.setVisibility(View.GONE);
                audio.setClickable(true);
                Toast.makeText(mContext
                        , "Audio Downloaded Press Again to Listen!"
                        , Toast.LENGTH_SHORT).show();
            }
            if (isOther) {
                other.setVisibility(View.GONE);
                other.setClickable(true);
                Toast.makeText(mContext
                        , "File Downloaded Press Again to See!"
                        , Toast.LENGTH_SHORT).show();
            }
            AppLog.setVibrate(mContext, AppLog.INTENSITY_HIGH);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (isAudio) {
                audio.setClickable(false);
                audio.setVisibility(View.VISIBLE);
                audio.setIndeterminate(false);
            }
            if (isOther) {
                other.setClickable(false);
                other.setVisibility(View.VISIBLE);
                other.setIndeterminate(false);
            }
        }

        protected void onProgressUpdate(String... strings) {
            if (isAudio) {
                audio.setProgress(Integer.parseInt(strings[0]));
            }
            if (isOther) {
                other.setProgress(Integer.parseInt(strings[0]));
            }
        }
    }
}
