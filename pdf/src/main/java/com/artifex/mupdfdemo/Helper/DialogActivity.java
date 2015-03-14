package com.artifex.mupdfdemo.Helper;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.artifex.mupdfdemo.R;


/**
 * Created by Andy on 8/16/14 AD.
 */
public class DialogActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout mainLayout = null;
        LayoutInflater inflater = LayoutInflater.from(this);
        String screen_type = getResources().getString(R.string.screen_type);
        mainLayout = (FrameLayout) inflater.inflate(R.layout.activity_dialog, null);
        mainLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        FrameLayout frameLayout = (FrameLayout) mainLayout.findViewById(R.id.dialog);

        getViewDialog(inflater, frameLayout);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                if (tag.equals("outbound")) {
                    finish();
                }
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        setContentView(mainLayout);
    }

    protected void getViewDialog(LayoutInflater inflater, FrameLayout containner) {
    }
}
