package com.example.antony.rssreader.models;

/**
 * Created by Pripachkin on 04.02.2017.
 */

public class RssFeedItem {
    private String title;
    private String linkUrl;
    private String description;
    private String imgUrl;
    private String date;

    public RssFeedItem(String title, String linkUrl, String description, String date, String imgUrl) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.description = description;
        this.date = date;
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public String getImgUrl() {
        return imgUrl;
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
}
