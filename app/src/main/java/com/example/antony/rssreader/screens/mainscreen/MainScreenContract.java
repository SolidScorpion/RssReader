package com.example.antony.rssreader.screens.mainscreen;

import com.example.antony.rssreader.models.MenuItem;

import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public interface MainScreenContract {
    interface View {

        void showMessage(String message);


        void showMenuItems(List<MenuItem> data);
    }

    interface Presenter {

        void saveMenuItem(MenuItem item);

        void removeItem(MenuItem item);

        void getMenuItems();

    }
}
