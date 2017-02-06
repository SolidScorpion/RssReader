package com.example.antony.rssreader.screens.content;

import com.example.antony.rssreader.database.RssFeedDatabase;
import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;
import com.example.antony.rssreader.utilities.Parser;

import java.util.ArrayList;
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
    public void queryDatabase(String url) {
        if (mDatabase.hasData()) {
            mDatabase.getDataAsync(url,new RssFeedDatabase.DatabaseCallback<RssFeedItem>() {
                @Override
                public void onDataReceived(List<RssFeedItem> data) {
                    mView.showData(data);
                }

                @Override
                public void onDataSaved() {

                }
            });
        } else {
            fetchData(url);
        }
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
    public void sortByNewest() {

    }

    @Override
    public void sortByOldest() {

    }

    @Override
    public void onParsed(final List<RssFeedItem> resultList) {
        mDatabase.saveRssListToDatabaseAsync(resultList, new RssFeedDatabase.DatabaseCallback<RssFeedItem>() {
            @Override
            public void onDataReceived(List<RssFeedItem> data) {

            }

            @Override
            public void onDataSaved() {
            mView.showData(new ArrayList<RssFeedItem>(resultList));
            }
        });
    }

    @Override
    public void onError(String error) {
        mView.showMessage("Error parsing: " + error);
    }
}
