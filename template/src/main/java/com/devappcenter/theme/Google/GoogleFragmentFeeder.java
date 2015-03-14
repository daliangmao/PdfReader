package com.devappcenter.theme.Google;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devappcenter.theme.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Helper.FeedViewController;
import Helper.ViewCell;

/**
 * Created by Andy on 3/4/15 AD.
 */
public class GoogleFragmentFeeder extends FeedViewController implements FragmentListener {

    private FragmentListener listener;
    private GoogleDelegate delegate;

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        listener = this;
        delegate = (GoogleDelegate) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewHeader.addRightPannelView(listener.getRightViewHeader());
        ViewHeader.addSubMenu(listener.getSubViewHeader());
        ViewHeader.SubOpen(false);
        ViewHeader viewHeader = (ViewHeader) ViewHeader.getInstance();
        viewHeader.setOnBackWithTitle(listener.setHeaderBackWithTitle());
        viewHeader.setOnMenuWithTitle(listener.setHeaderMenuWithTitle());
        delegate.onRightDrawer(listener.getDrawerRightView());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public ViewCell cellForItemAtIndex(JSONObject obj, Integer position) {
        return null;
    }

    @Override
    public void OnItemClick(Integer position) {

    }

    @Override
    public JSONArray arrayOfItem(String result) throws JSONException{
        JSONObject p = new JSONArray(result).getJSONObject(0);
        return p.getJSONArray("Result");
    }

    @Override
    public void OnInitialize() {

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
}
