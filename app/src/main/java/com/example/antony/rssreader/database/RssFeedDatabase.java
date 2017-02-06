package com.example.antony.rssreader.database;

import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface RssFeedDatabase {
    void saveRssListToDatabase(List<RssFeedItem> items);
    void saveRssItemToDatabase(RssFeedItem item);
    void saveRssListToDatabaseAsync(List<RssFeedItem> data,DatabaseCallback callback);
    List<RssFeedItem> getAllData();
    boolean hasData();
    void clearAllData();
    void getDataAsync(DatabaseCallback callback);
    interface DatabaseCallback {
        void onDataReceived(List<RssFeedItem> data);
        void onDataSaved();
    }
}
