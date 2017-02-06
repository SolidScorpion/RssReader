package com.example.antony.rssreader.screens.mainscreen;

import com.example.antony.rssreader.database.RssDatabaseImpl;
import com.example.antony.rssreader.database.RssFeedDatabase;
import com.example.antony.rssreader.models.MenuItem;
import com.example.antony.rssreader.utilities.Constants;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class MainScreenPresenterImpl implements MainScreenContract.Presenter {
    private MainScreenContract.View mView;
    private RssFeedDatabase database;
    public MainScreenPresenterImpl(MainScreenContract.View view, RssFeedDatabase rssDatabase) {
        mView = view;
        database = rssDatabase;
        database.saveMenuItem(new MenuItem("Kotaku", Constants.KOTAKU_RSS_FEED_LINK));
    }

    @Override
    public void saveMenuItem(MenuItem item) {
        database.saveMenuItem(item);
    }

    @Override
    public void removeItem(MenuItem item) {
        database.removeMenuItem(item);
    }

    @Override
    public void getMenuItems() {
        database.getMenuItemsAsync(new RssFeedDatabase.DatabaseCallback<MenuItem>() {
            @Override
            public void onDataReceived(List<MenuItem> data) {
                mView.showMenuItems(data);
            }

            @Override
            public void onDataSaved() {

            }
        });
    }
}
