package com.example.antony.rssreader.screens.content;

import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class ContentFragmentPresenterImpl implements ContentFragmentContract.Presenter {
    private ContentFragmentContract.View mView;

    public ContentFragmentPresenterImpl(ContentFragmentContract.View mView) {
        this.mView = mView;
    }

    @Override
    public List<RssFeedItem> queryDatabase() {
        return null;
    }
}
