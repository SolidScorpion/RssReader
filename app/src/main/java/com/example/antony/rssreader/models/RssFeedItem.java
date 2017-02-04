package com.example.antony.rssreader.models;

/**
 * Created by Pripachkin on 04.02.2017.
 */

public class RssFeedItem {
    private String title;
    private String linkUrl;
    private String description;

    public RssFeedItem(String title, String linkUrl, String description) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.description = description;
    }
}
