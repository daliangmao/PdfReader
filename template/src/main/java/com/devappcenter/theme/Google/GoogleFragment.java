package com.devappcenter.theme.Google;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import Helper.Header.*;

/**
 * Created by Andy on 3/3/15 AD.
 */
public class GoogleFragment extends Fragment implements FragmentListener{

    private FragmentListener listener;
    private GoogleDelegate delegate;

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        listener = this;
        delegate = (GoogleDelegate) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewHeader.addRightPannelView(listener.getRightViewHeader());
        ViewHeader.addSubMenu(listener.getSubViewHeader());
        listener.setRightViewHeader(ViewHeader.addRightPannel());
        ViewHeader.SubOpen(false);
        ViewHeader viewHeader = (ViewHeader) ViewHeader.getInstance();
        viewHeader.setOnBackWithTitle(listener.setHeaderBackWithTitle());
        viewHeader.setOnMenuWithTitle(listener.setHeaderMenuWithTitle());
        delegate.onRightDrawer(listener.getDrawerRightView());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View getRightViewHeader() {
        return null;
    }

    @Override
    public View getSubViewHeader() {
        return null;
    }

    @Override
    public View getDrawerRightView() {
        return null;
    }

    @Override
    public String setHeaderBackWithTitle() {
        return null;
    }

    @Override
    public String setHeaderMenuWithTitle() {
        return null;
    }

    @Override
    public void setRightViewHeader(View view) {

    }

}
