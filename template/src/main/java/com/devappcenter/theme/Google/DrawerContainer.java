package com.devappcenter.theme.Google;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.devappcenter.theme.R;


/**
 * Created by Andy on 8/18/14 AD.
 */
public class DrawerContainer extends FrameLayout {

    public DrawerContainer(Context context) {
        super(context);
        initViews();
    }

    public DrawerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public DrawerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.drawer_containner, this, true);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        this.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}