package com.example.antony.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.antony.rssreader.models.MenuItem;
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
            if (!rssRecordExists(item)) {
                saveRssItemToDatabase(item);
            }
        }
    }

    private boolean rssRecordExists(RssFeedItem item) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String select = "SELECT * FROM " + RssFeedTable.TABLE_NAME_RSS + " WHERE " + RssFeedTable.TITLE + " =?";
            cursor = db.rawQuery(select, new String[]{item.getTitle()});
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
        writableDatabase.close();
    }

    @Override
    public void saveRssListToDatabaseAsync(List<RssFeedItem> data, final DatabaseCallback<RssFeedItem> callback) {
        new AsyncTask<List<RssFeedItem>, Void, Void>() {

            @Override
            protected Void doInBackground(List<RssFeedItem>... params) {
                List<RssFeedItem> param = params[0];
                saveRssListToDatabase(param);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.onDataSaved();
            }
        }.execute(data);
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
        database.close();
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
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            query = database.rawQuery("SELECT * FROM " + RssFeedTable.TABLE_NAME_RSS, null);
            return query != null && (query.getCount() > 0);
        } finally {
            if (query != null) {
                query.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public void clearAllData() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("delete from " + RssFeedTable.TABLE_NAME_RSS);
        writableDatabase.execSQL("delete from " + MenuItemsTable.TABLE_NAME_MENU_ITEMS);
        writableDatabase.close();
    }

    @Override
    public void getDataAsync(final DatabaseCallback callback) {
        new AsyncTask<Void, Void, List<RssFeedItem>>() {
            @Override
            protected List<RssFeedItem> doInBackground(Void... params) {
                List<RssFeedItem> allData = getAllData();
                return allData;
            }

            @Override
            protected void onPostExecute(List<RssFeedItem> rssFeedItems) {
                callback.onDataReceived(rssFeedItems);
            }
        }.execute();
    }

    @Override
    public void saveMenuItem(MenuItem item) {
        if (menuItemExist(item)) {
            return;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MenuItemsTable.SERVICE_LINK, item.getServiceUrl());
        contentValues.put(MenuItemsTable.SERVICE_NAME, item.getServiceName());
        writableDatabase.insert(MenuItemsTable.TABLE_NAME_MENU_ITEMS, null, contentValues);
        writableDatabase.close();
    }

    @Override
    public void saveMenuItems(List<MenuItem> items) {
        for (MenuItem item : items) {
            if (!menuItemExist(item)) {
                saveMenuItem(item);
            }
        }
    }

    @Override
    public List<MenuItem> getMenuItems() {
        List<MenuItem> resultList = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor query = database.query(MenuItemsTable.TABLE_NAME_MENU_ITEMS, null, null, null, null, null, null);
        if (query.moveToFirst()) {
            int serviceNameIndex = query.getColumnIndex(MenuItemsTable.SERVICE_NAME);
            int serviceLinkIndex = query.getColumnIndex(MenuItemsTable.SERVICE_LINK);
            do {
                addMenuItem(resultList, query, serviceLinkIndex, serviceNameIndex);
            } while (query.moveToNext());
        }
        query.close();
        database.close();
        return resultList;
    }

    private void addMenuItem(List<MenuItem> resultList, Cursor query, int linkIndex, int serviceNameIndex) {
        String link = query.getString(linkIndex);
        String name = query.getString(serviceNameIndex);
        MenuItem menuItem = new MenuItem(name, link);
        resultList.add(menuItem);
    }

    @Override
    public void saveMenuItemsAsync(List<MenuItem> items, final DatabaseCallback callback) {
        new AsyncTask<List<MenuItem>, Void, Void>() {
            @Override
            protected Void doInBackground(List<MenuItem>... params) {
                List<MenuItem> param = params[0];
                saveMenuItems(param);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.onDataSaved();
            }
        }.execute(items);
    }

    private boolean menuItemExist(MenuItem item) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String select = "SELECT * FROM " + MenuItemsTable.TABLE_NAME_MENU_ITEMS + " WHERE " + MenuItemsTable.SERVICE_LINK + " =?";
            cursor = db.rawQuery(select, new String[]{item.getServiceUrl()});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    @Override
    public void getMenuItemsAsync(final DatabaseCallback<MenuItem> databaseCallback) {
        new AsyncTask<Void, Void, List<MenuItem>>() {
            @Override
            protected List<MenuItem> doInBackground(Void... params) {
                return getMenuItems();
            }

            @Override
            protected void onPostExecute(List<MenuItem> items) {
                databaseCallback.onDataReceived(items);
            }
        }.execute();
    }
}
