package com.example.antony.rssreader.database;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class MenuItemsTable {
    public static final String TABLE_NAME_MENU_ITEMS = "MenuItems";
    public static final String SERVICE_LINK = "serviceLink";
    public static final String SERVICE_NAME = "serviceName";
    public static final String ID = "id";

    public static final String columns[] = new String[]{ID, SERVICE_NAME, SERVICE_LINK};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_MENU_ITEMS + " ( " + ID + " integer primary key autoincrement, " +
            "" + SERVICE_NAME + " text," +
            " " + SERVICE_LINK + " text );";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS" + TABLE_NAME_MENU_ITEMS;

}
