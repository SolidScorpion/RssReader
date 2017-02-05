package com.example.antony.rssreader.database;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class RssFeedTable {
    public static final String TABLE_NAME_RSS = "RssItems";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "pubDate";
    public static final String IMG_LINK = "imgLink";
    public static String[] columns = new String[]{ID, TITLE, LINK, DESCRIPTION, DATE, IMG_LINK};

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME_RSS + " ( " + ID + " integer primary key autoincrement, " +
                    "" + TITLE + " text," +
                    " " + LINK + " text," +
                    " " + DESCRIPTION + " text," +
                    " " + IMG_LINK + " text," +
                    " " + DATE + " text );";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS" + TABLE_NAME_RSS;

}
