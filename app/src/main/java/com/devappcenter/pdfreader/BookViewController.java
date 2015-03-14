package com.devappcenter.pdfreader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.devappcenter.theme.Google.GoogleFragmentFeeder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Helper.ImageLoader;
import Helper.ViewCell;

/**
 * Created by Andy on 3/14/15 AD.
 */
public class BookViewController extends GoogleFragmentFeeder {

    @Override
    public String setHeaderMenuWithTitle() {
        return "รายการหนังสือ";
    }

    @Override
    public String getUrl() {
        return "http://www.lightoflovenovel.com/ebook/ios/discover.php";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, new ListView(getActivity()), savedInstanceState);
    }

    @Override
    public ViewCell cellForItemAtIndex(JSONObject obj, Integer position) {
        return new ViewItem(getActivity(), obj);
    }

    @Override
    public JSONArray arrayOfItem(String result) throws JSONException{
        return new JSONArray(result).getJSONArray(0);
    }

    @Override
    protected List<NameValuePair> getPostValue() {
        List<NameValuePair> postValue = super.getPostValue();
        postValue.add(new BasicNameValuePair("filter", "new_entry"));
        return postValue;
    }

    @Override
    public void OnItemClick(Integer position) {

    }

    private class ViewItem extends ViewCell {

        public ViewItem(Context context, JSONObject json) {
            super(context, json);
        }

        @Override
        public View getViewItem(LayoutInflater inflater, View convertView) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.cart_item, null);
            }
            ImageView imgCover = (ImageView)view.findViewById(R.id.img_cart_cover);
            TextView lblTitle = (TextView) view.findViewById(R.id.lbl_cart_title);
            TextView lblPrice = (TextView) view.findViewById(R.id.lbl_cart_price);
            ImageLoader imageLoader = ImageLoader.getInstance(null);
            try {
                Integer price = jsonItem.getInt("price");
                lblTitle.setText(jsonItem.getString("title"));
                lblPrice.setText(String.format("%d บาท", price));
                imageLoader.DisplayImage(jsonItem.getString("thumb"), imgCover);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}
