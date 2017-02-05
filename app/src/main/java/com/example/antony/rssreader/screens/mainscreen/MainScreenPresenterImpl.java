package com.example.antony.rssreader.screens.mainscreen;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class MainScreenPresenterImpl implements MainScreenContract.Presenter {
    private MainScreenContract.View mView;
    public MainScreenPresenterImpl(MainScreenContract.View view) {
        mView = view;
    }
}
