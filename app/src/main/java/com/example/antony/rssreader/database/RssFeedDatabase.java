package com.example.antony.rssreader.database;

import com.example.antony.rssreader.models.MenuItem;
import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface RssFeedDatabase {
    void saveRssListToDatabase(List<RssFeedItem> items);
    void saveRssItemToDatabase(RssFeedItem item);
    void saveRssListToDatabaseAsync(List<RssFeedItem> data,DatabaseCallback<RssFeedItem> callback);
    List<RssFeedItem> getAllData();
    boolean hasData();
    void clearAllData();
    void getDataAsync(DatabaseCallback callback);
    void saveMenuItem(MenuItem item);
    void saveMenuItems(List<MenuItem> items);
    List<MenuItem> getMenuItems();
    void saveMenuItemsAsync(List<MenuItem> items, DatabaseCallback callback);
    void getMenuItemsAsync(DatabaseCallback<MenuItem> databaseCallback);

    interface DatabaseCallback <T>{
        void onDataReceived(List<T> data);
        void onDataSaved();
    }
}
