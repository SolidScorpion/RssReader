package com.example.antony.rssreader.utilities;

        import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class CommonUtils {
    private CommonUtils() {
    }

    public static Spanned fromHtml(String text) {
        Spanned result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT, null, null);
        } else {
            result = Html.fromHtml(text, null, null);
        }
        return result;
    }
}
