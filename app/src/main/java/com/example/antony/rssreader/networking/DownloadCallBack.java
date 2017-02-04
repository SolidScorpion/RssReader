package com.example.antony.rssreader.networking;

import android.net.NetworkInfo;

/**
 * Created by Pripachkin on 03.02.2017.
 */

public interface DownloadCallBack<T> {
    NetworkInfo getNetworkInfo();
    void deliverResult(T result);
    void cancelDownload();
}
