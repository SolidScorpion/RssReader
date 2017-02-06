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
    private String requestUrl;
    public RssFeedItem(String title, String linkUrl, String description, String date, String imgUrl, String requestUrl) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.description = description;
        this.date = date;
        this.imgUrl = imgUrl;
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RssFeedItem that = (RssFeedItem) o;

        if (!title.equals(that.title)) return false;
        if (!linkUrl.equals(that.linkUrl)) return false;
        if (!description.equals(that.description)) return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + linkUrl.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + date.hashCode();
        return result;
    }
}
