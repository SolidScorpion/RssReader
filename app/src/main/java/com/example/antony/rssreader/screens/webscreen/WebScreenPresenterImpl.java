package com.example.antony.rssreader.screens.webscreen;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class WebScreenPresenterImpl implements WebScreenContract.Presenter {
    private WebScreenContract.View mView;

    public WebScreenPresenterImpl(WebScreenContract.View mView) {
        this.mView = mView;
    }
}
