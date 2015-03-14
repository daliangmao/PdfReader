package com.devappcenter.theme.Line.ViewController;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Andy on 2/24/15 AD.
 */
public class TabViewController extends Fragment implements TabControllerListener {

    @Override
    public View getMenuView() {
        return null;
    }
}

interface TabControllerListener {
    abstract View getMenuView();
}