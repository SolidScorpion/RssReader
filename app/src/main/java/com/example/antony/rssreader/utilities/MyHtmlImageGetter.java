package com.example.antony.rssreader.utilities;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

/**
 * Created by Pripachkin on 04.02.2017.
 */

public class MyHtmlImageGetter implements Html.ImageGetter {
    private static final String TAG = "MyHtmlImageGetter";
    private String[] imgExtentions = {"png", "gif", "jpg", "jpeg"};

    @Override
    public Drawable getDrawable(String source) {
        for (String imgExtention : imgExtentions) {
            if (source.endsWith(imgExtention)) {
                Log.d(TAG, source);
                break;
            }
        }
        return null;
    }
}
