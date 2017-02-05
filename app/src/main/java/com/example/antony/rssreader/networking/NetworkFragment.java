package com.example.antony.rssreader.networking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Pripachkin on 03.02.2017.
 */

public class NetworkFragment extends Fragment {
    private static final String TAG = "NetworkFragment";
    private DownloadCallBack mDownloadCallback;
    private DownloadTask mDownloadTask;

    public static NetworkFragment getInstance(FragmentManager fragmentManager) {
        NetworkFragment networkFragment = (NetworkFragment) fragmentManager
                .findFragmentByTag(NetworkFragment.TAG);
        if (networkFragment == null) {
            networkFragment = new NetworkFragment();
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }
        return networkFragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDownloadCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startDownload(String mUrl, DownloadCallBack callBack) {
        mDownloadCallback = callBack;
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
