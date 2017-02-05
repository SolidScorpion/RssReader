package com.example.antony.rssreader.database;

import android.net.Uri;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class FeedDatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.example.antony.rssitem";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RSS_ITEM = "rssItem";
    public static final String MENU_ITEM = "menuItem";
}
