package com.artifex.mupdfdemo.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andy on 8/15/14 AD.
 */
public class Config extends Table
{
    public Integer theme;

    @Override
    public void initdata(SQLiteDatabase db) {
        super.initdata(db);

        String sql = "SELECT * FROM Config WHERE 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            theme = cursor.getInt(0);
        }
        else
        {
            clear();
            sql = "INSERT INTO Config(theme)VALUES(0)";
            db.execSQL(sql);
        }

    }

    public void update(){
        String sql = "UPDATE Config SET theme = " + theme + " WHERE 1";
        db.execSQL(sql);
    }

    public String OnCreate() {
        return "CREATE TABLE Config(theme INTEGER);";
    }

    public void clear(){
        theme = 0;
    }
}

