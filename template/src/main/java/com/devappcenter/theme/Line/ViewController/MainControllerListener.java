package com.devappcenter.theme.Line.ViewController;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

public interface MainControllerListener {
    Integer TabTotal();
    View MenuOfTab(Integer tab);
    Integer TabIcon(Integer tab);
    String[] TabTitles();
    Drawable[] TabImages();
    Fragment ControllerOfTab(Integer tab);
    List<TabViewController> AllController();

}
