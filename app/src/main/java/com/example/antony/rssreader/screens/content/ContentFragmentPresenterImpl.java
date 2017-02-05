package com.example.antony.rssreader.screens.content;

import com.example.antony.rssreader.database.RssFeedDatabase;
import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.utilities.Parser;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class ContentFragmentPresenterImpl implements ContentFragmentContract.Presenter, Parser.ParseCompleteCallback {
    private ContentFragmentContract.View mView;
    private RssFeedDatabase mDatabase;

    public ContentFragmentPresenterImpl(ContentFragmentContract.View mView, RssFeedDatabase rssFeedDatabase) {
        this.mView = mView;
        this.mDatabase = rssFeedDatabase;
    }

    @Override
    public List<RssFeedItem> queryDatabase() {
        return null;
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
    public void onParsed(List<RssFeedItem> resultList) {
        mDatabase.saveRssListToDatabase(resultList);
        mView.showData(resultList);
    }

    @Override
    public void onError(String error) {
        mView.showMessage("Error parsing: " + error);
    }
}
