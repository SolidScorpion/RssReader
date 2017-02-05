package com.example.antony.rssreader.screens.content;

import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface ContentFragmentContract {
    interface View {

    }

    interface Presenter {

        List<RssFeedItem> queryDatabase();
    }
}
