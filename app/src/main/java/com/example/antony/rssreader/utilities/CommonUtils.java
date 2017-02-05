package com.example.antony.rssreader.utilities;

import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

import com.example.antony.rssreader.UrlClickListener;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class CommonUtils {
    private CommonUtils() {
    }

    public static Spanned fromHtml(String text, UrlClickListener clickListener) {
        Spanned result = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT, null, null);
        } else {
            result = Html.fromHtml(text, null, null);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(result);
        URLSpan[] spans = spannableStringBuilder.getSpans(0, result.length(), URLSpan.class);
        for (URLSpan span : spans) {
            makeLinkClickable(spannableStringBuilder, span, clickListener);
        }
        return spannableStringBuilder;
    }
    private static void makeLinkClickable(SpannableStringBuilder stringBuilder, final URLSpan span, final UrlClickListener listener) {
        int spanStart = stringBuilder.getSpanStart(span);
        int spanEnd = stringBuilder.getSpanEnd(span);
        int spanFlags = stringBuilder.getSpanFlags(span);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                listener.onLinkClicked(span.getURL());
            }
        };
        stringBuilder.setSpan(clickableSpan, spanStart, spanEnd, spanFlags);
        stringBuilder.removeSpan(span);
    }
}
