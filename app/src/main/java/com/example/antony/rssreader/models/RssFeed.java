package com.example.antony.rssreader.models;

/**
 * Created by Pripachkin on 03.02.2017.
 */

public class RssFeed {
    private String rawResponse;
    private String originalLink;

    public RssFeed(String rawResponse, String originalLink) {
        this.rawResponse = rawResponse;
        this.originalLink = originalLink;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public String getRawResponse() {
        return rawResponse;
    }
}
