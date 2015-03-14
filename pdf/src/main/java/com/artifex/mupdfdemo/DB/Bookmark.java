package com.artifex.mupdfdemo.DB;

import android.database.Cursor;

/**
 * Created by Andy on 10/20/14 AD.
 */
public class Bookmark extends Table {
    //public Integer mark_id, page;
    //public String book_id, user_id, title;

    public String OnCreate() {
        return "CREATE TABLE Bookmark(mark_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id TEXT, " +
                "user_id TEXT, " +
                "title TEXT, " +
                "page INTEGER);";
    }

    public Cursor All(String bookid, String userid) {
        String sql = String.format("SELECT * FROM Bookmark WHERE book_id = '%s' AND user_id = '%s'", bookid, userid);
        Cursor mCursor = db.rawQuery(sql,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            return mCursor.getCount()>0?mCursor:null;
        }
        return null;
    }

    public Cursor getItem(Integer index) {
        String sql = String.format("SELECT * FROM Bookmark WHERE mark_id = %d LIMIT 1", index);
        Cursor mCursor = db.rawQuery(sql,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            return mCursor.getCount()>0?mCursor:null;
        }
        return null;
    }

    public Cursor getItem(String bookid, String userid, Integer page) {
        String sql = String.format("SELECT * FROM Bookmark WHERE book_id = '%s' AND user_id = '%s' AND page = %d LIMIT 1", bookid, userid, page);
        Cursor mCursor = db.rawQuery(sql,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            return mCursor.getCount()>0?mCursor:null;
        }
        return null;
    }

    public void Add(String bookid, String userid, Integer page) {
        String sql = String.format("INSERT INTO Bookmark(book_id, user_id, title, page)VALUES('%s', '%s', 'Untitled', %d)", bookid, userid, page);
        db.execSQL(sql);
    }

    public void Delete(Integer index) {
        String sql = String.format("DELETE FROM Bookmark WHERE mark_id = %d", index);
        db.execSQL(sql);
    }

    public void Update(Integer markid, String title) {
        String sql = String.format("UPDATE Bookmark SET title = '%s' WHERE mark_id = %d", title, markid);
        db.execSQL(sql);
    }
}
