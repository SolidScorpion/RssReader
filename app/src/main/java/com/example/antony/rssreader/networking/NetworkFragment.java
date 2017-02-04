package com.example.antony.rssreader.networking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Pripachkin on 03.02.2017.
 */

public class NetworkFragment extends Fragment {
    private static final String TAG = "NetworkFragment";
    private static final String URL_KEY = "urlKey";
    private String mUrl;
    private DownloadCallBack mDownloadCallback;
    private DownloadTask mDownloadTask;

    public static NetworkFragment getInstance(String url, FragmentManager fragmentManager) {
        NetworkFragment networkFragment = (NetworkFragment) fragmentManager
                .findFragmentByTag(NetworkFragment.TAG);
        if (networkFragment == null) {
            networkFragment = new NetworkFragment();
            Bundle args = new Bundle();
            args.putString(URL_KEY, url);
            networkFragment.setArguments(args);
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }
        return networkFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof DownloadCallBack)) {
            throw new IllegalStateException("Must implement DownloadCallback");
        }
        mDownloadCallback = (DownloadCallBack) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDownloadCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(URL_KEY);
        setRetainInstance(true);
    }

    public void startDownload() {
        mDownloadTask = new DownloadTask(mDownloadCallback);
        mDownloadTask.execute(mUrl);
    }

    @Override
    public void onDestroy() {
        cancelDownload();
        super.onDestroy();
    }

    public void cancelDownload() {
        if (mDownloadTask != null) {
            mDownloadTask.cancel(true);
        }
    }
}
