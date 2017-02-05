package com.example.antony.rssreader.screens.content;

import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface ContentFragmentContract {
    interface View {

        void showData(List<RssFeedItem> resultList);

        void showMessage(String message);

        void makeNetworkCall(String link);
    }

    interface Presenter {

        List<RssFeedItem> queryDatabase();

        void fetchData(String link);

        void OnDeliveredResult(RssFeed result);
    }
}
