package com.example.antony.rssreader.models;

/**
 * Created by Pripachkin on 04.02.2017.
 */

public class RssFeedItem {
    private String title;
    private String linkUrl;

    public RssFeedItem(String title, String linkUrl, String description) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getDescription() {
        return description;
    }

    private String description;
}
