package com.example.antony.rssreader.screens.mainscreen;

import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface MainScreenContract {
    interface View {

        void showMessage(String message);

        void showData(List<RssFeedItem> resultList);

        void makeNetworkCall(String link);
    }

    interface Presenter {

        void fetchData(String link);

        void OnDeliveredResult(RssFeed result);

        List<RssFeedItem> queryDatabase();
    }
}
