package com.devappcenter.pdfreader;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 3/14/15 AD.
 */
public class SplashScreen extends com.devappcenter.theme.SplashScreen {
    @Override
    public Drawable getBackground() {
        return new ColorDrawable(getResources().getColor(R.color.CornflowerBlue));
    }

    @Override
    public int getLogo() {
        return getResources().getIdentifier("ic_launcher", "drawable", getPackageName());
    }

    @Override
    public void onFinishLoading(String jsonString) {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        i.putExtra("discover", jsonString);
        startActivity(i);
        finish();
    }

    @Override
    public String getLoadingUrl() {
        return "http://www.lightoflovenovel.com/ebook/ios/discover.php";
    }

    @Override
    public List<NameValuePair> getPost() {
        List<NameValuePair> postValue = new ArrayList<NameValuePair>(2);
        postValue.add(new BasicNameValuePair("filter", "new_entry"));
        postValue.add(new BasicNameValuePair("user", ""));
        postValue.add(new BasicNameValuePair("platform", "android"));
        postValue.add(new BasicNameValuePair("page", "0"));
        return postValue;
    }
}
