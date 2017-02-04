package com.example.antony.rssreader.utilities;


import android.util.Xml;

import com.example.antony.rssreader.models.RssFeed;
import com.example.antony.rssreader.models.RssFeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Pripachkin on 04.02.2017.
 */

public class Parser {
    private XmlPullParser parser;
    private static final String TITLE_TAG = "title";
    private static final String LINK_TAG = "link";
    private static final String DESCRIPTION_TAG = "description";
    private static final String ITEM_TAG = "item";

    public Parser() {
        parser = Xml.newPullParser();
    }

    public List<RssFeedItem> parseRssFeed(RssFeed feed) {
        String rawResponse = feed.getRawResponse();
        List<RssFeedItem> returnList = new ArrayList<>();
        StringReader stringReader = null;
        try {
            stringReader = new StringReader(rawResponse);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stringReader);
            parser.nextTag();
            parser.nextTag();
            returnList = readFeed(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stringReader != null) {
                stringReader.close();
            }
        }
        return returnList;
    }

    private List<RssFeedItem> readFeed(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<RssFeedItem> items = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, null, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = parser.getName();
            if (name.equals(ITEM_TAG)) {
                RssFeedItem item = readItemTag(parser);
                items.add(item);
            } else {
                skip(parser);
            }
        }
        return items;
    }

    private RssFeedItem readItemTag(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, ITEM_TAG);
        String title = null;
        String link = null;
        String description = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
            String name = parser.getName();
            if (name.equals(TITLE_TAG)) {
                title = readTagContent(parser, TITLE_TAG);
            } else if (name.equals(LINK_TAG)) {
                link = readTagContent(parser, LINK_TAG);
            } else if (name.equals(DESCRIPTION_TAG)) {
                description = readTagContent(parser, DESCRIPTION_TAG);
            } else {
                skip(parser);
            }
        }
        return new RssFeedItem(title, link, description);
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private String readTagContent(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, tag);
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, tag);
        return content;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
