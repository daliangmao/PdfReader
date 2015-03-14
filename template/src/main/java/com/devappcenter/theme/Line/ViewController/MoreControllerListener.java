package com.devappcenter.theme.Line.ViewController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andy on 2/24/15 AD.
 */
public interface MoreControllerListener {
    JSONObject getItemAtIndex(int index) throws JSONException;
    Integer getNumOfItem();
    void onItemClick(int index);
}
