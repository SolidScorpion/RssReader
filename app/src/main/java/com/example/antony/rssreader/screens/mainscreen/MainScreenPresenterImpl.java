package com.example.antony.rssreader.screens.mainscreen;

import com.example.antony.rssreader.database.RssFeedDatabase;
import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.utilities.Parser;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class MainScreenPresenterImpl implements MainScreenContract.Presenter, Parser.ParseCompleteCallback {
    private MainScreenContract.View mView;
    private RssFeedDatabase mDatabase;
    public MainScreenPresenterImpl(MainScreenContract.View view, RssFeedDatabase rssDatabase) {
        mView = view;
        this.mDatabase = rssDatabase;
    }

    @Override
    public void fetchData(String link) {
        mView.makeNetworkCall(link);
    }

    @Override
    public void OnDeliveredResult(RssFeed result) {
        if (result != null) {
            new Parser(this).execute(result);
        }
    }

    @Override
    public List<RssFeedItem> queryDatabase() {
        return mDatabase.getAllData();
    }

    @Override
    public void onParsed(List<RssFeedItem> resultList) {
        if (resultList != null) {
            mDatabase.saveRssListToDatabase(resultList);
            mView.showData(mDatabase.getAllData());
        }
    }

    @Override
    public void onError(String error) {
        mView.showMessage(error);
    }
}
