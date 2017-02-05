package com.example.antony.rssreader;

import com.example.antony.rssreader.networking.DownloadCallBack;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface WebController {
    void onLinkClicked(String url);
    void sendNetworkRequest(String url, DownloadCallBack callBack);
    void cancelDownload();
}
