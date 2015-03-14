package com.devappcenter.theme.Google;

import android.view.View;

/**
 * Created by Andy on 3/4/15 AD.
 */
public interface FragmentListener {
    abstract View getRightViewHeader();
    abstract void setRightViewHeader(View view);
    abstract View getSubViewHeader();
    abstract View getDrawerRightView();
    abstract String setHeaderBackWithTitle();
    abstract String setHeaderMenuWithTitle();
}
