package com.artifex.mupdfdemo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;

/**
 * Created by Andy on 8/15/14 AD.
 */
class OpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "mupdf.db";
    private static final int DATABASE_VERSION = 2;

    private SqliteDelegate delegate;

    OpenHelper(Context context, SqliteDelegate delegate)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.delegate = delegate;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        for (Table table : delegate.OnTables()) {
            db.execSQL(table.OnCreate());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for (Table table : delegate.OnTables()) {
            db.execSQL(table.OnDrop());
        }
        onCreate(db);
    }

    public interface SqliteDelegate {
        public Collection<Table> OnTables();
    }
}

