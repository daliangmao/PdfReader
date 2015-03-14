package com.artifex.mupdfdemo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Configure implements OpenHelper.SqliteDelegate
{
    private static Configure instance = null;

    private SQLiteDatabase database;
    public Bookmark bookmark;
    public Config confg;
    public Map<String, Table> tables;

    public static Configure getInstance(Context context) {
        if (instance == null) {
            instance = new Configure(context);
        }
        return instance;
    }

    public static Configure getInstance() {
        return instance;
    }

    public Configure(Context context) {
        tables = new HashMap<String, Table>();
        confg = new Config();
        bookmark = new Bookmark();
        tables.put("Config", confg);
        tables.put("Bookmark", bookmark);

        OpenHelper openHelper = new OpenHelper(context, this);
        this.database = openHelper.getWritableDatabase();
        for (Table table : tables.values()) {
            table.initdata(this.database);
        }
    }

    @Override
    public Collection<Table> OnTables() {
        return this.tables.values();
    }
}
