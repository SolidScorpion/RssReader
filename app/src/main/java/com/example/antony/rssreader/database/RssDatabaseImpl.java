package com.example.antony.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.antony.rssreader.models.RssFeedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class RssDatabaseImpl extends SQLiteOpenHelper implements RssFeedDatabase {
    private static final String DB_NAME = "cacheDB";

    public RssDatabaseImpl(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RssFeedTable.CREATE_TABLE);
        db.execSQL(MenuItemsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RssFeedTable.DROP_TABLE);
        db.execSQL(MenuItemsTable.DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void saveRssListToDatabase(List<RssFeedItem> items) {
        for (RssFeedItem item : items) {
            if (!recordExists(item)) {
                saveRssItemToDatabase(item);
            }
        }
    }

    private boolean recordExists(RssFeedItem item) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String select = "SELECT * FROM " + RssFeedTable.TABLE_NAME_RSS + " WHERE " + RssFeedTable.TITLE + " =?";
            cursor = db.rawQuery(select, new String[] {item.getTitle()});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
                db.close();
            }
        }
    }

    @Override
    public void saveRssItemToDatabase(RssFeedItem item) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RssFeedTable.TITLE, item.getTitle());
        contentValues.put(RssFeedTable.LINK, item.getLinkUrl());
        contentValues.put(RssFeedTable.IMG_LINK, item.getImgUrl());
        contentValues.put(RssFeedTable.DESCRIPTION, item.getDescription());
        contentValues.put(RssFeedTable.DATE, item.getDate());
        writableDatabase.insert(RssFeedTable.TABLE_NAME_RSS, null, contentValues);
    }

    @Override
    public List<RssFeedItem> getAllData() {
        List<RssFeedItem> resultList = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor query = database.query(RssFeedTable.TABLE_NAME_RSS, null, null, null, null, null, null);
        if (query.moveToFirst()) {
            int titleIndex = query.getColumnIndex(RssFeedTable.TITLE);
            int linkIndex = query.getColumnIndex(RssFeedTable.LINK);
            int descriptionIndex = query.getColumnIndex(RssFeedTable.DESCRIPTION);
            int dateIndex = query.getColumnIndex(RssFeedTable.DATE);
            int imgLinkIndex = query.getColumnIndex(RssFeedTable.IMG_LINK);
            do {
                addRssItem(resultList, query, titleIndex, linkIndex, descriptionIndex, dateIndex, imgLinkIndex);
            } while (query.moveToNext());
        }
        query.close();
        return resultList;
    }

    private void addRssItem(List<RssFeedItem> resultList, Cursor query, int titleIndex,
                            int linkIndex, int descriptionIndex, int dateIndex, int imgLinkIndex) {
        String title = query.getString(titleIndex);
        String link = query.getString(linkIndex);
        String description = query.getString(descriptionIndex);
        String date = query.getString(dateIndex);
        String imgLink = query.getString(imgLinkIndex);
        RssFeedItem rssItem = new RssFeedItem(title, link, description, date, imgLink);
        resultList.add(rssItem);
    }

    @Override
    public boolean hasData() {
        Cursor query = null;
        try {
            SQLiteDatabase database = getWritableDatabase();
            query = database.rawQuery("SELECT * FROM " + RssFeedTable.TABLE_NAME_RSS, null);
            return query != null && (query.getCount() > 0);
        } finally {
            if (query != null) {
                query.close();
            }
        }
    }

    @Override
    public void clearAllData() {
        getWritableDatabase().execSQL("delete from " + RssFeedTable.TABLE_NAME_RSS);
        getWritableDatabase().execSQL("delete from " + MenuItemsTable.TABLE_NAME_MENU_ITEMS);
    }
}
