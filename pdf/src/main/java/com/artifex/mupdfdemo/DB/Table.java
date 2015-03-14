package com.artifex.mupdfdemo.DB;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andy on 8/15/14 AD.
 */
public class Table{

    protected SQLiteDatabase db;

    public Table(){
        this.db = null;
    }

    public void initdata(SQLiteDatabase db){
        this.db = db;
    }

    public String OnCreate() {
        return null;
    }

    public String OnDrop() {
        String drop = "DROP TABLE IF EXISTS "+this.getClass().getSimpleName();
        return drop;
    }
}
