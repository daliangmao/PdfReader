package com.artifex.mupdfdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Andy on 10/24/14 AD.
 */
public class TableContentViewController extends RelativeLayout {
    public TableContentViewController(Context context) {
        super(context);
        initViews();
    }

    public void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.table_content_menu, this, true);
    }
}
