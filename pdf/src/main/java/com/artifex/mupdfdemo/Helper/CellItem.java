package com.artifex.mupdfdemo.Helper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artifex.mupdfdemo.R;

import org.json.JSONObject;

/**
 * Created by APPLE on 6/7/14.
 */
public class CellItem implements InterfaceItem {

    protected String mTitle;
    private int mImage;
    private int mTag;
    public boolean selected;

    public CellItem(String title, int imageResource, int tag){
        mTitle = title;
        mImage = imageResource;
        mTag = tag;
        selected = false;
    }

    public int getTag(){
        return mTag;
    }

    @Override
    public int getViewType() {
        return Adapter.RowType.ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view = convertView;
        return view;
    }

    @Override
    public JSONObject getContent() {
        return null;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
