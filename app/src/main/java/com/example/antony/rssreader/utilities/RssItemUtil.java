package com.example.antony.rssreader.utilities;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pripachkin on 04.02.2017.
 */

public class RssItemUtil {
    private RssItemUtil() {
    }

    private static final String[] IMG_FORMATS = {"jpg", "png", "gif", "jpeg"};

    public static String removeImgTags(String content) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        return content.replaceAll("<img\\s[^>]*?src\\s*=\\s*['\"]([^'\"]*?)['\"][^>]*?>", "");
    }

    public static String getImgUrl(String content) {
        String imgRegex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        Pattern pattern = Pattern.compile(imgRegex);
        Matcher matcher = pattern.matcher(content);
        String result = null;
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    public static boolean isImgUtl(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            return false;
        }
        for (String imgFormat : IMG_FORMATS) {
            if (imgUrl.endsWith(imgFormat)) {
                return true;
            }
        }
        return false;
    }
}
