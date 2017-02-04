package com.example.antony.rssreader.models;

/**
 * Created by Pripachkin on 03.02.2017.
 */

public class RssFeed {
    private String rawResponse;

    public RssFeed(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public String getRawResponse() {
        return rawResponse;
    }
}
